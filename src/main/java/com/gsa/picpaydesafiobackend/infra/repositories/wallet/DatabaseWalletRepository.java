package com.gsa.picpaydesafiobackend.infra.repositories.wallet;

import java.util.UUID;
import java.util.Optional;

import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;

import com.gsa.picpaydesafiobackend.infra.dao.WalletDAO;
import com.gsa.picpaydesafiobackend.infra.dao.CustomerDAO;
import com.gsa.picpaydesafiobackend.infra.dao.orms.WalletORM;
import com.gsa.picpaydesafiobackend.domain.models.wallet.Wallet;
import com.gsa.picpaydesafiobackend.domain.models.wallet.WalletRepository;

@Repository
public class DatabaseWalletRepository implements WalletRepository {
  @Autowired
  private WalletDAO walletDAO;
  @Autowired
  private CustomerDAO customerDAO;

  @Override
  public Optional<Wallet> findOneByCustomerId(UUID id) {
    var walletFound = this.walletDAO.findByCustomerId(id);

    if (!walletFound.isPresent()) {
      return Optional.empty();
    }

    var wallet = Wallet.reconstitute(walletFound.get().id(), walletFound.get().customer().id(),
        walletFound.get().balance());

    return Optional.of(wallet);
  }

  @Override
  public void update(Wallet wallet) {
    var customerDAO = this.customerDAO.findById(wallet.customerId()).orElseThrow();
    var walletORM = new WalletORM(wallet.id(), customerDAO, wallet.balance().value());
    this.walletDAO.save(walletORM);
  }

}
