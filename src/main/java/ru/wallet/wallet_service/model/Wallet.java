package ru.wallet.wallet_service.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;


@Entity
@Table(name = "wallets")
@Getter
@Setter
public class Wallet {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID walletId;
    @Column(nullable = false)
    private long balance;
}
