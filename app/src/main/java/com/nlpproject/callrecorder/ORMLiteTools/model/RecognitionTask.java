package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * Created by Piotrek on 21.12.2016.
 */
@DatabaseTable(tableName = "recognition_task")
public class RecognitionTask implements Comparable {

    @DatabaseField
    private Long id;
    @DatabaseField
    private String transcription;
    @DatabaseField
    private String file_path;
    @DatabaseField
    private Date record_date;
    @DatabaseField
    private Date recognized_date;
    @DatabaseField
    private Boolean done;
    @DatabaseField
    private Integer progress;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public String getFilePath() {
        return file_path;
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
        if (another instanceof RecognitionTask) {
            if (this.getRecordDate().getTime() < ((RecognitionTask) another).getRecordDate().getTime()) {
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

    public Integer getProgress() {
        return progress;
    }

    public void setProgress(Integer progress) {
        this.progress = progress;
    }
}
