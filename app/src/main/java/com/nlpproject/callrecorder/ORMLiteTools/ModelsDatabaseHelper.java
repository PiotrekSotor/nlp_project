package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.KeywordBase;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword_X_ProcessingTask;
import com.nlpproject.callrecorder.R;

import java.sql.SQLException;

/**
 * Created by Piotrek on 21.12.2016.
 */

public class ModelsDatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "NLP_proj";
    private static final int DATABASE_VERSION = 1;

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<ProcessingTask, Long> processingTasksDao;
    private Dao<Keyword, Long> keywordDao;
    private Dao<Keyword_X_ProcessingTask, Long> keyword_X_processingTaskDao;
    private Dao<KeywordBase, Long> keywordBaseDao;
    static ModelsDatabaseHelper instance = null;

    /**
     *
     * @param context - may be null when you are sure that helper is initialized
     * @return
     */
    public static ModelsDatabaseHelper getInstance(Context context){
        if (instance  == null)
        {
            instance = OpenHelperManager.getHelper(context,ModelsDatabaseHelper.class);
        }
        return instance;
    }

    public ModelsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION,
                /**
                 * R.raw.ormlite_config is a reference to the ormlite_config.txt file in the
                 * /res/raw/ directory of this project
                 * */
                R.raw.ormlite_config);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, ProcessingTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, KeywordBase.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Keyword.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.createTable(connectionSource, Keyword_X_ProcessingTask.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ProcessingTask.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.dropTable(connectionSource, Keyword_X_ProcessingTask.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.dropTable(connectionSource, Keyword.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            TableUtils.dropTable(connectionSource, KeywordBase.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        onCreate(database, connectionSource);
    }

    /**
     * Returns an instance of the data access object
     *
     * @return
     * @throws SQLException
     */
    public Dao<ProcessingTask, Long> getProcessingTaskDao() throws SQLException {
        if (processingTasksDao == null) {
            processingTasksDao = getDao(ProcessingTask.class);
        }
        return processingTasksDao;
    }
    public Dao<Keyword, Long> getKeywordDao() throws SQLException {
        if (keywordDao == null) {
            keywordDao = getDao(Keyword.class);
        }
        return keywordDao;
    }
    public Dao<Keyword_X_ProcessingTask, Long> getKeyword_X_ProcessingTaskDao() throws SQLException {
        if (keyword_X_processingTaskDao == null) {
            keyword_X_processingTaskDao = getDao(Keyword_X_ProcessingTask.class);
        }
        return keyword_X_processingTaskDao;
    }
    public Dao<KeywordBase, Long> getKeywordBaseDao() throws SQLException {
        if (keywordBaseDao == null) {
            keywordBaseDao = getDao(KeywordBase.class);
        }
        return keywordBaseDao;
    }

}


