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
 *      Mam nadzieje, że GoogleCloud nie będzie wymagała osobnych kluczy dla każdego AndroidStudio buildującego aplikację
 *      (W czasie wyrabiania klucza chyba musiałem podać hashowy odcisk AndroidStudio)
 *      Wtedy trzeba będzie to zmienić w jakiś resource ciągnięty z pliku w czasie buildowania
 *          (Do momentu wcomitowania na jednym kompie i pulla na innym nie mam jak tego sprawdzić)
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
        static final String serviceKeyContent = "{  \"type\": \"service_account\",  \"project_id\": \"nlp-proj-1\",  \"private_key_id\": \"ae07f347d2a71bc6d39cc9b5e67b17fb0b0cd59c\",  \"private_key\": \"-----BEGIN PRIVATE KEY-----\\nMIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCz2penSourlYEF\\n+HLq+41oupnd+AqoZDqkj8qlqR1akRbfDGLqoeN30RYi9rMzKbalIlEyqswsgOfE\\nRB2Y8ZrL1n08cD6vbxphJUnzVe/d83/slX0AmLSIdpthVHP2eY0FhBmksx7sZ1iN\\n/dabHlct2SAFWC/kcIBHgl6dPMlIFsD2cZHhCBGJI+mTQG9pWDWXUb0PkPEm5DaO\\n8h7MtBdZEFRqwPtpC5oqPDYDU5BT7l/EEA/E+6TC3+c3imEWmc4K/9EnmjlsZwJI\\nehdZbQBSrD+PH1iAT6mplgAHDBEdTKLphwRUzR8UDGRgxABbP4a+hJIIfvSyWqFP\\n1MumQQABAgMBAAECggEANDBE+1VFTCfYxau/ZPJTqUUkauWT6iMTZTcYXZPCIcqi\\nMVz9wwnw4I66drDRdRIwnqBjoWkwT+Wj2y7My9mnymhV9Ni5R9zccb4JRGP2c5g5\\n0aPbHvYwxLxuAAMqlyXURvp7GnCjk4O1jZZP1LksIPC1OvfJBeejQZzR6Zx+57y9\\n/vVIBFSm48Rcg15HYa1Vs3hbQ9GGd7VFuMcbaphFYBxL73Y98UJpIj5hxIolLZpd\\ncjjTiuV3n5V2HIyeLsoUIPlxIbzPcqDgHC2SBiWQ/QV99mlvWFGTQA49Du2DQGy8\\nYtwie745fNoHbdN9PcGC1fehSvg7SoIOvKlR2SqtJQKBgQDYTtyOpKeGRJX/kdTX\\nXgz6FGTMvoj+Z+zId0nWCLFhhG5fupJLBaRnIDpJc72wWNTORXn3r06ioxPiWaZ7\\nmWHMVTQ8gX1QJ/1wPsQmpuqi2fmqLyqT4Q2Ldl1sy2/kuyXyi55l3K3URPUJhqBO\\nwlutgtHtMhtsGdgiMFF5kZ8dXwKBgQDU20jVZAavQjH3oeLF73j04X4nK5xKqSmD\\n2ILYRVBYDTrTWNPRSCN39DFV93Fzl1QO+dnSoFgSYpsd8hYZN3vlE5aYHpvdjLlV\\njddLom2XjO/7dKsRoEFpCc5xKMGNzl6SHEok28F5lk2Wiv/ihuibJESvi3suopZ7\\n2w9NJad+nwKBgDPXer7pMYlZiMWu0t8nGF8bYik8BSNMDxDgnh6hCLpBfh6NXaCD\\nYqqgq7eM+Mp6D868EgZNyr/p68sVB3SaBzlstk9GDZ2XaANt158X5CZRraefFEpK\\n5u8k2DFJ9LBSNj8SPU1WfYbhXAUDcgpORKCqONvrZW0cKJPRFsXXI9ZrAoGBAMVj\\n4UP6MXS/VyUEXA8gRzQYFGvwtkxKWTRqSNSUWrB8/EtX+X3/ftzTeBj3kZ8W+52z\\nt3rqtSG/jYeUo0eZO2yw4JuK7xHiWVBsa/Y415aN3VJ729RJn77vmCE6IVwGv46R\\na3SI0f+WEbeEiU0hvH16gbOlDoOtxQ1wlNz/CTeTAoGAAJEwXcupaTGjb0Zk6NnD\\nqruHVBDs5OfzlSjs4o9mmpq9bvKUvQQ7p5q+hnoJ5bWS1FOJ9BwbfewGkYOIRwAp\\npTglmhQGNCMAXeNMd83mMT9M2Y0/4p6fYjUWCnaXjEobqHZypYKpv609wUwdb76b\\nTRRrKlF/UguOQxjDWpYxeR4=\\n-----END PRIVATE KEY-----\\n\",  \"client_email\": \"nlp-proj-1@appspot.gserviceaccount.com\",  \"client_id\": \"104258885627965099523\",  \"auth_uri\": \"https://accounts.google.com/o/oauth2/auth\",  \"token_uri\": \"https://accounts.google.com/o/oauth2/token\",  \"auth_provider_x509_cert_url\": \"https://www.googleapis.com/oauth2/v1/certs\",  \"client_x509_cert_url\": \"https://www.googleapis.com/robot/v1/metadata/x509/nlp-proj-1%40appspot.gserviceaccount.com\"}";

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
