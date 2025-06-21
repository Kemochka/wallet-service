package ru.wallet.wallet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class WalletBalanceDto {
    private UUID walletId;
    private long balance;
}
