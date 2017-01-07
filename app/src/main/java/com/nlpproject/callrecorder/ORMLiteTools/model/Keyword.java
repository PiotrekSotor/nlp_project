package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Piotrek on 05.01.2017.
 */

@DatabaseTable(tableName = "keyword")
public class Keyword extends BaseModel{


    public static final String ORIGINAL_FIELD_NAME = "originalWord";
    @DatabaseField
    private
    String originalWord;

    @DatabaseField
    private
    String baseWord;


    @Override
    public int compareTo(Object another) {
        if (another instanceof Keyword) {
            return this.getOriginalWord().compareTo(((Keyword) another).getOriginalWord());
        }
        return 0;
    }

    public String getOriginalWord() {
        return originalWord;
    }

    public void setOriginalWord(String originalWord) {
        this.originalWord = originalWord;
    }

    public String getBaseWord() {
        return baseWord;
    }

    public void setBaseWord(String baseWord) {
        this.baseWord = baseWord;
    }

    @Override
    public int hashCode() {
        return getId().hashCode();
    }
}
