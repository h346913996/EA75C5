package com.example.MicServices_2.service;

import com.example.MicServices_2.dao.DataAccessObjects;
import com.example.MicServices_2.domain.Tables;
import com.example.MicServices_2.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ExcuteSqlService {

    @Autowired
    private DataAccessObjects dao;

    @Value("${excuteSql.querySql:SELECT PLSQL FROM META836 WHERE GUID = ?}")
    private String querySql; // 查询语句

    public String query( String guid){
        return dao.query(querySql,guid);
    }

    public Tables[] excuteSqlSearch( String sql,String[] values,String pageSize,String pageIndex){
        sql = Util.stitchingStatement(sql, values); // 拼接sql语句
        Tables[] tables = new Tables[2]; // 存放表信息

        tables[0] = new Tables(); // 第一个表信息用于存放分页情况
        tables[0].setTableName("tb1");
        List<String> fields1 = new ArrayList<String>(); // 记录总条数，每页大小，页码
        fields1.add("sum"); // 总计记录条数
        fields1.add("size"); // 每页大小
        fields1.add("indx"); // 当前页码
        tables[0].setFields(fields1); // 放入表中

        tables[1] = dao.excuteSqlSearch(sql);

        int pageSizeInt = Integer.parseInt(pageSize); // 转换参数
        int pageIndexInt = Integer.parseInt(pageIndex); // 转换参数
        int front = ((pageIndexInt - 1) * pageSizeInt) + 1; // 从第几条开始属于查询内容
        int behind = (pageSizeInt * pageIndexInt); // 到第几条结束

        int sum = tables[1].getRecords().size(); // 获取总记录数目
        if(front>sum){
            tables[1].setRecords(null); // 超出，无内容
        } else if(behind>sum) {
            behind = sum;
        }
        if(tables[1].getRecords()!=null){
            tables[1].getRecords().subList(front,behind); // 进行分页
        }

        List<List<Object>> records1 = new ArrayList<List<Object>>(); // 存储记录，每次加入一整条记录
        List<Object> splitData = new ArrayList<Object>(); // 存放分页数据
        splitData.add(sum); // 获取该查询总记录数
        splitData.add(pageSize);
        splitData.add(pageIndex);
        records1.add(splitData); // 组装
        tables[0].setRecords(records1); // 放入表中

        return tables;
    }

    public boolean excuteSql( String sql,String[] values){
        sql = Util.stitchingStatement(sql, values); // 拼接sql语句
        int row = dao.excuteSql(sql);//执行
        if( row > 0 ){ // 判断受影响
            return true;
        }
        return false;
    }

}
