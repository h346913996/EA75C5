package com.example.MicServices_2.routes.showorder;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/getOrder",method = RequestMethod.GET)
public class TheOrderController {

    public final static String INFO = "笔记本：联想K41 金额：5000￥ 由Micservice_2提供";

    @RequestMapping
    public Object getInde(){
        return INFO;
    }

}
