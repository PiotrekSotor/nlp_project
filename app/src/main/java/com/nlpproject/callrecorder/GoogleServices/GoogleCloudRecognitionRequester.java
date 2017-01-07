package com.nlpproject.callrecorder.GoogleServices;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.api.client.json.GenericJson;
import com.google.api.services.speech.v1beta1.Speech;
import com.google.api.services.speech.v1beta1.model.AsyncRecognizeRequest;
import com.google.api.services.speech.v1beta1.model.Operation;
import com.google.api.services.speech.v1beta1.model.RecognitionAudio;
import com.google.api.services.speech.v1beta1.model.RecognitionConfig;
import com.nlpproject.callrecorder.Analyser.AnalyserMonitor;
import com.nlpproject.callrecorder.ORMLiteTools.services.ProcessingTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * Created by Piotrek on 05.12.2016.
 * <p>
 * GoogleCloudRecognitionRequester - wysyłanie żądania transkrypcji nagrania
 * <p>
 */

public class GoogleCloudRecognitionRequester implements RequestAsyncOperationRequester {

    Speech speechService = null;
    String fileName;

    public GoogleCloudRecognitionRequester() {
        GoogleCloudContext gcc = GoogleCloudContext.getInstance();
        speechService = new Speech.Builder(gcc.getHttpTransport(), gcc.getJsonFactory(), gcc.getCredential()).setApplicationName("NLP-proj").build();
    }


    /**
     * @param fileName - sama nazwa pliku
     */
    public void sendRecognitionRequest(String fileName, final Long id) {

        AsyncRecognizeRequest asyncRecognizeRequest = prepareAsyncRecognizeRequest(fileName);
        try {
            Speech.SpeechOperations.Asyncrecognize operation = speechService.speech().asyncrecognize(asyncRecognizeRequest);
            RequestAsyncOperation rao = new RequestAsyncOperation(operation, this, id);
            rao.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    private AsyncRecognizeRequest prepareAsyncRecognizeRequest(String fileName) {
        String gcURI = "gs://nlp-proj-1.appspot.com/" + fileName;

        AsyncRecognizeRequest asyncRecognizeRequest = new AsyncRecognizeRequest();
        RecognitionAudio recognitionAudio = new RecognitionAudio();
        recognitionAudio.setUri(gcURI);
        RecognitionConfig recognitionConfig = new RecognitionConfig();
        recognitionConfig.setEncoding("AMR");
        recognitionConfig.setSampleRate(8000);
        recognitionConfig.setLanguageCode("pl-PL");
        recognitionConfig.setProfanityFilter(false);
        recognitionConfig.setMaxAlternatives(1);
        asyncRecognizeRequest.setAudio(recognitionAudio);
        asyncRecognizeRequest.setConfig(recognitionConfig);
        return asyncRecognizeRequest;
    }

    private boolean isRecognitionDone(GenericJson response) {
        if (response.get("done") != null) {
            return (Boolean)response.get("done");
        }
        return false;
    }

    private String getResultFromResponse(GenericJson response) {
        String result = "";
        if (response.get("done") != null) {
            Map<String, Object> map = ((Operation) response).getResponse();
            if (map.get("results") != null) {
                List<Map<String, Object>> listResults = (List<Map<String, Object>>) map.get("results");
                Map<String, Object> mapResults = listResults.get(0);
                if (mapResults.get("alternatives") != null) {
                    List<Map<String, Object>> alternatives = (List<Map<String, Object>>) mapResults.get("alternatives");
                    if (!alternatives.isEmpty() && alternatives.get(0).get("transcript") != null) {
                        result = alternatives.get(0).get("transcript").toString();
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void performRequestAsyncOperationResponse(GenericJson response, Long id) {

        if (response instanceof Operation) {
            if (isRecognitionDone(response)) {
                String transcription = getResultFromResponse(response);
                Log.e("Recognition rest", transcription);
                processingTaskUpdateRecognitionResults(id, transcription);
                AnalyserMonitor.getInstance().invokeAnalyse();
            }
            else {
                try {
                    processingTaskUpdateRecognitionProgress((Operation) response, id);

                    Thread.sleep(5000);
                    Speech.Operations.Get getResult = speechService.operations().get((String) response.get("name"));
                    RequestAsyncOperation rao = new RequestAsyncOperation(getResult, this, id);
                    rao.execute();

                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processingTaskUpdateRecognitionProgress(Operation response, Long id) {
        ProcessingTask task = ProcessingTaskService.find(id);
        if (task != null){
            if (response.getMetadata() != null){
                Map<String, Object> metadata = response.getMetadata();
                Integer progressPercent= (Integer)metadata.get("progress_percent");
                task.setRecognitionProgress(progressPercent);
            }
            ProcessingTaskService.update(task);
        }
    }

    private void processingTaskUpdateRecognitionResults(Long id, String transcription) {
        ProcessingTask processingTask = ProcessingTaskService.find(id);
        if (processingTask != null) {
            processingTask.setRecognitionProgress(100);
            processingTask.setDone(true);
            processingTask.setTranscription(transcription);
            processingTask.setRecognizedDate(new Date());
            processingTask.setAnalysed_for_keywords(false);
            ProcessingTaskService.update(processingTask);
        }
    }
}
