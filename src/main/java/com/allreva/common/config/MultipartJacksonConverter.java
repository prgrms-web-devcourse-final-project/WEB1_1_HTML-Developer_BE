package com.allreva.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;

@Component
public class MultipartJacksonConverter extends AbstractJackson2HttpMessageConverter {
    public MultipartJacksonConverter(ObjectMapper objectMapper) {
        super(objectMapper, MediaType.APPLICATION_OCTET_STREAM);
    }

    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        return false;
    }



    @Override
    protected boolean canWrite(MediaType mediaType) {
        return false;
    }
}
