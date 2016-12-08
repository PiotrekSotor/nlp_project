package com.nlpproject.callrecorder.GoogleServices;

import com.google.api.client.json.GenericJson;

/**
 * Created by Piotrek on 08.12.2016.
 */

public interface RequestAsyncOperationRequester {
    void performRequestAsyncOperationResponse(GenericJson response);
}
