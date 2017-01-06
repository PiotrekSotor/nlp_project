package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "keyword_x_processingTask")
public class Keyword_X_ProcessingTask extends BaseModel{

    @DatabaseField (foreign = true)
    private
    Keyword foundKeyword;

    @DatabaseField(foreign = true)
    private
    ProcessingTask processingTask;

    @DatabaseField
    private
    Integer numberOfMatches;

    public ProcessingTask getProcessingTask() {
        return processingTask;
    }

    public void setProcessingTask(ProcessingTask processingTask) {
        this.processingTask = processingTask;
    }

    public Keyword getFoundKeyword() {
        return foundKeyword;
    }

    public void setFoundKeyword(Keyword foundKeyword) {
        this.foundKeyword = foundKeyword;
    }

    public Integer getNumberOfMatches() {
        return numberOfMatches;
    }

    public void setNumberOfMatches(Integer numberOfMatches) {
        this.numberOfMatches = numberOfMatches;
    }
}
