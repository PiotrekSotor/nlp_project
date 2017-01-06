package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class Keyword_X_ProcessingTaskService extends BaseService{
    public static Keyword_X_ProcessingTask findId(Long id) {
        Keyword_X_ProcessingTask result = null;
        Dao<Keyword_X_ProcessingTask, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeyword_X_ProcessingTaskDao();
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Keyword_X_ProcessingTask> findByProcessingTask(ProcessingTask processingTask) {
        List<Keyword_X_ProcessingTask>result =null;
        Dao<Keyword_X_ProcessingTask, Long> dao = null;
        try {
            dao =modelsDatabaseHelper.getKeyword_X_ProcessingTaskDao();
            result = dao.queryForEq("processingTask",processingTask);
            Keyword_X_ProcessingTask.setSortType(Keyword_X_ProcessingTask.SORT_TYPE.SORT_MATCHES_DSC);
            Collections.sort(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Keyword_X_ProcessingTask> findByKeyword(Keyword keyword) {
        List<Keyword_X_ProcessingTask>result =null;
        Dao<Keyword_X_ProcessingTask, Long> dao = null;
        try {
            dao =modelsDatabaseHelper.getKeyword_X_ProcessingTaskDao();
            result = dao.queryForEq("foundKeyword",keyword);
            Keyword_X_ProcessingTask.setSortType(Keyword_X_ProcessingTask.SORT_TYPE.SORT_DATE_DSC);
            Collections.sort(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static Boolean update(Keyword_X_ProcessingTask keywordXProcessingTask) {
        if (keywordXProcessingTask == null || keywordXProcessingTask.getId() == null) {
            return false;
        }
        Boolean result = null;
        Dao<Keyword_X_ProcessingTask, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeyword_X_ProcessingTaskDao();
            result = dao.update(keywordXProcessingTask) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param keywordXProcessingTask
     * @return - id of persisted Keyword_X_ProcessingTask
     * @throws SQLException
     */
    public static Long create(Keyword_X_ProcessingTask keywordXProcessingTask) {
        Dao<Keyword_X_ProcessingTask, Long> dao = null;
        Long id = null;
        try {
            dao = modelsDatabaseHelper.getKeyword_X_ProcessingTaskDao();
            dao.create(keywordXProcessingTask);
            id = keywordXProcessingTask.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
