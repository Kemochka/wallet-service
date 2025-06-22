package ru.wallet.wallet_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wallet.wallet_service.dto.WalletBalanceDto;
import ru.wallet.wallet_service.dto.WalletOperationRequestDro;
import ru.wallet.wallet_service.model.Wallet;
import ru.wallet.wallet_service.repository.WalletRepository;

import java.util.UUID;

@Service
public class SimpleWalletService implements WalletService {

    private final WalletRepository walletRepository;
    private final WalletDomainService walletDomainService;

    public SimpleWalletService(WalletRepository walletRepository, WalletDomainService walletDomainService) {
        this.walletRepository = walletRepository;
        this.walletDomainService = walletDomainService;
    }

    @Override
    public Wallet save(Wallet wallet) {
        return walletRepository.save(wallet);
    }

    @Transactional
    @Override
    public WalletBalanceDto chooseOperation(WalletOperationRequestDro request) {
        Wallet wallet = walletRepository.findByWalletId(request.getWalletId())
                .orElseThrow(() -> new IllegalStateException("Wallet not found, validate parameters or json"));
        switch (request.getOperationType()) {
            case DEPOSIT -> walletDomainService.deposit(wallet, request.getAmount());
            case WITHDRAW -> walletDomainService.withdraw(wallet, request.getAmount());
        }
        return new WalletBalanceDto(wallet.getWalletId(), wallet.getBalance());
    }

    @Transactional
    @Override
    public WalletBalanceDto getBalance(UUID walletId) {
        Wallet wallet = walletRepository.findByWalletId(walletId)
                .orElseThrow(() -> new IllegalStateException("Wallet not found, validate parameters or json"));
        return new WalletBalanceDto(walletId, wallet.getBalance());
    }
}
