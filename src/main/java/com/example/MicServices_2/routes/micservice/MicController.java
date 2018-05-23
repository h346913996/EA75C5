package com.example.MicServices_2.routes.micservice;

import com.example.MicServices_2.integrate.MicClient;
import com.example.MicServices_2.routes.showorder.TheOrderController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
@RequestMapping(value = "/admin-order",method = RequestMethod.GET)
public class MicController implements Serializable {

    @Autowired
    private MicClient micClient;

    @RequestMapping
    public Object getIndex(){
        return  micClient.getIndex() + "<br>"+TheOrderController.INFO;
    }

}
