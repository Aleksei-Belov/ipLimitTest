package com.project.iplimittest.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

public class IpServiceUtils {
    private static final String LOCAL_HOST_IP = "127.0.0.1";
    private static final String ORIGINAL_CLIENT_IP = "0:0:0:0:0:0:0:1";

    public static String getCurrentClientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String ip = null;
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            ip = Optional.ofNullable(request.getHeader("X-FORWARDED-FOR")).orElse(request.getRemoteAddr());
            if (ORIGINAL_CLIENT_IP.equals(ip)) {
                return LOCAL_HOST_IP;
            }
        }

        return Optional.ofNullable(ip).orElse("unknown");
    }
}
