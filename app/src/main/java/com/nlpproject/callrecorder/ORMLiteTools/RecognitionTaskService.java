package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.RecognitionTask;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

/**
 * Created by Piotrek on 21.12.2016.
 */

public class RecognitionTaskService {
    private static RecognitionTaskDatabaseHelper recognitionTaskDatabaseHelper;
    public RecognitionTaskService(Context context){
        if (recognitionTaskDatabaseHelper == null){
            recognitionTaskDatabaseHelper = OpenHelperManager.getHelper(context,RecognitionTaskDatabaseHelper.class);
        }
    }

    public RecognitionTaskService() {

    }

    public static RecognitionTask findRecognitionTaskById(Long id) throws SQLException {
        Dao<RecognitionTask,Long> recognitionTaskLongDao = recognitionTaskDatabaseHelper.getDao();
        return recognitionTaskLongDao.queryForId(id);
    }

    public static boolean updateRecognitionTask(RecognitionTask recognitionTask) throws SQLException {
        if (recognitionTask == null || recognitionTask.getId()==null){
            return false;
        }
        Dao<RecognitionTask,Long> recognitionTaskLongDao = recognitionTaskDatabaseHelper.getDao();
        return recognitionTaskLongDao.update(recognitionTask) != 0;
    }
    public static boolean createRecognitionTask (RecognitionTask recognitionTask) throws SQLException {
        Dao<RecognitionTask,Long> recognitionTaskLongDao = recognitionTaskDatabaseHelper.getDao();
        recognitionTaskLongDao.create(recognitionTask);
        return false;
    }
    public static List<RecognitionTask> getSortedList() throws SQLException {
        Dao<RecognitionTask,Long> recognitionTaskLongDao = recognitionTaskDatabaseHelper.getDao();
        List<RecognitionTask> result = recognitionTaskLongDao.queryForAll();
//        Collections.sort(result);
        return result;
    }
}
