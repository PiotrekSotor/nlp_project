package com.nlpproject.callrecorder.GoogleServices;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.api.client.googleapis.services.json.AbstractGoogleJsonClientRequest;
import com.google.api.client.json.GenericJson;


import java.io.IOException;
import java.net.UnknownHostException;

/**
 * Created by Piotrek on 08.12.2016.
 */

public class RequestAsyncOperation extends AsyncTask<Void, Void, GenericJson> {

    private AbstractGoogleJsonClientRequest<GenericJson> request;
    private RequestAsyncOperationRequester requestAsyncOperationRequester = null;
    private Long id = null;

    /**
     * @param m_request - request do wykonania
     * @param @Nullable m_requester - obiekt mający otrzymać response
     */
    public RequestAsyncOperation(AbstractGoogleJsonClientRequest m_request, @Nullable RequestAsyncOperationRequester m_requester, @Nullable Long m_id) {
        request = m_request;
        requestAsyncOperationRequester = m_requester;
        id = m_id;
    }

    @Override
    protected GenericJson doInBackground(Void... params) {

        GenericJson response = null;
        request.setDisableGZipContent(true);
        try {
            response = request.execute();
        } catch (UnknownHostException e) {
            // no internet connection
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    @Override
    protected void onPostExecute(GenericJson response) {
        if (response != null) {
            Log.e("Response class", response.getClass().toString());
            Log.e("Response", response.toString());
            if (requestAsyncOperationRequester != null) {
                requestAsyncOperationRequester.performRequestAsyncOperationResponse(response, id);
            }
        }
    }
}