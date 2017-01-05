package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Piotrek on 21.12.2016.
 */
@DatabaseTable(tableName = "processing_task")
public class ProcessingTask extends BaseModel implements Comparable {

    @DatabaseField
    private String google_cloud_recognition_task_id;
    @DatabaseField
    private String transcription;
    @DatabaseField
    private String file_path;
    @DatabaseField
    private Date record_date;
    @DatabaseField
    private Date recognized_date;
    @DatabaseField
    private Date upload_date;
    @DatabaseField
    private Boolean done;
    @DatabaseField
    private Integer recognition_progress;

    public String getTranscription() {
        return transcription==null ? "" : transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getFilePath() {
        return file_path == null ? "" : file_path;
    }

    public void setFilePath(String file_path) {
        this.file_path = file_path;
    }

    public Date getRecordDate() {
        return record_date;
    }

    public void setRecordDate(Date record_date) {
        this.record_date = record_date;
    }

    public Date getRecognizedDate() {
        return recognized_date;
    }

    public void setRecognizedDate(Date recognized_date) {
        this.recognized_date = recognized_date;
    }

    @Override
    public int compareTo(Object another) {
        if (another == null)
            return 1;
        if (another instanceof ProcessingTask) {
            if (this.getRecordDate().getTime() < ((ProcessingTask) another).getRecordDate().getTime()) {
                return 1;
            } else {
                return -1;
            }
        }
        else{
            return -1;
        }

    }


    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getRecognitionProgress() {
        return recognition_progress == null ? 0 : recognition_progress;
    }

    public void setRecognitionProgress(Integer recognition_progress) {
        this.recognition_progress = recognition_progress;
    }

    public Date getUploadDate() {
        return upload_date;
    }

    public void setUploadDate(Date upload_date) {
        this.upload_date = upload_date;
    }

    public String getGoogleCloudRecognitionTaskId() {
        return google_cloud_recognition_task_id == null ? "" : google_cloud_recognition_task_id;
    }

    public void setGoogleCloudRecognitionTaskId(String google_cloud_recognition_task_id) {
        this.google_cloud_recognition_task_id = google_cloud_recognition_task_id;
    }
}
