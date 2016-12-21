package com.nlpproject.callrecorder.ORMLiteTools;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.nlpproject.callrecorder.ORMLiteTools.model.RecognitionTask;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by Piotrek on 21.12.2016.
 *
 * Run this file after each change in database structure
 * Add classes to 'classes' table
 */

    public class OrmliteDatabaseConfigUtil extends OrmLiteConfigUtil {

    /**
     * classes represents the models to use for generating the ormlite_config.txt file
     */
    private static final Class<?>[] classes = new Class[] {RecognitionTask.class};

    /**
     * Given that this is a separate program from the android app, we have to use
     * a static main java method to create the configuration file.
     * @param args
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws IOException, SQLException {

        writeConfigFile("ormlite_config.txt", classes);
    }
}