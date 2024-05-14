package com.gsa.picpaydesafiobackend.infra.repositories.wallet;

import java.util.UUID;
import java.util.Optional;
import java.util.ArrayList;

import com.gsa.picpaydesafiobackend.domain.models.wallet.Wallet;
import com.gsa.picpaydesafiobackend.domain.models.wallet.WalletRepository;

public class FakeWalletRepository implements WalletRepository {
  public ArrayList<FakeWalletDTO> fakeWallets = new ArrayList<>();

  @Override
  public Optional<Wallet> findOneByCustomerId(UUID id) {
    var fakeWalletFound = this.fakeWallets.stream().filter(fakeWallet -> fakeWallet.customerId().equals(id))
        .findFirst();

    if (!fakeWalletFound.isPresent()) {
      return Optional.empty();
    }

    var wallet = Wallet.reconstitute(fakeWalletFound.get().id(), fakeWalletFound.get().customerId(),
        fakeWalletFound.get().balance());

    return Optional.of(wallet);
  }

  @Override
  public void update(Wallet wallet) {
    var fakeWalletFound = this.fakeWallets.stream()
        .filter(fakeWallet -> fakeWallet.customerId().equals(wallet.customerId()))
        .findFirst();

    if (!fakeWalletFound.isPresent()) {
      return;
    }

    var fakeWalletUpdated = new FakeWalletDTO(fakeWalletFound.get().id(), fakeWalletFound.get().customerId(),
        wallet.balance().value());

    this.fakeWallets.remove(fakeWalletFound.get());
    this.fakeWallets.add(fakeWalletUpdated);
  }

}
