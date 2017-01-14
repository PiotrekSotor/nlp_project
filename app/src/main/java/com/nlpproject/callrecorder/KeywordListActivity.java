package com.nlpproject.callrecorder;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nlpproject.callrecorder.Analyser.AnalyserMonitor;
import com.nlpproject.callrecorder.Morf.MorfeuszFactory;
import com.nlpproject.callrecorder.Morf.OwnMorfeusz;
import com.nlpproject.callrecorder.ORMLiteTools.KeywordListButton;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.KeywordBase;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordBaseService;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordService;

import java.util.List;

public class KeywordListActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textViewNewKeyword;
    Button btnNewKeyword;
    LinearLayout scrollViewLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.keyword_list_activity);

        btnNewKeyword = (Button) findViewById(R.id.btn_addKeyword);
        btnNewKeyword.setOnClickListener(this);
        textViewNewKeyword = (TextView) findViewById(R.id.editText_newKeyword);
        scrollViewLinearLayout = (LinearLayout) findViewById(R.id.keywordListActivity_scrollViewInnerLayout);

        refreshScrollView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshScrollView();
    }

    @Override
    public void onClick(View v) {
        if (btnNewKeyword.equals(v)){
            String input = textViewNewKeyword.getText().toString();
            if (input.isEmpty() || input.contains(" "))
                return;
            input = input.toLowerCase();
            if (!KeywordService.findByOriginal(input).isEmpty()){
                Toast.makeText(this, "Keyword already exists",Toast.LENGTH_SHORT).show();
                return;
            }
            Keyword inserted = addNewKeyword(input);
            textViewNewKeyword.setText("");
            refreshScrollView();

            AnalyserMonitor.getInstance().invokeAnalyseForKeyword(inserted);

        }
    }

    private Keyword addNewKeyword(String newKeyword) {
        OwnMorfeusz morfeusz = MorfeuszFactory.getMorfeusz();
        Keyword keyword = new Keyword();
        keyword.setOriginalWord(newKeyword);
        Long id = KeywordService.create(keyword);
        findBasesToKeyword(keyword);
        return KeywordService.findId(id);
    }

    private void findBasesToKeyword(Keyword keyword) {
        GetBasesAsyncTask gbas = new GetBasesAsyncTask();
        gbas.execute(keyword);
    }

    private void refreshScrollView(){
        List<Keyword> list = KeywordService.getSortedList();
        scrollViewLinearLayout.removeAllViews();
        if (list == null || list.isEmpty()){
            TextView message = new TextView(getApplicationContext());
            message.setText("Empty keyword list");
            scrollViewLinearLayout.addView(message);
        }
        for (Keyword keyword : list){
            KeywordListButton newButton = new KeywordListButton(getApplicationContext());
            newButton.setRepresentedKeyword(keyword);
            scrollViewLinearLayout.addView(newButton);
        }
    }
    class GetBasesAsyncTask extends AsyncTask<Keyword, Void, Void>{

        @Override
        protected Void doInBackground(Keyword... params) {
            OwnMorfeusz morfeusz = MorfeuszFactory.getMorfeusz();
            List<String> bases = morfeusz.getBase(params[0].getOriginalWord());
            if (bases != null) {
                for (String base : bases) {
                    KeywordBase keywordBase = new KeywordBase();
                    keywordBase.setBase(base);
                    KeywordBaseService.create(keywordBase);
                    keywordBase.setKeyword(params[0]);
                    KeywordBaseService.update(keywordBase);
                }
            }
            return null;
        }
    }
}