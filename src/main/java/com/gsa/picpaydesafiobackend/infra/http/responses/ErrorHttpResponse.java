package com.gsa.picpaydesafiobackend.infra.http.responses;

public abstract class ErrorHttpResponse extends HttpResponse {
  public final ResponseError error;

  public ErrorHttpResponse(ResponseStatus status, int statusCode, String statusText, ResponseError error) {
    super(status, statusCode, statusText);
    this.error = error;
  }
}