package com.gsa.picpaydesafiobackend.infra.http.responses;

public abstract class SuccessHttpResponse extends HttpResponse {
  public final Object data;

  public SuccessHttpResponse(ResponseStatus status, int statusCode, String statusText, Object data) {
    super(status, statusCode, statusText);
    this.data = data;
  }
}
