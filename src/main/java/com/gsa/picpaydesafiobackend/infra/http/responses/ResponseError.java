package com.gsa.picpaydesafiobackend.infra.http.responses;

import java.util.Date;

public class ResponseError {
  public String message;
  public String suggestion;
  public String timestamp;

  public ResponseError(String message) {
    this.message = message;
    this.timestamp = new Date().toString();
  }

  public ResponseError(String message, String suggestion) {
    this.message = message;
    this.suggestion = suggestion;
    this.timestamp = new Date().toString();
  }

}
