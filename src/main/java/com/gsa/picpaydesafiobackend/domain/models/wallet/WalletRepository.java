package com.gsa.picpaydesafiobackend.domain.models.wallet;

import java.util.UUID;
import java.util.Optional;

public interface WalletRepository {
  Optional<Wallet> findOneByCustomerId(UUID id);

  void update(Wallet wallet);
}
