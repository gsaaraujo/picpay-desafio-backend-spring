package com.gsa.picpaydesafiobackend.infra.http.responses;

public abstract class HttpResponse {
  public final ResponseStatus status;
  public final int statusCode;
  public final String statusText;

  public HttpResponse(ResponseStatus status, int statusCode, String statusText) {
    this.status = status;
    this.statusCode = statusCode;
    this.statusText = statusText;
  }
}
