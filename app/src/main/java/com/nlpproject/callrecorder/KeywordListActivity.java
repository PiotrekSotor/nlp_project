package com.nlpproject.callrecorder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nlpproject.callrecorder.ORMLiteTools.ScrollViewButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordService;

import java.util.List;

public class KeywordListActivity extends AppCompatActivity implements View.OnClickListener{

    ScrollView scrollView;
    TextView textViewNewKeyword;
    Button btnNewKeyword;
    LinearLayout scrollViewLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_list_activity);

//        scrollView = (ScrollView) findViewById(R.id.)
        btnNewKeyword = (Button) findViewById(R.id.btn_addKeyword);
        btnNewKeyword.setOnClickListener(this);
        textViewNewKeyword = (TextView) findViewById(R.id.editText_newKeyword);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.scrollViewInnerLayout);

        refreshScrollView();
    }


    @Override
    public void onClick(View v) {
        if (btnNewKeyword.equals(v)){
            String input = textViewNewKeyword.getText().toString();
            if (input.isEmpty() || input.contains(" "))
                return;
            input = input.toLowerCase();
            addNewKeyword(input);
        }
    }

    private void addNewKeyword(String newKeyword) {

    }

    private void refreshScrollView(){
        List<Keyword> list = KeywordService.getSortedList();
        for (Keyword keyword : list){
            ScrollViewButton newButton = new ScrollViewButton(getApplicationContext());
            newButton.setText(keyword.getOriginalWord());
            scrollViewLinearLayout.addView(newButton);
        }
    }
}