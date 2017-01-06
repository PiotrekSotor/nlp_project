package com.nlpproject.callrecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.RecordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.util.List;

public class RecordListActivity extends AppCompatActivity{

    LinearLayout scrollViewLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list_activity);


        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.recordListActivity_scrollViewInnerLayout);

        refreshScrollView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScrollView();
    }

    private void refreshScrollView(){
        List<ProcessingTask> list = ProcessingTaskService.getSortedList();
        scrollViewLinearLayout.removeAllViews();
        if (list == null || list.isEmpty()){
            TextView message = new TextView(getApplicationContext());
            message.setText("Empty records list");
            scrollViewLinearLayout.addView(message);
        }
        else{
            for (ProcessingTask processingTask : list){
                RecordListButton newButton = new RecordListButton(getApplicationContext());
                newButton.setContent(processingTask,null);
                scrollViewLinearLayout.addView(newButton);
            }
        }
    }
}