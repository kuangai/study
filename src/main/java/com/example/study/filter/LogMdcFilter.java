package com.example.study.filter;

import java.io.IOException;
import java.util.UUID;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import org.slf4j.MDC;

/**
 * @title 为logback日志增加traceId
 * @author Xingbz
 * @createDate 2019-4-12
 */
@WebFilter(urlPatterns = "/*", filterName = "logMdcFilter")
public class LogMdcFilter implements Filter {
    private static final String UNIQUE_ID = "traceId";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        boolean bInsertMDC = insertMDC();
        try {
            chain.doFilter(request, response);
        } finally {
            if (bInsertMDC) {
                MDC.remove(UNIQUE_ID);
            }
        }
    }

    @Override
    public void destroy() {
    }

    private boolean insertMDC() {

        String uniqueId = UUID.randomUUID().toString().replace("-", "");
        MDC.put(UNIQUE_ID, uniqueId);
        return true;
    }
}