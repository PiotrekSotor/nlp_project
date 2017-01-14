package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Piotrek on 13.01.2017.
 */

public class KeywordBase extends BaseModel {

    public static final String KEYWORD_ID_FIELD_NAME = "keyword_id";

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

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) {
            return base.equals(o);
        } else if (o instanceof KeywordBase) {
            return base.equals(((KeywordBase) o).base) &&
                    keyword.equals(((KeywordBase) o).getKeyword());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 17*keyword.hashCode() + 31*base.hashCode()+super.hashCode();
    }
}
