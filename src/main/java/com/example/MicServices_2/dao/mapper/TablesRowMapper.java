package com.example.MicServices_2.dao.mapper;

import com.example.MicServices_2.domain.Tables;
import com.example.MicServices_2.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablesRowMapper implements RowMapper<List<Object>> {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private  SqlRowSetMetaData metaData;//元数据

    @Value("${excuteSql.charset:1}")
    private String charSet; //

    public TablesRowMapper(String sql) {
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql);
        metaData = rowSet.getMetaData();
    }

    @Override
    public List<Object> mapRow(ResultSet rs, int rowNum) throws SQLException {
        List<Object> record = new ArrayList<Object>(); // 存储单个记录，每次加入一个字段值
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            if (metaData.getColumnTypeName(i).equals("BYTEA")) {
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
        return record;
    }

}
