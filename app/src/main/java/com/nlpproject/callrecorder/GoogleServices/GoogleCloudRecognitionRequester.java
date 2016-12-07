package com.nlpproject.callrecorder.GoogleServices;

import com.google.api.services.speech.v1beta1.Speech;
import com.google.api.services.speech.v1beta1.model.AsyncRecognizeRequest;
import com.google.api.services.speech.v1beta1.model.Operation;
import com.google.api.services.speech.v1beta1.model.RecognitionAudio;
import com.google.api.services.speech.v1beta1.model.RecognitionConfig;

import java.io.IOException;

/**
 * Created by Piotrek on 05.12.2016.
 *
 * GoogleCloudRecognitionRequester - wysyłanie żądania transkrypcji nagrania
 *
 * Jeszcze IN PROGRESS
 */

public class GoogleCloudRecognitionRequester {

    Speech speechService = null;

    public GoogleCloudRecognitionRequester(){
        GoogleCloudContext gcc = GoogleCloudContext.getInstance();
        speechService = new Speech.Builder(gcc.getHttpTransport(), gcc.getJsonFactory(), gcc.getCredential()).setApplicationName("NLP-proj").build();
    }


    /**
     *
     * @param fileName - sama nazwa pliku. W GoogleCloudStorage wszystko będzie w jednym kubełku
     */
    public void sendRecognitionRequest(String fileName) {

        String gcURI = "gs://bucket_name/" + fileName;

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
            Operation result = operation.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
