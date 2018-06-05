package com.example.MicServices_2.dao.impl;

import com.example.MicServices_2.dao.DataAccessObjects;
import com.example.MicServices_2.domain.Tables;
import com.example.MicServices_2.routes.aicoder.MetaController;
import com.example.MicServices_2.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PostgreSqlDAO implements DataAccessObjects {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetaController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String query(String sql,String param) {
        String[] strArr = {param};
        String result = jdbcTemplate.queryForObject(sql,strArr,String.class);
        return result;
    }

    @Override
    public Tables excuteSqlSearch(String sql) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        SqlRowSetMetaData metaData = rowSet.getMetaData(); // 获取元数据

        Tables tables = new Tables();
        tables.setTableName(metaData.getTableName(1)); // 获取表名

        int columnCount = metaData.getColumnCount(); // 获取记录字段数量

        List<String> fields = new ArrayList<String>(); // 用于存放字段名
        for (int i = 1; i <= columnCount; i++) {
            fields.add(metaData.getColumnName(i));// 循环以获得字段名称
        }
        tables.setFields(fields); // 存放字段名称

        List<List<Object>>  records = jdbcTemplate.query(sql,

                /**
                 * 这是一个内部类
                 */
                new RowMapper<List<Object>>() {

            @Value("${excuteSql.charset:gbk}")
            private String charSet; // 流文件转换时的编码格式

            @Value("${excuteSql.columnTypeName:BYTEA}")
            private String columnTypeName; // 流文件字段类型名称

            @Override
            public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
                List<Object> record = new ArrayList<Object>(); // 存储单个记录，每次加入一个字段值
                for (int i = 1; i <= columnCount; i++) {
                    if (metaData.getColumnTypeName(i).equals(columnTypeName)) {
                        record.add(Util.convertStreamToString(
                                rs.getBinaryStream(i), charSet));// 将流转换成string再存入
                    } else {
                        String str = rs.getString(i);
                        if (str == null) {
                            record.add("");// 循环以获得字段值
                        } else {
                            record.add(str);// 循环以获得字段值
                        }
                    }
                }
                return record; // 返回单条记录
            }
        });

        tables.setRecords(records); // 存入结果集
        return tables;
    }

    @Override
    public int excuteSql(String sql) {
        int row = jdbcTemplate.update(sql);
        return row;
    }
}
