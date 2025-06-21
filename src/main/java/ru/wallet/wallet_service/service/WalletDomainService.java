package ru.wallet.wallet_service.service;

import org.springframework.stereotype.Service;
import ru.wallet.wallet_service.model.Wallet;

@Service
public class WalletDomainService {

    protected void deposit(Wallet wallet, long amount) {
        if (amount < 0) {
            throw new IllegalStateException("Amount must be greater than zero");
        }
        wallet.setBalance(wallet.getBalance() + amount);
    }

    protected void withdraw(Wallet wallet, long amount) {
        if (amount <= 0) {
            throw new IllegalStateException("Amount must be greater than zero");
        }
        if (wallet.getBalance() < amount) {
            throw new IllegalStateException("Not enough balance");
        }
        wallet.setBalance(wallet.getBalance() - amount);
    }
}
