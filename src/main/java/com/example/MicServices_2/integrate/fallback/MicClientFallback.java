package com.example.MicServices_2.integrate.fallback;

import com.example.MicServices_2.integrate.MicClient;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MicClientFallback implements FallbackFactory<MicClient> {

    private final static Logger logger = LoggerFactory.getLogger(MicClientFallback.class);
    private MicClient fallback;

    @Override
    public MicClient create(Throwable cause) {
        cause.printStackTrace();
        logger.error(cause.getLocalizedMessage());
        return ErrorFallback();
    }


    private MicClient ErrorFallback(){

        return fallback == null ? fallback = () ->{
            logger.error("调用外部微服务失败！");
            return "没有匹配的网络";
        } :fallback;

    }

}
