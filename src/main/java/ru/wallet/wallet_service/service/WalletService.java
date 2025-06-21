package ru.wallet.wallet_service.service;

import ru.wallet.wallet_service.dto.WalletBalanceDto;
import ru.wallet.wallet_service.dto.WalletOperationRequestDro;
import ru.wallet.wallet_service.model.Wallet;

import java.util.UUID;

public interface WalletService {
    Wallet save(Wallet wallet);

    WalletBalanceDto chooseOperation(WalletOperationRequestDro request);

    WalletBalanceDto getBalance(UUID walletId);

}
