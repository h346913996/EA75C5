package com.example.MicServices_2.util;

import java.io.*;

public class Util {

    /**
     * 参数验证
     * @param str 参数
     * @return true：有效参数	false：无效参数
     */
    public static boolean validate(String str){
        if(str==null){
            return false;
        }else if(str.equals("")){
            return false;
        }else if((str.toLowerCase()).equals("null")){
            return false;
        }else{
            return true;
        }
    }

    /**
     * 将stream转换成String
     *
     * @param is
     *            输入流
     * @param charSet
     *            编码
     * @return 被转换成String的输入流内容
     */
    public static String convertStreamToString(InputStream is, String charSet) {
        BufferedReader reader = null;
        if (is == null) {
            return "";
        }
        reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        String result = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\r\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            result = new String(sb.toString().getBytes(), charSet);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * sql拼接函数
     *
     * @param sql
     *            带{0-9} 的sql语句
     * @param values
     *            将要替换的值
     * @return 一个拼接完成的sql语句
     */
    public static String stitchingStatement(String sql, String[] values) {
        if (values != null) {
            for (int i = 0, len = values.length; i < len; i++) {
                sql = sql.replaceAll("\\{[" + i + "]\\}",
                        "fb7a0b12-6fcf-44db-8ccb-5e36693b0401" + i);//防止替换掉参数中带有的｛number｝
            }
            sql = sql.replaceAll("\\{[0-9]*\\}", "");
            for (int i = 0, len = values.length; i < len; i++) {
                sql = sql.replaceAll(
                        "fb7a0b12-6fcf-44db-8ccb-5e36693b0401" + i, values[i]);
            }
        }
        return sql;
    }
}
