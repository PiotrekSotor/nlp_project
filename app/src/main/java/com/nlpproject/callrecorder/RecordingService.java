package com.nlpproject.callrecorder;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaRecorder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RecordingService extends Service {
    public static final String LOG_TAG = "CALL_RECORDER";
    private static final String ACTION_PHONE_STATE = "android.intent.action.PHONE_STATE";

    private MediaRecorder recorder;
    private boolean recording;
    private File output_dir;
    private CallBroadcastReceiver call_receiver;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        recorder = new MediaRecorder();
        recording = false;

        output_dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call Recorder");

        if (!output_dir.exists())
            output_dir.mkdirs();

        final IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_PHONE_STATE);
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
        String datetime = new SimpleDateFormat("yy-MM-dd hh-mm-ss").format(new Date());
        String fileName = output_dir.getAbsolutePath() + "/record_" + datetime + ".mp4";

        recorder.setAudioSource(MediaRecorder.AudioSource.VOICE_COMMUNICATION);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        recorder.setOutputFile(fileName);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            recorder.prepare();
        } catch (IOException e) {
            // TODO Handle exception
        }

        recorder.start();

        recording = true;
    }

    private void stopRecording() {
        recorder.stop();
        recorder.release();
        recorder = null;
        recording = false;
    }

    class CallBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);

            if (state.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)) {
                if (!recording) {
                    startRecording();
                    Log.i(LOG_TAG, "Recording...");
                }
            } else if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)) {
                if (recording) {
                    stopRecording();
                    Log.i(LOG_TAG, "Stopped recording.");
                }
            }
        }
    }
}
