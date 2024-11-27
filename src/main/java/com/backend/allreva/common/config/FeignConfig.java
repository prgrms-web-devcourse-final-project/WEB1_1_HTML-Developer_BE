package com.backend.allreva.common.config;


import feign.codec.Decoder;
import feign.jaxb.JAXBContextFactory;
import feign.jaxb.JAXBDecoder;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.backend.allreva")
public class FeignConfig {
    @Bean
    public Decoder xmlDecoder() {
        return new JAXBDecoder(new JAXBContextFactory.Builder()
                .withMarshallerJAXBEncoding("UTF-8")
                .build());
    }
}
