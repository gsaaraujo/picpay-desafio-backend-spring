package com.gsa.picpaydesafiobackend.infra.repositories.wallet;

import java.util.UUID;
import java.math.BigDecimal;

public record FakeWalletDTO(UUID id, UUID customerId, BigDecimal balance) {

}
