package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "keyword_x_processingTask")
public class Keyword_X_ProcessingTask extends BaseModel{

    @DatabaseField (foreign = true)
    private
    Keyword foundKeywords;

    @DatabaseField(foreign = true)
    private
    ProcessingTask processingTask;


    public ProcessingTask getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(ProcessingTask processingTask) {
        this.processingTask = processingTask;
    }

    public Keyword getFoundKeywords() {
        return foundKeywords;
    }

    public void setFoundKeywords(Keyword foundKeywords) {
        this.foundKeywords = foundKeywords;
    }
}
