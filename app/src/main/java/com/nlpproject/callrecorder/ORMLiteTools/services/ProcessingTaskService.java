package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Piotrek on 21.12.2016.
 */

public class ProcessingTaskService extends BaseService {

    public static ProcessingTask find(Long id){
        ProcessingTask result = null;
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        try {
            recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
            result = recognitionTaskLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

        public static Boolean update(ProcessingTask processingTask) {
        if (processingTask == null || processingTask.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        try {
            recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
            result = recognitionTaskLongDao.update(processingTask) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param processingTask
     * @return - id of persisted processingTask
     * @throws SQLException
     */
    public static Long create(ProcessingTask processingTask){
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        Long id = null;
        try {
            recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
            recognitionTaskLongDao.create(processingTask);
            id = processingTask.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }


    public static List<ProcessingTask> getSortedList(){
        List<ProcessingTask> result = null;
        try {
            Dao<ProcessingTask, Long> recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
            result = recognitionTaskLongDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}