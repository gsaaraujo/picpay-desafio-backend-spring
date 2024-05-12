package com.gsa.picpaydesafiobackend.infra.http.responses;

public class BadRequest extends ErrorHttpResponse {
  public BadRequest(ResponseError error) {
    super(ResponseStatus.ERROR, 400, "BAD_REQUEST", error);
  }
}