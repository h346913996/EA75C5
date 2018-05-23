package com.example.MicServices_2.config;


import com.example.MicServices_2.integrate.MicClient;
import com.example.MicServices_2.integrate.fallback.MicClientFallback;
import feign.Contract;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.hystrix.HystrixFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.io.Serializable;

@Configuration
@Import(FeignClientsConfiguration.class)
public class MicClientConfig implements Serializable {

    private final static Logger LOGGER = LoggerFactory.getLogger(MicClientConfig.class);
    @Value("${extends.url:http://baidu.com}")
    private String url;
    @Value("${extends.serverName:MICSERVICETEST}")
    private String serverName;
    @Autowired
    DiscoveryClient discoveryClient;

    @Bean
    @Autowired
    public MicClient bulidMicClient(Encoder encoder, Decoder decoder, Contract contract, MicClientFallback fallback){
        String serverUrl = "http://"
                + discoveryClient.getInstances(serverName).get(0).getHost() + ":"
                + discoveryClient.getInstances(serverName).get(0).getPort()
                + url;
        LOGGER.info("extends.url:{}",serverUrl);
        return HystrixFeign.builder()
                .encoder(encoder)
                .decoder(decoder)
                .contract(contract)
                .target(MicClient.class,serverUrl,fallback);
    }

}
