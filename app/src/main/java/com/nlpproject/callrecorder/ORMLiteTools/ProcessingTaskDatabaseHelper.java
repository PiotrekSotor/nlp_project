package com.nlpproject.callrecorder.ORMLiteTools;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.nlpproject.callrecorder.ORMLiteTools.model.ProcessingTask;
import com.nlpproject.callrecorder.R;

import java.sql.SQLException;

/**
 * Created by Piotrek on 21.12.2016.
 */

public class ProcessingTaskDatabaseHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME = "NLP_proj";
    private static final int DATABASE_VERSION = 1;

    /**
     * The data access object used to interact with the Sqlite database to do C.R.U.D operations.
     */
    private Dao<ProcessingTask, Long> recognitionTasksDao;

    public ProcessingTaskDatabaseHelper(Context context) {
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
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, ProcessingTask.class, false);
            onCreate(database, connectionSource);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * Returns an instance of the data access object
     *
     * @return
     * @throws SQLException
     */
    public Dao<ProcessingTask, Long> getDao() throws SQLException {
        if (recognitionTasksDao == null) {
            recognitionTasksDao = getDao(ProcessingTask.class);
        }
        return recognitionTasksDao;
    }

}

