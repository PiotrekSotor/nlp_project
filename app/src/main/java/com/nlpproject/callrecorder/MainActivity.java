package com.nlpproject.callrecorder;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.nlpproject.callrecorder.GoogleServices.GoogleCloudRecognitionRequester;
import com.nlpproject.callrecorder.GoogleServices.GoogleCloudStorageSender;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST = 42;

    TextView txt;
    Button btn_testGCStorage;
    Button btn_testGCSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt = (TextView) findViewById(R.id.txt);

        if (checkPermissions())
            txt.setText("Got permissions!");
        else {
            txt.setText("Didn't get permissions. Requesting...");

            ActivityCompat.requestPermissions(this,
                    new String[]{
                            Manifest.permission.READ_PHONE_STATE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.RECORD_AUDIO
                    },
                    MY_PERMISSIONS_REQUEST);
        }

        startService(new Intent(this, RecordingService.class));


        // test api wysyłania pliku
        btn_testGCStorage = (Button) findViewById(R.id.testGoogleCloudStorage);
        btn_testGCStorage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Random rand = new Random();
                    File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Call_Recorder/"+Integer.toString(rand.nextInt())+".txt");
                    FileWriter fw = new FileWriter(file);
                    fw.write("Ala ma kota");
                    fw.close();
                    Log.d("MainActivity","file path: " + file.getAbsolutePath());
                    GoogleCloudStorageSender gcss = new GoogleCloudStorageSender();
                    gcss.uploadFile(file.getAbsolutePath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_testGCSpeech = (Button)findViewById(R.id.testGoogleCloudSpeech);
        btn_testGCSpeech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GoogleCloudRecognitionRequester gcrr = new GoogleCloudRecognitionRequester();
                gcrr.sendRecognitionRequest("test1.3gp");
            }
        });
    }

    private boolean checkPermissions() {
        // TODO: Do it cleaner
        int permissionCheckPhone = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        int permissionCheckStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionCheckRecord = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        return permissionCheckStorage == PackageManager.PERMISSION_GRANTED &&
                permissionCheckPhone == PackageManager.PERMISSION_GRANTED &&
                permissionCheckRecord == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        // TODO: Do it cleaner
        if (requestCode == MY_PERMISSIONS_REQUEST)
            if (grantResults.length == 3 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[2] == PackageManager.PERMISSION_GRANTED)
                txt.setText("Got permissions!");
    }
}