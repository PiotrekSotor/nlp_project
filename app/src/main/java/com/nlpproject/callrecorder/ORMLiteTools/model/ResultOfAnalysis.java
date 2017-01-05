package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "resutls_of_analysis")
public class ResultOfAnalysis extends BaseModel{

    @ForeignCollectionField(eager = false)
    private
    ForeignCollection<Keyword> foundKeywords;

    @DatabaseField(foreign = true)
    private
    ProcessingTask processingTask;

    public ForeignCollection<Keyword> getFoundKeywords() {
        return foundKeywords;
    }

    public void setFoundKeywords(ForeignCollection<Keyword> foundKeywords) {
        this.foundKeywords = foundKeywords;
    }

    public ProcessingTask getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(ProcessingTask processingTask) {
        this.processingTask = processingTask;
    }
}
