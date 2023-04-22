package com.myblog.blogapp.exception;

import com.myblog.blogapp.payload.ErrorDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;


@ControllerAdvice              //this annotation makes exception object and give that to this class.
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    //for specific exception.
    @ExceptionHandler(ResourceNotFoundException.class)
    //whenever any ResourceNotFoundException occurs, this annotation creates an object and call this method.
    public ResponseEntity<ErrorDetails> handleResourceNotFoundException(
            ResourceNotFoundException exception,
            WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }


    //for global exception.
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> handleAllException(
           Exception exception,
           WebRequest webRequest
    ){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), exception.getMessage(), webRequest.getDescription(false));
        return new ResponseEntity<>(errorDetails, HttpStatus.CONFLICT
        );
        //insted of internal server error we should put conflict.
    }

}
