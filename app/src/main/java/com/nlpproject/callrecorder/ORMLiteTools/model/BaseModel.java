package com.nlpproject.callrecorder.ORMLiteTools.model;

import com.j256.ormlite.field.DatabaseField;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class BaseModel {
    @DatabaseField (generatedId = true)
    private
    Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}