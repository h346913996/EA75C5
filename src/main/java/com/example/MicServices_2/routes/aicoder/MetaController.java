package com.example.MicServices_2.routes.aicoder;

import com.example.MicServices_2.domain.QueryResult;
import com.example.MicServices_2.service.ExcuteSqlService;
import com.example.MicServices_2.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/subsystem/sc/metadatam/MetaDataM8367")
public class MetaController implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(MetaController.class);

    @Autowired
    private ExcuteSqlService excuteSqlService; // ea7=5c5的service层

    @Value("${excuteSql.allowOrigin:1}")
    private String allowOrigin; // 参数设置是否允许跨域

    @Value("${excuteSql.successMessage:操作成功！}")
    private String successMessage; // 参数设置是否允许跨域

    @RequestMapping
    public @ResponseBody Object routes(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        if("1".equals(allowOrigin)){
            response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错
            response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600"); //设置过期时间
            response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, client_id, uuid, Authorization");
        }
        if ("5c5".equals(request.getParameter("ea7"))) {// 如果不为空则使用执行语句方法
           return excuteSql(request, response);
        }
        return "找不到对应的方法，请检查传入参数是否正确！";
    }


    public
    Object excuteSql(HttpServletRequest request, HttpServletResponse response) {
        String mode = request.getParameter("g"); // 获取操作模式
        String guid = request.getParameter("h"); // 获取数据库语句存放id<字段名，值>
        String divide = request.getParameter("i"); // 获取分隔符
        String values = request.getParameter("j"); // 获取参数值
        String pageSize = request.getParameter("k"); // 获取分页大小
        String pageIndex = request.getParameter("l"); // 获取页码
        LOGGER.info("request happen :{},{},{},{},{}",mode,divide,values,pageSize,pageIndex);
        String sql = null;
        Object result = null;
        QueryResult qrs = new QueryResult(); // 存储结果对象
        Map<String, Object> results = new LinkedHashMap<String, Object>();// 用于存储最终结果
        results.put("Result", qrs);// 存放Result
        try {
            sql = excuteSqlService.query(guid); // 查询sql语句
            if ("1".equals(mode)) { // 查询模式
                if (Util.validate(values) && !"0".equals(values)) {
                    if (Util.validate(divide)) {
                        result = excuteSqlService.excuteSqlSearch(sql, values.split(divide), pageSize, pageIndex); // 有参数进行分割传入
                    } else {
                        String[] strArr = {values};
                        result = excuteSqlService.excuteSqlSearch(sql, strArr, pageSize, pageIndex); // 无分割字符，将传入参数看作一个
                    }
                } else {
                    result = excuteSqlService.excuteSqlSearch(sql, null, pageSize, pageIndex); // 无参数直接传null
                }
                results.put("Tables", result); // 放入结果存储对象
            } else if ("2".equals(mode)) { // 批量操作模式
                if (Util.validate(values) && !"0".equals(values)) {
                    if (Util.validate(divide)) {
                        excuteSqlService.excuteSql(sql, values.split(divide)); // 有参数进行分割传入
                    } else {
                        String[] strArr = {values};
                        excuteSqlService.excuteSql(sql, strArr); // 无分割字符，将传入参数看作一个
                    }
                } else {
                    excuteSqlService.excuteSql(sql, null); // 无参数直接传null
                }
            }
            qrs.setCode(1); // 操作成功代码
            qrs.setMsg(successMessage); // 操作成功信息
        } catch(Exception e) {
            e.printStackTrace();
            qrs.setMsg(e.toString()); // 将异常信息写入要返回的结果码里
        }
        finally {
            return results;
        }
    }

}
