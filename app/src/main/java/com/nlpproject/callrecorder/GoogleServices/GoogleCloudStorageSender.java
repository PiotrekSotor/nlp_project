package com.nlpproject.callrecorder.GoogleServices;

/*
 * BEFORE RUNNING:
 * ---------------
 * 1. If not already done, enable the Cloud Storage JSON API
 *    and check the quota for your project at
 *    https://console.developers.google.com/apis/api/storage
 * 2. This sample uses Application Default Credentials for authentication.
 *    If not already done, install the gcloud CLI from
 *    https://cloud.google.com/sdk/ and run
 *    'gcloud beta auth application-default login'
 * 3. Install the Java client library on maven or gradle. Check installation
 *    instructions at https://github.com/google/google-api-java-client.
 *    On other build systems, you can add the jar files to your project from
 *    https://developers.google.com/resources/api-libraries/download/storage/v1/java
 */

import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.GenericJson;
import com.google.api.services.storage.Storage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

/**
 *  GoogleCloudStorageSender - wysyłanie plików do GoogleCloudStorage
 *      Do szczęścia potrzebuje tylko ścieżki do pliku
 */

public class GoogleCloudStorageSender implements RequestAsyncOperationRequester {

    private Storage storageService;
    private final String bucketName = "nlp-proj-1.appspot.com";

    public GoogleCloudStorageSender() {

        GoogleCloudContext gcc = GoogleCloudContext.getInstance();
        storageService = new Storage.Builder(gcc.getHttpTransport(), gcc.getJsonFactory(), gcc.getCredential()).setApplicationName("NLP-proj").build();
    }

    /**
     * Jeszcze nie bawiłem się wydobyciem informacji o powodzeniu wysyłania
     * @param filePath - ścieżka do pliku
     * @throws IOException - błędna ścieżka/nie można otworzyć pliku
     */
    public void uploadFile(final String filePath) throws IOException {

        File uploadFile = new File(filePath);
        InputStream contentStream = new FileInputStream(uploadFile);

        String contentType = URLConnection.guessContentTypeFromStream(contentStream);
        InputStreamContent content = new InputStreamContent(contentType, contentStream);

        Storage.Objects.Insert insert = storageService.objects().insert(bucketName, null, content);
        insert.setName(uploadFile.getName());

        RequestAsyncOperation rao = new RequestAsyncOperation(insert, this);
        rao.execute();
    }

    @Override
    public void performRequestAsyncOperationResponse(GenericJson response) {
        // TODO: implementation
    }
}



