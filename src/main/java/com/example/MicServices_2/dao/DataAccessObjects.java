package com.example.MicServices_2.dao;

import com.example.MicServices_2.domain.Tables;

import java.util.List;
import java.util.Map;

public interface DataAccessObjects {

    /**
     *
     * @param sql
     * @param param
     * @return
     */
    public String query(String sql,String param);

    /**
     * sql查询语句执行
     * @param sql sql语句
     * @return 结果对象类
     */
    public Tables excuteSqlSearch(String sql);
    /**
     * sql操作语句执行
     * @param sql sql语句
     * @return 成功或错误信息
     */
    public int excuteSql(String sql);
}
