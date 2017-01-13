package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;
import com.nlpproject.callrecorder.ORMLiteTools.model.KeywordBase;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class KeywordBaseService extends BaseService{
    public static KeywordBase findId(Long id){
        KeywordBase result = null;
        Dao<KeywordBase, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeywordBaseDao();
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<KeywordBase> findByKeyword(Keyword original) {
        List<KeywordBase>result =null;
        Dao<KeywordBase, Long> dao = null;
        try {
            dao =modelsDatabaseHelper.getKeywordBaseDao();
            result = dao.queryForEq(KeywordBase.KEYWORD_ID_FIELD_NAME,original.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean update(KeywordBase keywordBase) {
        if (keywordBase == null || keywordBase.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<KeywordBase, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeywordBaseDao();
            result = dao.update(keywordBase) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param keywordBase
     * @return - id of persisted Keyword
     * @throws SQLException
     */
    public static Long create(KeywordBase keywordBase){
        Dao<KeywordBase, Long> dao = null;
        Long id = null;
        try {
            dao = modelsDatabaseHelper.getKeywordBaseDao();
            dao.create(keywordBase);
            id = keywordBase.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean delete(KeywordBase keywordBase){
        Dao<KeywordBase, Long> dao = null;
        boolean result = false;
        try {
            dao = modelsDatabaseHelper.getKeywordBaseDao();
            result = dao.delete(keywordBase) == 1;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<KeywordBase> getSortedList(){
        List<KeywordBase> result = null;
        Dao<KeywordBase,Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeywordBaseDao();
            result = dao.queryForAll();
            Collections.sort(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
