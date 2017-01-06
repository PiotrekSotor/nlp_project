package com.nlpproject.callrecorder;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.RecordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordService;
import com.nlpproject.callrecorder.ORMLiteTools.services.Keyword_X_ProcessingTaskService;

import java.util.List;

public class KeywordDetailsActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SELECTED_KEYWORD =  "com.nlpproject.callrecorder.KEYWORD";
    Button btnDeleteKeyword;
    LinearLayout scrollViewLinearLayout;
    TextView textViewKeyword;

    Keyword representedKeyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_details_activity);

        btnDeleteKeyword = (Button) findViewById(R.id.btn_deleteRecord);
        btnDeleteKeyword.setOnClickListener(this);
        textViewKeyword = (TextView) findViewById(R.id.editText_keyword);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.RecordDetailsActivity_scrollViewInnerLayout);

        //TODO: retrieve representedProcessingTask from savedInstanceState
        Intent intent = getIntent();
        representedKeyword = (Keyword) intent.getSerializableExtra(SELECTED_KEYWORD);
        if (representedKeyword != null)
        {
            textViewKeyword.setText(representedKeyword.getOriginalWord());
        }

        refreshScrollView();
    }


    @Override
    public void onClick(View v) {
        if (btnDeleteKeyword.equals(v)){
            deleteKeyword();
        }
    }

    private void deleteKeyword() {
        if (KeywordService.delete(representedKeyword)){
            finish();
        }
    }

    private void refreshScrollView(){
        List<ProcessingTask> list = Keyword_X_ProcessingTaskService.findByKeyword(representedKeyword);
        for (ProcessingTask processingTask : list){
            RecordListButton newButton = new RecordListButton(getApplicationContext());
            newButton.setRepresentedRecord(processingTask);
            scrollViewLinearLayout.addView(newButton);
        }
    }
}