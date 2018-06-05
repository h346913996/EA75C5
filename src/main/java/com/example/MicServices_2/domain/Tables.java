package com.example.MicServices_2.domain;

import java.util.List;

public class Tables {
    private String TableName;
    private List<String> Fields;
    private List<List<Object>> Records;
    public String getTableName() {
        return TableName;
    }
    public void setTableName(String tableName) {
        TableName = tableName;
    }
    public List<String> getFields() {
        return Fields;
    }
    public void setFields(List<String> fields) {
        Fields = fields;
    }
    public List<List<Object>> getRecords() {
        return Records;
    }
    public void setRecords(List<List<Object>> records) {
        Records = records;
    }
    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return super.toString();
    }
}
