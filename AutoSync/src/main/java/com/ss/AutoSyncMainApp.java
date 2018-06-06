package com.ss;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@ComponentScan
@EnableTransactionManagement
@EnableScheduling
@MapperScan(basePackages = "com.ss.mapper")
public class AutoSyncMainApp implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        WebMvcConfigurer.super.configureMessageConverters(converters);
        //创建fastjson转换器实例
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        //配置对象
        FastJsonConfig config = new FastJsonConfig();
        List<MediaType> mediaTypes = new ArrayList<>();
        //中文编码
        MediaType mediaType = MediaType.APPLICATION_JSON_UTF8;
        mediaTypes.add(mediaType);
        config.setSerializerFeatures(SerializerFeature.PrettyFormat);
        converter.setSupportedMediaTypes(mediaTypes);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }

    public static void main(String[] args) {
        SpringApplication.run(AutoSyncMainApp.class,args);
    }
}
