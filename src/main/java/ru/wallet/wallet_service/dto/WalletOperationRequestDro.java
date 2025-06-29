package ru.wallet.wallet_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.wallet.wallet_service.model.OperationType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WalletOperationRequestDro {
    private UUID walletId;
    private long amount;
    private OperationType operationType;
}
