package com.nlpproject.callrecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.nlpproject.callrecorder.ORMLiteTools.RecordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.util.List;

public class RecordListActivity extends AppCompatActivity{

    LinearLayout scrollViewLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_list_activity);


        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.RecordDetailsActivity_scrollViewInnerLayout);

        refreshScrollView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScrollView();
    }

    private void refreshScrollView(){
        List<ProcessingTask> list = ProcessingTask.getSortedList();
        scrollViewLinearLayout.removeAllViews();
        for (ProcessingTask processingTask : list){
            RecordListButton newButton = new RecordListButton(getApplicationContext());
            newButton.setRepresentedRecord(processingTask);
            scrollViewLinearLayout.addView(newButton);
        }
    }
}