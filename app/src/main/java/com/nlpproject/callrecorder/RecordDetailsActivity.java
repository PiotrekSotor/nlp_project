package com.nlpproject.callrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.KeywordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.Keyword_X_ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class RecordDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    public final static String SELECTED_RECORD = "com.nlpproject.callrecorder.RECORD";
    Button btnDeleteRecord;
    LinearLayout scrollViewLinearLayout;
    TextView textViewKeyword;

    ProcessingTask representedProcessingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_details_activity);

        btnDeleteRecord = (Button) findViewById(R.id.btn_deleteRecord);
        btnDeleteRecord.setOnClickListener(this);
        textViewKeyword = (TextView) findViewById(R.id.textView_callerNumber);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.RecordDetailsActivity_scrollViewInnerLayout);

        Intent intent = getIntent();
        representedProcessingTask = (ProcessingTask) intent.getSerializableExtra(SELECTED_RECORD);
        if (representedProcessingTask != null) {
            // TODO: fill fields on layout with details
        }

        refreshScrollView();
    }


    @Override
    public void onClick(View v) {
        if (btnDeleteRecord.equals(v)) {
            deleteKeyword();
        }
    }

    private void deleteKeyword() {
        if (ProcessingTaskService.delete(representedProcessingTask)) {
            finish();
        }
    }

    private void refreshScrollView() {
        List<Keyword_X_ProcessingTask> list = Keyword_X_ProcessingTaskService.findByProcessingTask(representedProcessingTask);
        if (list == null || list.isEmpty()) {
            TextView message = new TextView(getApplicationContext());
            message.setText("Empty keyword list");
            scrollViewLinearLayout.addView(message);
        } else {
            StringBuffer sb = new StringBuffer();
            TextView keywords = new TextView(getApplicationContext());
            for (Keyword_X_ProcessingTask keyword_x_processingTask : list) {
                sb.append(String.format("(%s - %d)", keyword_x_processingTask.getFoundKeyword().getOriginalWord(), keyword_x_processingTask.getNumberOfMatches()));
            }
            keywords.setText(sb.toString());
            keywords.setTextColor(0xFF0000);
            keywords.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
            scrollViewLinearLayout.addView(keywords);

            scrollViewLinearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);

            TextView transcription = new TextView(getApplicationContext());
            transcription.setText(representedProcessingTask.getTranscription());

        }
    }
}