package com.nlpproject.callrecorder.Analyser;

import android.os.AsyncTask;

import com.nlpproject.callrecorder.Morf.MorfeuszFactory;
import com.nlpproject.callrecorder.Morf.OwnMorfeusz;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.KeywordBase;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.services.KeywordBaseService;
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
        List<KeywordBase> keywordsList;
        keywordsList = analyseForSpecificKeywords
                ? AnalyserMonitor.getInstance().getSpecificKeywordsToAnalyse()
                : KeywordBaseService.getSortedList();
        List<String> clonedKeywordList = new ArrayList<>();
        if (keywordsList != null) {
            for (KeywordBase keywordBase : keywordsList) {
                clonedKeywordList.add(keywordBase.getBase());
            }
        } else {
            return null;
        }
        List<ProcessingTask> processingTaskList = analyseForSpecificKeywords
                ? ProcessingTaskService.getSortedList()
                : ProcessingTaskService.findNotAnalysed();
        if (processingTaskList != null) {
            OwnMorfeusz morf = MorfeuszFactory.getMorfeusz();
            for (ProcessingTask processingTask : processingTaskList) {
                Map<Keyword, Integer> occurances = new HashMap<>();
                String transcription = processingTask.getTranscription();
                String[] words = transcription.split(" ");
                Map<String, List<String>> transcriptionBases = morf.getBase(words);
                for (String transcriptionWord : transcriptionBases.keySet()) {
                    for (String transcriptionWordBase : transcriptionBases.get(transcriptionWord)) {

                        List<Integer> indexes = indexOfMultiple(clonedKeywordList, transcriptionWordBase);

                        for (Integer index : indexes) {
                            if (index != -1) {
                                Keyword key = keywordsList.get(index).getKeyword();
                                if (occurances.keySet().contains(keywordsList.get(index).getKeyword())) {
                                    occurances.put(key, occurances.get(key) + 1);
                                } else {
                                    occurances.put(key, 1);
                                }
                            }
                        }
                    }
                }

                for (Keyword keyword : occurances.keySet()) {
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

        AnalyserMonitor.getInstance().
                getSpecificKeywordsToAnalyse().clear();

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        AnalyserMonitor.getInstance().autoRerun();
    }

    private <T> List<Integer> indexOfMultiple(List<T> list, T object) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).equals(object)) {
                indices.add(i);
            }
        }
        return indices;
    }
}
