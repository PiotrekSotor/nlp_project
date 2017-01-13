package com.nlpproject.callrecorder.GoogleServices;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Collections;

/**
 * Created by Piotrek on 05.12.2016.
 *
 * GoogleCloudContext - zawiera mechanizmy związane z połączeniem do GoogleCloud
 *      Na razie jako singleton, choć trzeba sprawdzić jak długi czas można korzystać z raz utworzonych tutaj obiektów
 *      (czy nie ma timeoutów czy czegoś) i wtedy przerobić na inicjalizacje przy każdym dostępie
 *
 * CredentialsHardcoded - zawiera JSON z kluczem autoryzującym aplikacje w GoogleCloud.
 */

public class GoogleCloudContext {
    private GoogleCredential credential = null;
    private HttpTransport httpTransport = null;
    private JsonFactory jsonFactory = null;
    private static GoogleCloudContext gcc = null;

    private GoogleCloudContext(){
        try {
            credential = GoogleCredential.fromStream(new CredentialsHardcoded().getInputStream());
            httpTransport = new com.google.api.client.http.javanet.NetHttpTransport();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (getCredential().createScopedRequired()) {
            credential = getCredential().createScoped(Collections.singletonList("https://www.googleapis.com/auth/cloud-platform"));
        }
        jsonFactory = JacksonFactory.getDefaultInstance();
    }

    public static GoogleCloudContext getInstance(){
        if (gcc == null){
            gcc = new GoogleCloudContext();
        }
        return gcc;
    }

    public GoogleCredential getCredential() {
        return credential;
    }

    public HttpTransport getHttpTransport() {
        return httpTransport;
    }

    public JsonFactory getJsonFactory() {
        return jsonFactory;
    }


    class CredentialsHardcoded {
        static final String serviceKeyContent = "{\n" +
                "  \"type\": \"service_account\",\n" +
                "  \"project_id\": \"nlp-proj-155517\",\n" +
                "  \"private_key_id\": \"036dbef8cd005cd2c255e181b2ef9c3d8f761ac6\",\n" +
                "  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQDCauuNwxnZFo8Z\\nbgP8Q4+IK/sg4EMNPAJOOaJ7te6HAFJekpZoApaGxqCK1r7jqncNNH6hza5gQfzO\\nFb0tICYvkKLMPx0uwrxgv8DpY141q5TYhYY1YWRjITEn42Nj6ofV6H0G5tWXnsU6\\nOWVIpJAWhj4xoXQ3sU7FAL2izBLyDQX9VQ8L57j79V/mnWl5o7EXbE0opmG7l0Vm\\nmjCtF6zLuwVxht+/zB/Ifen+E7WcmOK1PVL5/Ft9JfoxlrmH7HqYh2sGJPhWUPrM\\nqCGK3Z+Ce9rDYeVtIAoZL94yNT4KJ+jEv4j1sbezW8ZiBV+azRi8vyV2nEvZ23fF\\nUjxitndnAgMBAAECggEADf4+GAT73kkmYZEujkCamxV3TMV3bgNssw6o6DiPA359\\nbzsjwja1eNvhAD02nhJN4VBu8Nxp+ZmmRXsBE7dlqDTD0umUAyKXBmumyeWSCVDh\\nNLhfkYYCALSJ4jIWnEFsJ3RFU3LX2sLdtTQeWx0lFcnxvga946hKYBmOjKIdlvhC\\noWITkt4Qn7noQYuLPgg08W/13L3sygQstMiWqj1gdUx0ozS1rhzcCo68iZfWtMTQ\\nAROwiK5Bun0hFCcWNhtvYY04FFRgXFgRSEiqPOzw1Zb0l2RjpI/IoP4BUsEJoMpV\\nFYiEsUMKPQPXdF2nD63HeJqAuB0gi0Ufd9O9iI+b2QKBgQD8z0BuCO4vrYwfdCs8\\nlswjpCy9tTA/cu/mcpFZR6D1/ZG5utFD55qQ97+AyfVWRehrPvZd1A/GrE2wm5Ji\\n00bMv0yA1aPbSfUJHrYurDfijF1S8gj4Bq8tOX370QclqWIP3xlyfJmCT+q2ijsI\\nIb7sjmRHw6b8AgnCwJVw//kxfQKBgQDE3wXCxY8HSE3Ki1A2dgCxIEWfXmph5L3M\\n2xuYV4N7/gigqtv8L8JK2DDrVFXuNAkouDvoRACH95pHPK02dSsbXqbho5cOWxWS\\n5uEKt53F8RVZSbkmObKhPcSJYw9bTfFTZVFNkiDdaTCriZQ1pMC39uP+SG5bRdid\\nJEIRAALhswKBgQDknYL1Powuf3FkjEgBLZkDbharo2szJXw+WEKKixTK/vhGDePp\\n1UpYaEI8c+Wxk9xfB3wBU6DEl9JVsAnL5qVyyFeldg0MZC/7hFNZl5GeBDLsrEVi\\nDaFic0gzbU5aR3ePRMAdYQHs7tohIpXJf8LI5udWYl7iK/GptUAvBLvTfQKBgHEY\\nsgWHWrhTSCUwTR9MQO+AW57HLZQZRRP3V2338Ff9wtUR8ph4k0RN2CSI6WiTSiOz\\nWu7+idOSYXXFHLipLN9nS7VaNkAQAV/H9MYkX6XD7oDBwnR8Nus8QbivSLYqG5XO\\nIZvPc8PeVxBkU9lRUUkdszJ0R7l1cZAssSt09zLTAoGAcv/sLpJjO7MsIYOasoj7\\ncPbQRwJoga64i84yGeKSeSytHvGmqMfOB8d8aReC2fXNXvYFjQnTcizjvxi3fgtW\\nqq5wcewdj0h36CyBzqoUidr70nDaPlGkWALA5s6RKB9PxxA3VyPTOiAIJeM+Ny3U\\nTE5Cu4I7RM72f3j5aLWVodw=\\n-----END PRIVATE KEY-----\\n\",\n" +
                "  \"client_email\": \"258167214909-compute@developer.gserviceaccount.com\",\n" +
                "  \"client_id\": \"102905297797234247894\",\n" +
                "  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",\n" +
                "  \"token_uri\": \"https://accounts.google.com/o/oauth2/token\",\n" +
                "  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",\n" +
                "  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/258167214909-compute%40developer.gserviceaccount.com\"\n" +
                "}";

        public CredentialsHardcoded() {
        }

        public InputStream getInputStream() {
            InputStream stream = null;
            try {
                stream = new ByteArrayInputStream(serviceKeyContent.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return stream;
        }
    }
}
