package com.gsa.picpaydesafiobackend.infra.http.responses;

public class Ok extends SuccessHttpResponse {

  public Ok() {
    super(ResponseStatus.SUCCESS, 200, "OK", null);
  }

  public Ok(Object data) {
    super(ResponseStatus.SUCCESS, 200, "OK", data);
  }

}
