package com.cheems.common.web.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * 跨域配置项,前缀为 common.web.cors。
 *
 * <p>默认放开所有来源,方便本地前后端联调;生产环境务必在配置文件中
 * 收紧 allowed-origin-patterns 为具体域名。
 */
@ConfigurationProperties(prefix = "common.web.cors")
public class CorsProperties {

    /** 跨域配置的默认有效期,单位秒,避免浏览器频繁发送预检请求 */
    static final long DEFAULT_MAX_AGE_SECONDS = 3600;

    private boolean enabled = true;

    private List<String> allowedOriginPatterns = List.of("*");

    private List<String> allowedMethods = List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS");

    private List<String> allowedHeaders = List.of("*");

    private boolean allowCredentials = false;

    private long maxAgeSeconds = DEFAULT_MAX_AGE_SECONDS;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<String> getAllowedOriginPatterns() {
        return allowedOriginPatterns;
    }

    public void setAllowedOriginPatterns(List<String> allowedOriginPatterns) {
        this.allowedOriginPatterns = allowedOriginPatterns;
    }

    public List<String> getAllowedMethods() {
        return allowedMethods;
    }

    public void setAllowedMethods(List<String> allowedMethods) {
        this.allowedMethods = allowedMethods;
    }

    public List<String> getAllowedHeaders() {
        return allowedHeaders;
    }

    public void setAllowedHeaders(List<String> allowedHeaders) {
        this.allowedHeaders = allowedHeaders;
    }

    public boolean isAllowCredentials() {
        return allowCredentials;
    }

    public void setAllowCredentials(boolean allowCredentials) {
        this.allowCredentials = allowCredentials;
    }

    public long getMaxAgeSeconds() {
        return maxAgeSeconds;
    }

    public void setMaxAgeSeconds(long maxAgeSeconds) {
        this.maxAgeSeconds = maxAgeSeconds;
    }
}
