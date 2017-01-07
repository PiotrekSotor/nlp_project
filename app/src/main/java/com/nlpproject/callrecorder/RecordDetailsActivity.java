package com.nlpproject.callrecorder;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.Keyword_X_ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class RecordDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    private DateFormat DATA_FORMAT = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
    public final static String SELECTED_RECORD = "com.nlpproject.callrecorder.RECORD";
    Button btnDeleteRecord;
    Button btnPlayStopRecord;
    LinearLayout scrollViewLinearLayout;
    TextView textViewCallerNumber;
    TextView textViewRecordDate;

    MediaPlayer mediaPlayer;
    Boolean isPlaying = false;

    ProcessingTask representedProcessingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_details_activity);

        btnDeleteRecord = (Button) findViewById(R.id.btn_deleteRecord);
        btnDeleteRecord.setOnClickListener(this);
        btnPlayStopRecord = (Button) findViewById(R.id.btn_playStop);
        btnPlayStopRecord.setOnClickListener(this);
        textViewCallerNumber = (TextView) findViewById(R.id.textView_callerNumber);
        textViewRecordDate = (TextView) findViewById(R.id.textView_date);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.RecordDetailsActivity_scrollViewInnerLayout);

        Intent intent = getIntent();
        representedProcessingTask = (ProcessingTask) intent.getSerializableExtra(SELECTED_RECORD);
        if (representedProcessingTask != null) {
            File recordFile = new File(representedProcessingTask.getFilePath());
            if (!recordFile.exists()) {
                btnPlayStopRecord.setEnabled(false);
                btnPlayStopRecord.setText("Unable to\nfind record");
            }

            textViewRecordDate.setText(String.format("Date: %s", DATA_FORMAT.format(representedProcessingTask.getRecordDate())));
            textViewCallerNumber.setText(String.format("Caller: %s", representedProcessingTask.getCaller_number()));
        }

        refreshScrollView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isPlaying) {
            stopPlaying();
            isPlaying = false;
        }

    }

    @Override
    public void onClick(View v) {
        if (btnDeleteRecord.equals(v)) {
            deleteRecord();
        } else if (btnPlayStopRecord.equals(v)) {
            if (isPlaying) {
                stopPlaying();

            } else {
                startPlaying();

            }

        }
    }

    private void startPlaying() {
        try {
            isPlaying = true;
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(representedProcessingTask.getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });
            btnPlayStopRecord.setText("Stop");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void stopPlaying() {
        isPlaying = false;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
        btnPlayStopRecord.setText("Play");
    }

    private void deleteRecord() {
        List<Keyword_X_ProcessingTask> list = Keyword_X_ProcessingTaskService.findByProcessingTask(representedProcessingTask);
        if (list != null) {
            for (Keyword_X_ProcessingTask keyword_x_processingTask : list) {
                Keyword_X_ProcessingTaskService.delete(keyword_x_processingTask);
            }
        }
        File recordFile = new File(representedProcessingTask.getFilePath());
        if (recordFile.exists()) {
            recordFile.delete();
        }

        if (ProcessingTaskService.delete(representedProcessingTask)) {
            finish();
        }

    }

    private void refreshScrollView() {
        List<Keyword_X_ProcessingTask> list = Keyword_X_ProcessingTaskService.findByProcessingTask(representedProcessingTask);
        scrollViewLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        if (list == null || list.isEmpty()) {
            TextView message = new TextView(getApplicationContext());
            message.setText("No keywords found in trancription\n");
            scrollViewLinearLayout.addView(message);
        } else {
            StringBuffer sb = new StringBuffer();
            TextView keywords = new TextView(getApplicationContext());
            sb.append("Found keywords:\n");
            for (Keyword_X_ProcessingTask keyword_x_processingTask : list) {
                sb.append(String.format("(%s - %d)", keyword_x_processingTask.getFoundKeyword().getOriginalWord(), keyword_x_processingTask.getNumberOfMatches()));
            }
            sb.append("\n");
            keywords.setText(sb.toString());
            keywords.setTextColor(Color.parseColor("#ff0000"));
            keywords.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            scrollViewLinearLayout.addView(keywords);


        }
        TextView transcription = new TextView(getApplicationContext());
        transcription.setText("Transcription:\n" + representedProcessingTask.getTranscription());
        scrollViewLinearLayout.addView(transcription);

    }
}