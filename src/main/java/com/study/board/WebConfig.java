package com.study.board;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * localhost:5173의 접속을 허용한다.
     *
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("*")
                .maxAge(3600);
    }

    /**
     * SHA256Encoder Bean으로 등록
     *
     * @return
     */
    @Bean
    public SHA256Encoder sha256Encoder() {
        return new SHA256Encoder();
    }
}
