package com.nlpproject.callrecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.nlpproject.callrecorder.GoogleServices.GoogleCloudRecognitionRequester;
import com.nlpproject.callrecorder.GoogleServices.GoogleCloudStorageSender;
import com.nlpproject.callrecorder.ORMLiteTools.services.BaseService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordingService extends Service{
    public static final String LOG_TAG = "CALL_RECORDER";
    private static final String ACTION_PHONE_STATE = "android.intent.action.PHONE_STATE";
    private static final String ACTION_OUTGOING_CALL = "android.intent.action.NEW_OUTGOING_CALL";

    private MediaRecorder recorder;
    private boolean recording;
    public static File output_dir;
    private CallBroadcastReceiver call_receiver;
    private String recordedFilePath;
    private String fileName;
    private String ringingNumber;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BaseService.initServices(getApplicationContext());

        recording = false;

        output_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call_Recorder");

        if (!output_dir.exists())
            output_dir.mkdirs();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PHONE_STATE);
        filter.addAction(ACTION_OUTGOING_CALL);

        call_receiver = new CallBroadcastReceiver();
        registerReceiver(call_receiver, filter);

        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        if (recording)
            stopRecording();

        super.onDestroy();
    }


    private void startRecording() {
        recorder = new MediaRecorder();
        String datetime = new SimpleDateFormat("yy-MM-dd hh-mm-ss").format(new Date());
        fileName = "record_" + datetime + ".3gp";
        recordedFilePath = output_dir.getAbsolutePath() + "/" + fileName;

        Log.i(LOG_TAG, "File name: " + recordedFilePath);
        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        recorder.setOutputFile(recordedFilePath);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        recorder.setAudioSamplingRate(8000);

        try {
            recorder.prepare();
        } catch (IOException e) {
            // TODO Handle exception
            e.printStackTrace();
        }

        recorder.start();

        recording = true;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        recording = false;
        try {
            ProcessingTask newProcessingTask = new ProcessingTask();
            newProcessingTask.setRecordDate(new Date());
            newProcessingTask.setCaller_number(ringingNumber);
            Long id = ProcessingTaskService.create(newProcessingTask);
            new GoogleCloudStorageSender().uploadFile(recordedFilePath, id);
            new GoogleCloudRecognitionRequester().sendRecognitionRequest(fileName, id);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class CallBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (intent.getAction().equals(ACTION_OUTGOING_CALL)){
                ringingNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
            }
            else if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                ringingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            }
            else if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if (!recording) {
                    startRecording();
                    Log.i(LOG_TAG, "Recording...");
                }
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (recording) {
                    stopRecording();
                    ringingNumber = null;
                    Log.i(LOG_TAG, "Stopped recording.");
                }
            }
        }
    }
//    class MyPhoneStateListener extends PhoneStateListener{
//        public void onCallStateChanged(final int state, final String incomingNumber) {
//            Log.e("")
//        }
//    }
}
