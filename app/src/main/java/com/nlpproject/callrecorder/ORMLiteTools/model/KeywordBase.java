package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Piotrek on 13.01.2017.
 */

public class KeywordBase extends BaseModel {

    public static final String KEYWORD_ID_FIELD_NAME = "originalWord";

    @DatabaseField(foreign = true, foreignAutoRefresh = true, maxForeignAutoRefreshLevel = 2)
    private
    Keyword keyword;

    @DatabaseField
    private
    String base;

    @Override
    public int compareTo(Object another) {
        if (another instanceof KeywordBase) {
            return this.base.compareTo(((KeywordBase) another).getBase());
        }
        return 0;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    public void setKeyword(Keyword keyword) {
        this.keyword = keyword;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    //todo: napisać equals dla stringa tak by sprawdzał z base
}
