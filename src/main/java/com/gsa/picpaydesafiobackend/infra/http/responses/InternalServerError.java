package com.gsa.picpaydesafiobackend.infra.http.responses;

public class InternalServerError extends ErrorHttpResponse {
  public InternalServerError(ResponseError error) {
    super(ResponseStatus.ERROR, 500, "INTERNAL_SERVER_ERROR", error);
  }
}
