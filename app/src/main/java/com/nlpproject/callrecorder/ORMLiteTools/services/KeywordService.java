package com.nlpproject.callrecorder.ORMLiteTools.services;

import com.j256.ormlite.dao.Dao;
import com.nlpproject.callrecorder.ORMLiteTools.model.Keyword;

import java.sql.SQLException;

/**
 * Created by Piotrek on 05.01.2017.
 */

public class KeywordService extends BaseService{
    public static Keyword findId(Long id){
        Keyword result = null;
        Dao<Keyword, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeywordDao();
            result = dao.queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static Boolean update(Keyword keyword) {
        if (keyword == null || keyword.getId()==null){
            return false;
        }
        Boolean result = null;
        Dao<Keyword, Long> dao = null;
        try {
            dao = modelsDatabaseHelper.getKeywordDao();
            result = dao.update(keyword) != 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     *
     * @param keyword
     * @return - id of persisted Keyword
     * @throws SQLException
     */
    public static Long create(Keyword keyword){
        Dao<Keyword, Long> dao = null;
        Long id = null;
        try {
            dao = modelsDatabaseHelper.getKeywordDao();
            dao.create(keyword);
            id = keyword.getId();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

}
