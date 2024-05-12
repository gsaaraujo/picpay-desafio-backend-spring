package com.gsa.picpaydesafiobackend.infra.http.responses;

public class NotFound extends ErrorHttpResponse {
  public NotFound(ResponseError error) {
    super(ResponseStatus.ERROR, 404, "NOT_FOUND", error);
  }
}
