package com.nlpproject.callrecorder.GoogleServices;

import android.util.Log;

import com.google.api.client.json.GenericJson;
import com.google.api.services.speech.v1beta1.Speech;
import com.google.api.services.speech.v1beta1.model.AsyncRecognizeRequest;
import com.google.api.services.speech.v1beta1.model.Operation;
import com.google.api.services.speech.v1beta1.model.RecognitionAudio;
import com.google.api.services.speech.v1beta1.model.RecognitionConfig;
import com.nlpproject.callrecorder.ORMLiteTools.RecognitionTaskService;
import com.nlpproject.callrecorder.ORMLiteTools.model.RecognitionTask;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;


/**
 * Created by Piotrek on 05.12.2016.
 * <p>
 * GoogleCloudRecognitionRequester - wysyłanie żądania transkrypcji nagrania
 * <p>
 * Jeszcze IN PROGRESS
 */

public class GoogleCloudRecognitionRequester implements RequestAsyncOperationRequester {

    Speech speechService = null;
    String fileName;

    public GoogleCloudRecognitionRequester() {
        GoogleCloudContext gcc = GoogleCloudContext.getInstance();
        speechService = new Speech.Builder(gcc.getHttpTransport(), gcc.getJsonFactory(), gcc.getCredential()).setApplicationName("NLP-proj").build();
    }


    /**
     * @param fileName - sama nazwa pliku. W GoogleCloudStorage wszystko będzie w jednym kubełku
     */
    public void sendRecognitionRequest(String fileName) {

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

        this.fileName = fileName;

        try {
            Speech.SpeechOperations.Asyncrecognize operation = speechService.speech().asyncrecognize(asyncRecognizeRequest);
            RequestAsyncOperation rao = new RequestAsyncOperation(operation, this);
            rao.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean doesResponseContainResults(GenericJson response) {
        String result = null;
        if (response.get("done") != null) {
            Map<String, Object> map = ((Operation) response).getResponse();
            if (map.get("results") != null) {
                List<Map<String, Object>> listResults = (List<Map<String, Object>>) map.get("results");
                Map<String, Object> mapResults = listResults.get(0);
                if (mapResults.get("alternatives") != null) {
                    List<Map<String, Object>> alternatives = (List<Map<String, Object>>) mapResults.get("alternatives");
                    if (!alternatives.isEmpty() && alternatives.get(0).get("transcript") != null) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private String getResultFromResponse(GenericJson response) {
        String result = null;
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
    public void performRequestAsyncOperationResponse(GenericJson response) {

        if (response instanceof Operation) {
            if (doesResponseContainResults(response)) {
                String transcription = getResultFromResponse(response);
                Log.e("Recognition rest", transcription);
                try {
//                    RecognitionTask recognitionTask = RecognitionTaskService.findRecognitionTaskById(Long.parseLong(((Operation) response).getName()));
                    RecognitionTask recognitionTask = new RecognitionTask();
                    recognitionTask.setId(Long.parseLong(((Operation) response).getName()));
                    recognitionTask.setProgress(100);
                    recognitionTask.setDone(true);
                    recognitionTask.setTranscription(transcription);
                    recognitionTask.setFilePath(fileName);
                    RecognitionTaskService.createRecognitionTask(recognitionTask);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
//                    RecognitionTask recognitionTask = RecognitionTaskService.findRecognitionTaskById(Long.parseLong(((Operation) response).getName()));

                    Thread.sleep(5000);
                    Speech.Operations.Get getResult = speechService.operations().get((String) response.get("name"));
                    RequestAsyncOperation rao = new RequestAsyncOperation(getResult, this);
                    rao.execute();

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
