package com.nlpproject.callrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.KeywordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.Keyword_X_ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.util.List;

public class RecordDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SELECTED_RECORD =  "com.nlpproject.callrecorder.RECORD";
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

        //TODO: retrieve representedProcessingTask from savedInstanceState
        Intent intent = getIntent();
        representedProcessingTask = (ProcessingTask) intent.getSerializableExtra(SELECTED_RECORD);
        if (representedProcessingTask != null)
        {
            // TODO: fill fields on layout with details
        }

        refreshScrollView();
    }


    @Override
    public void onClick(View v) {
        if (btnDeleteRecord.equals(v)){
            deleteKeyword();
        }
    }

    private void deleteKeyword() {
        if (ProcessingTaskService.delete(representedProcessingTask)){
            finish();
        }
    }

    private void refreshScrollView(){
        List<Keyword> list = Keyword_X_ProcessingTaskService.findByProcessingTask(representedProcessingTask);
        for (Keyword keyword : list){
            KeywordListButton newButton = new KeywordListButton(getApplicationContext());
            newButton.setRepresentedKeyword(keyword);
            scrollViewLinearLayout.addView(newButton);
        }
    }
}