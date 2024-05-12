package com.gsa.picpaydesafiobackend.infra.http;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.converter.HttpMessageNotReadableException;

import com.gsa.picpaydesafiobackend.infra.http.responses.BadRequest;
import com.gsa.picpaydesafiobackend.infra.http.responses.HttpResponse;
import com.gsa.picpaydesafiobackend.infra.http.responses.ResponseError;
import com.gsa.picpaydesafiobackend.infra.http.responses.InternalServerError;

@RestControllerAdvice()
public class GlobalExceptionHandler {
  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<HttpResponse> handle(HttpMessageNotReadableException exception) {
    var responseError = new ResponseError("The request body is invalid", "Please check the request body");
    var BadRequest = new BadRequest(responseError);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BadRequest);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<HttpResponse> handle(Exception exception) {
    var responseError = new ResponseError("Something wrong happened. Try again later");
    var internalServerError = new InternalServerError(responseError);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(internalServerError);
  }
}
