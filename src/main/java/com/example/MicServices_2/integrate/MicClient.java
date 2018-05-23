package com.example.MicServices_2.integrate;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.Serializable;

public interface MicClient extends Serializable {

    @RequestMapping(value = "")
    String getIndex();

}
