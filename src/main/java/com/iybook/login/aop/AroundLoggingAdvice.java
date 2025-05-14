package com.iybook.login.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Map;

@Slf4j
@Component
@Aspect
public class AroundLoggingAdvice {

    @Around("execution (* com.kangbroo.sbatis.controller.*Controller.*(..))")
    public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        // 핵심로직메소드가 실행되기 전 시점
        // 요청 파라미터, 요청전송방식, 요청주소 출력
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();

        Map<String, String[]> map = request.getParameterMap();   // {} | {no=["1"], hobby=["sports", "reading"]}
        String params = "";
        if(map.isEmpty()){
            params += "No Parameter";
        }else{
            for(String key : map.keySet()) { // {"no", "hobby", ..}
                String[] value = map.get(key);
                params += key + ":" + Arrays.toString(value) + " ";
            }
        }

        log.debug("{}", "┬".repeat(70));
        log.debug("{} │ {}", request.getMethod(), request.getRequestURI());
        log.debug("{}", params);

        Object obj = proceedingJoinPoint.proceed(); // 핵심로직메소드를 직접 실행

        // 핵심로직메소드가 실행된 후 시점
        log.debug("{}", "┴".repeat(70));

        return obj;
    }

}
