package com.nlpproject.callrecorder.Analyser;

import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Piotrek on 06.01.2017.
 */

public class AnalyserMonitor {
    private Boolean isRunning = false;
    private Boolean analyzeRequested = false;
    private Boolean analyzeForKeywordRequested = false;
    protected List<Keyword> specificKeywordsToAnalyse = new ArrayList<>();
    private static AnalyserMonitor instance= null;

    private AnalyserMonitor(){}

    public static AnalyserMonitor getInstance(){
        if (instance == null){
            instance = new AnalyserMonitor();
        }
        return instance;
    }
    public void invokeAnalyse(){

        if (isRunning){
            analyzeRequested = true;
        }
        else{
            analyzeRequested = false;
            AnalyserAsyncTask task = new AnalyserAsyncTask();
            task.execute();
        }
    }

    public void invokeAnalyseForKeyword(Keyword keyword){
        if (keyword != null){
            analyzeForKeywordRequested = true;
            getSpecificKeywordsToAnalyse().add(keyword);
        }
        if (!isRunning){
            analyzeForKeywordRequested=false;
            AnalyserAsyncTask task = new AnalyserAsyncTask();
            task.execute();
        }
    }


    protected void setRunning(Boolean running) {
        isRunning = running;
    }

    protected void autoRerun(){
        isRunning = false;
        if (analyzeForKeywordRequested == true){
            invokeAnalyseForKeyword(null);
        }
        else if (analyzeRequested == true){
            analyzeRequested = false;
            invokeAnalyse();
        }
    }

    protected List<Keyword> getSpecificKeywordsToAnalyse() {
        return specificKeywordsToAnalyse;
    }
}
