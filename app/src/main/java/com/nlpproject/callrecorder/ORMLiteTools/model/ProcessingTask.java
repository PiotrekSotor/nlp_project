package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.List;

/**
 * Created by Piotrek on 21.12.2016.
 */
@DatabaseTable(tableName = "processing_task")
public class ProcessingTask extends BaseModel{

    public static final String ANALYSED_FOR_KEYWORDS_FIELD_NAME = "analysed_for_keywords";

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
    @DatabaseField
    private String caller_number;
    @DatabaseField
    private Boolean analysed_for_keywords;

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

    public String getCaller_number() {
        return caller_number==null ? "" : caller_number;
    }

    public void setCaller_number(String caller_number) {
        this.caller_number = caller_number;
    }

    public Boolean getAnalysed_for_keywords() {
        return analysed_for_keywords;
    }

    public void setAnalysed_for_keywords(Boolean analysed_for_keywords) {
        this.analysed_for_keywords = analysed_for_keywords;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
