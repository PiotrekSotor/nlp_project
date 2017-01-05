package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "transcription")
public class Transcription extends BaseModel{

    @DatabaseField
    private
    String transcription;

    @ForeignCollectionField(eager = false)
    private
    ForeignCollection<Keyword> foundKeywords;

    @DatabaseField
    private
    String pathToRecord;

    @DatabaseField(foreign = true)
    private
    ProcessingTask processingTask;

    public String getTranscription() {
        return transcription;
    }

    public void setTranscription(String transcription) {
        this.transcription = transcription;
    }

    public ForeignCollection<Keyword> getFoundKeywords() {
        return foundKeywords;
    }

    public void setFoundKeywords(ForeignCollection<Keyword> foundKeywords) {
        this.foundKeywords = foundKeywords;
    }

    public String getPathToRecord() {
        return pathToRecord;
    }

    public void setPathToRecord(String pathToRecord) {
        this.pathToRecord = pathToRecord;
    }

    public ProcessingTask getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(ProcessingTask processingTask) {
        this.processingTask = processingTask;
    }
}
