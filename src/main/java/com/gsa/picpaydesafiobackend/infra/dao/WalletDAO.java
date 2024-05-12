package com.gsa.picpaydesafiobackend.infra.dao;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.gsa.picpaydesafiobackend.infra.dao.orms.WalletORM;

public interface WalletDAO extends CrudRepository<WalletORM, UUID> {
  Optional<WalletORM> findByCustomerId(UUID customerId);
}
