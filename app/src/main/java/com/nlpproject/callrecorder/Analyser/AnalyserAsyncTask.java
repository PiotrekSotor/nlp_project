package com.nlpproject.callrecorder.Analyser;

import android.os.AsyncTask;

import com.nlpproject.callrecorder.Morf.MorfeuszMock;
import com.nlpproject.callrecorder.Morf.OwnMorfeusz;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordService;
import com.nlpproject.callrecorder.ORMLiteTools.services.Keyword_X_ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotrek on 06.01.2017.
 */
public class AnalyserAsyncTask extends AsyncTask<Void, Void, Void> {


    @Override
    protected Void doInBackground(Void... params) {
        AnalyserMonitor.getInstance().setRunning(true);
        boolean analyseForSpecificKeywords = !AnalyserMonitor.getInstance().getSpecificKeywordsToAnalyse().isEmpty();
        List<Keyword> keywordsList;
        keywordsList = analyseForSpecificKeywords
                ? AnalyserMonitor.getInstance().getSpecificKeywordsToAnalyse()
                : KeywordService.getSortedList();
        List<String> clonedKeywordList = new ArrayList<>();
        if (keywordsList != null) {
            for (Keyword keyword : keywordsList) {
                clonedKeywordList.add(keyword.getBaseWord());
            }
        }
        else {
            return null;
        }
        List<ProcessingTask> processingTaskList = analyseForSpecificKeywords
                ? ProcessingTaskService.getSortedList()
                : ProcessingTaskService.findNotAnalysed();
        if (processingTaskList != null) {
            OwnMorfeusz morf = new MorfeuszMock();
            for (ProcessingTask processingTask : processingTaskList) {
                Map<Keyword, Integer> occurances = new HashMap<>();
                String transcription = processingTask.getTranscription();
                String[] words = transcription.split(" ");
                List<String> transcriptionBases = morf.getBase(words);
                for (String transcriptionWord : transcriptionBases){
                    int index = clonedKeywordList.indexOf(transcriptionWord);
                    if (index!= -1){
                        if (occurances.keySet().contains(keywordsList.get(index))){
                            occurances.put(keywordsList.get(index), occurances.get(keywordsList.get(index))+1);
                        }
                        else{
                            occurances.put(keywordsList.get(index), 1);
                        }
                    }
                }
                for (Keyword keyword : occurances.keySet()){
                    Keyword_X_ProcessingTask newEntity = new Keyword_X_ProcessingTask();
                    Keyword_X_ProcessingTaskService.create(newEntity);
                    newEntity.setProcessingTask(processingTask);
                    newEntity.setFoundKeyword(keyword);
                    newEntity.setNumberOfMatches(occurances.get(keyword));
                    Keyword_X_ProcessingTaskService.update(newEntity);
                }
                processingTask.setAnalysed_for_keywords(true);
                ProcessingTaskService.update(processingTask);
            }
        }
        AnalyserMonitor.getInstance().getSpecificKeywordsToAnalyse().clear();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        AnalyserMonitor.getInstance().autoRerun();
    }
}
