package com.huynhthehoa.nutzen.exception;

import com.huynhthehoa.nutzen.payload.BaseResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.lang.annotation.Annotation;

@ControllerAdvice
public class CustomExceptionHandler extends ExceptionHandlerExceptionResolver {
    @ExceptionHandler(value = {Exception.class,})
    protected ResponseEntity<?> handleConflict(Exception e, HttpServletRequest req) {
        BaseResponse response = new BaseResponse(500, e.getLocalizedMessage(), null);
        return ResponseEntity.ok(response);
    }
}
