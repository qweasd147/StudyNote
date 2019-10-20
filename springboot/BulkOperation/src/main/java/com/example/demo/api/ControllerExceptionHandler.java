package com.example.demo.api;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = { RuntimeException.class })
    public ResponseEntity handleRuntimeException(RuntimeException ex){

        logger.error("RuntimeException!!!");
        logger.error(ex.getMessage());

        HashMap<String, String> result = new HashMap<>();

        result.put("code","99");
        result.put("msg","에러남");


        return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(BindException ex
            , HttpHeaders headers, HttpStatus status, WebRequest request) {

        //TODO : bind exception handling

        logger.error("handleBindException!!!");
        logger.error("handleBindException!!!");
        logger.error("handleBindException!!!");

        return super.handleBindException(ex, headers, status, request);
    }
}
