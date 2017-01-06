package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "keyword_x_processingTask")
public class Keyword_X_ProcessingTask extends BaseModel{

    private static SORT_TYPE sortType = SORT_TYPE.SORT_DATE_DSC;

    @DatabaseField (foreign = true)
    private
    Keyword foundKeyword;

    @DatabaseField(foreign = true)
    private
    ProcessingTask processingTask;

    @DatabaseField
    private
    Integer numberOfMatches;

    public static SORT_TYPE getSortType() {
        return sortType;
    }

    public static void setSortType(SORT_TYPE sortType) {
        Keyword_X_ProcessingTask.sortType = sortType;
    }

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

    @Override
    public int compareTo(Object another) {
        int result = 0;
        if (another != null && another instanceof Keyword_X_ProcessingTask)
        switch (sortType){
            case SORT_DATE_DSC:
                result = -processingTask.getRecordDate().compareTo(((Keyword_X_ProcessingTask) another).processingTask.getRecordDate());
                break;
            case SORT_MATCHES_DSC:
                result = -numberOfMatches.compareTo(((Keyword_X_ProcessingTask) another).numberOfMatches);
                break;
            default:
        }
        return result;
    }
    public enum SORT_TYPE{
        SORT_MATCHES_DSC,
        SORT_DATE_DSC
    }
}
