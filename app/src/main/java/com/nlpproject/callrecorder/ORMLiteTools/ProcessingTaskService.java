package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by Piotrek on 21.12.2016.
 */

public class ProcessingTaskService {
    private static ProcessingTaskDatabaseHelper processingTaskDatabaseHelper;

    public static void initProcessingTaskService(Context context){
        if (processingTaskDatabaseHelper == null){
            processingTaskDatabaseHelper = OpenHelperManager.getHelper(context,ProcessingTaskDatabaseHelper.class);
        }
    }

    public static ProcessingTask findProcessingTaskById(Long id){
        ProcessingTask result = null;
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        try {
            recognitionTaskLongDao = processingTaskDatabaseHelper.getDao();
            result = recognitionTaskLongDao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static ProcessingTask findRecognitionTaskByGoogleCloudId(String id) {
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        ProcessingTask result = null;
        try {
            recognitionTaskLongDao = processingTaskDatabaseHelper.getDao();
            result = recognitionTaskLongDao.queryForEq(ProcessingTask.GOOGLE_CLOUD_TASK_ID_FIELD_NAME,id).get(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean updateProcessingTask(ProcessingTask processingTask) {
        if (processingTask == null || processingTask.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        try {
            recognitionTaskLongDao = processingTaskDatabaseHelper.getDao();
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
    public static Long createRecognitionTask (ProcessingTask processingTask){
        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
        Long id = null;
        try {
            recognitionTaskLongDao = processingTaskDatabaseHelper.getDao();
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
            Dao<ProcessingTask, Long> recognitionTaskLongDao = processingTaskDatabaseHelper.getDao();
            result = recognitionTaskLongDao.queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
