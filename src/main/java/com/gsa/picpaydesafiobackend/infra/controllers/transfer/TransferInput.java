package com.gsa.picpaydesafiobackend.infra.controllers.transfer;

import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.experimental.Accessors;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.UUID;

@Getter
@AllArgsConstructor
@Accessors(fluent = true)
public class TransferInput {
  @NotBlank(message = "payerId is required")
  @UUID(message = "payerId must be a valid UUID")
  String payerId;

  @NotBlank(message = "payeeId is required")
  @UUID(message = "payerId must be a valid UUID")
  String payeeId;

  @NotNull(message = "value is required")
  @Positive(message = "value must be greater than zero")
  Float value;

}
