package com.drmarkdown.auth.exceptions.handler;

import com.mongodb.MongoBulkWriteException;
import com.mongodb.MongoWriteException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //MongoWriteException
    @ExceptionHandler({MongoWriteException.class})
    public ResponseEntity<Object> handleMongoWriteException(final MongoWriteException ex, final WebRequest request) {

        String bodyResponseString = "Username, email or Displayname already exist";

        log.error(bodyResponseString, ex.getMessage());
        return handleExceptionInternal(ex, bodyResponseString, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }
}
