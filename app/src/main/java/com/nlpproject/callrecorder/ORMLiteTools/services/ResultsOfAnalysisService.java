package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.ResultOfAnalysis;

import java.sql.SQLException;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class ResultsOfAnalysisService extends BaseService {
    public static ResultOfAnalysis findId(Long id){
        ResultOfAnalysis result = null;
        Dao<ResultOfAnalysis, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getResultsOfAnalisisDao();
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean update(ResultOfAnalysis resultOfAnalysis) {
        if (resultOfAnalysis == null || resultOfAnalysis.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<ResultOfAnalysis, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getResultsOfAnalisisDao();
            result = dao.update(resultOfAnalysis) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param resultOfAnalysis
     * @return - id of persisted ResultOfAnalysis
     * @throws SQLException
     */
    public static Long create(ResultOfAnalysis resultOfAnalysis){
        Dao<ResultOfAnalysis, Long> dao = null;
        Long id = null;
        try {
            dao = modelsDatabaseHelper.getResultsOfAnalisisDao();
            dao.create(resultOfAnalysis);
            id = resultOfAnalysis.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
