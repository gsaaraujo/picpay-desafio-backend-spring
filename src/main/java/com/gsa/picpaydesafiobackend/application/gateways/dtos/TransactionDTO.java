package com.gsa.picpaydesafiobackend.application.gateways.dtos;

import java.math.BigDecimal;
import java.util.UUID;

public record TransactionDTO(UUID id, UUID payerId, UUID payeeId, BigDecimal balance) {

}
