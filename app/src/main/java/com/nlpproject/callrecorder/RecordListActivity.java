package com.nlpproject.callrecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.RecordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.BaseService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.util.List;

public class RecordListActivity extends AppCompatActivity{

    public static final int MY_PERMISSIONS_REQUEST = 42;
    LinearLayout scrollViewLinearLayout;
    Button btn_keywordListStartActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_list_activity);
        BaseService.initServices(getApplicationContext());
        startService(new Intent(this, RecordingService.class));
        initPermitions();

        btn_keywordListStartActivity = (Button) findViewById(R.id.btn_runKeywordListActivity);
        btn_keywordListStartActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RecordListActivity.this, KeywordListActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.recordListActivity_scrollViewInnerLayout);

        refreshScrollView();
    }

    private void initPermitions() {
        if (!checkPermissions())
            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO,
                            Manifest.permission.INTERNET
                    },
                    MY_PERMISSIONS_REQUEST);
        }

    private boolean checkPermissions() {
        int permissionCheckPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionCheckStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckRecord = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        int permissionCheckInternet = ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET);

        return permissionCheckStorage == PackageManager.PERMISSION_GRANTED &&
                permissionCheckPhone == PackageManager.PERMISSION_GRANTED &&
                permissionCheckInternet == PackageManager.PERMISSION_GRANTED &&
                permissionCheckRecord == PackageManager.PERMISSION_GRANTED;
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