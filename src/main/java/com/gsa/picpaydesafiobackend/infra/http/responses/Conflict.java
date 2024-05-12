package com.gsa.picpaydesafiobackend.infra.http.responses;

public class Conflict extends ErrorHttpResponse {
  public Conflict(ResponseError error) {
    super(ResponseStatus.ERROR, 409, "CONFLICT", error);
  }
}
