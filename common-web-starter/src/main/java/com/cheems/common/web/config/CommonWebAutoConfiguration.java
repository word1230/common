package com.cheems.common.web.config;

import com.cheems.common.web.advice.GlobalExceptionHandler;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * Web 层自动装配:引入 common-web-starter 依赖后,
 * 全局异常处理器与跨域配置自动生效,项目无需再写任何配置类。
 */
@AutoConfiguration
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(CorsProperties.class)
public class CommonWebAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "commonCorsConfigurer")
    public WebMvcConfigurer commonCorsConfigurer(CorsProperties corsProperties) {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                if (!corsProperties.isEnabled()) {
                    return;
                }
                registry.addMapping("/**")
                        .allowedOriginPatterns(toArray(corsProperties.getAllowedOriginPatterns()))
                        .allowedMethods(toArray(corsProperties.getAllowedMethods()))
                        .allowedHeaders(toArray(corsProperties.getAllowedHeaders()))
                        .allowCredentials(corsProperties.isAllowCredentials())
                        .maxAge(corsProperties.getMaxAgeSeconds());
            }
        };
    }

    private String[] toArray(List<String> values) {
        return values.toArray(String[]::new);
    }
}
