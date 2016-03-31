package org.ff4j.spring.boot.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Paul
 *
 * @author <a href="mailto:paul58914080@gmail.com">Paul Williams</a>
 */
public enum HttpMethod {

    GET("GET"), PUT("PUT"), POST("POST"), DELETE("DELETE");

    private String httpMethod;

    HttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    @Override
    public String toString() {
        return httpMethod;
    }

    public static org.springframework.http.HttpMethod getHttpMethod(String httpMethodString) {
        switch (getEnum(httpMethodString)) {
            case GET:
                return org.springframework.http.HttpMethod.GET;
            case PUT:
                return org.springframework.http.HttpMethod.PUT;
            case POST:
                return org.springframework.http.HttpMethod.POST;
            case DELETE:
                return org.springframework.http.HttpMethod.DELETE;
            default:
                throw new IllegalArgumentException();
        }
    }

    private static HttpMethod getEnum(String httpMethodString) {
        if (StringUtils.isBlank(httpMethodString)) {
            throw new IllegalArgumentException();
        }
        for (HttpMethod httpMethod : values()) {
            if (httpMethod.toString().equalsIgnoreCase(httpMethodString)) {
                return httpMethod;
            }
        }
        throw new IllegalArgumentException();
    }
}
