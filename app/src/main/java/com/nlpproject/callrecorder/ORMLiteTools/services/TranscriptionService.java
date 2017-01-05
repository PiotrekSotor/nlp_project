package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.Transcription;

import java.sql.SQLException;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class TranscriptionService extends BaseService {
    public static Transcription findId(Long id){
        Transcription result = null;
        Dao<Transcription, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getTranscriptionDao();
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean update(Transcription transcription) {
        if (transcription == null || transcription.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<Transcription, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getTranscriptionDao();
            result = dao.update(transcription) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param transcription
     * @return - id of persisted Transcription
     * @throws SQLException
     */
    public static Long create(Transcription transcription){
        Dao<Transcription, Long> dao = null;
        Long id = null;
        try {
            dao = modelsDatabaseHelper.getTranscriptionDao();
            dao.create(transcription);
            id = transcription.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
