package com.nlpproject.callrecorder.ORMLiteTools.services;

import android.content.Context;

import com.nlpproject.callrecorder.ORMLiteTools.ModelsDatabaseHelper;

/**
 * Created by Piotrek on 05.01.2017.
 */
public abstract class BaseService {
    protected static ModelsDatabaseHelper modelsDatabaseHelper;

    /**
     *
     * @param context - may be null when you are sure that helper is already initialized
     */

    public static void initProcessingTaskService(Context context){
        if (modelsDatabaseHelper == null){
            modelsDatabaseHelper = ModelsDatabaseHelper.getInstance(context);
        }
    }
//
//    public static <T extends BaseModel> T findId(Long id){return null};
//
//
//    public static Boolean update(ProcessingTask processingTask) {
//        if (processingTask == null || processingTask.getId()==null){
//            return false;
//        }
//        Boolean result = null;
//        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
//        try {
//            recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
//            result = recognitionTaskLongDao.update(processingTask) != 0;
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     *
//     * @param processingTask
//     * @return - id of persisted processingTask
//     * @throws SQLException
//     */
//    public static Long create(ProcessingTask processingTask){
//        Dao<ProcessingTask, Long> recognitionTaskLongDao = null;
//        Long id = null;
//        try {
//            recognitionTaskLongDao = modelsDatabaseHelper.getProcessingTaskDao();
//            recognitionTaskLongDao.create(processingTask);
//            id = processingTask.getId();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return id;
//    }
}
