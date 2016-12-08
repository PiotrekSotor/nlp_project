package com.nlpproject.callrecorder.GoogleServices;

import com.google.api.client.json.GenericJson;
import com.google.api.services.speech.v1beta1.Speech;
import com.google.api.services.speech.v1beta1.model.AsyncRecognizeRequest;
import com.google.api.services.speech.v1beta1.model.RecognitionAudio;
import com.google.api.services.speech.v1beta1.model.RecognitionConfig;
import com.google.api.services.speech.v1beta1.model.SpeechRecognitionResult;

import java.io.IOException;

/**
 * Created by Piotrek on 05.12.2016.
 * <p>
 * GoogleCloudRecognitionRequester - wysyłanie żądania transkrypcji nagrania
 * <p>
 * Jeszcze IN PROGRESS
 */

public class GoogleCloudRecognitionRequester implements RequestAsyncOperationRequester {

    Speech speechService = null;

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
        recognitionConfig.setEncoding("AMR_WB");
        recognitionConfig.setSampleRate(16000);
        recognitionConfig.setLanguageCode("pl-PL");
        recognitionConfig.setProfanityFilter(false);
        recognitionConfig.setMaxAlternatives(1);
        asyncRecognizeRequest.setAudio(recognitionAudio);
        asyncRecognizeRequest.setConfig(recognitionConfig);


        try {
            Speech.SpeechOperations.Asyncrecognize operation = speechService.speech().asyncrecognize(asyncRecognizeRequest);
            RequestAsyncOperation rao = new RequestAsyncOperation(operation, this);
            rao.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performRequestAsyncOperationResponse(GenericJson response) {
        if (response instanceof SpeechRecognitionResult) {
            SpeechRecognitionResult result = (SpeechRecognitionResult) response;
        }
    }
}
