package ru.wallet.wallet_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.wallet.wallet_service.dto.WalletBalanceDto;
import ru.wallet.wallet_service.dto.WalletOperationRequestDro;
import ru.wallet.wallet_service.model.Wallet;
import ru.wallet.wallet_service.service.WalletService;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(("/api/v1"))
public class WalletController {
    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping("/wallets/{walletId}")
    public ResponseEntity<WalletBalanceDto> getWalletBalance(@PathVariable UUID walletId) {
        WalletBalanceDto walletBalance = walletService.getBalance(walletId);
        return walletBalance != null ? ResponseEntity.ok(walletBalance) : ResponseEntity.notFound().build();
    }

    @PostMapping()
    public ResponseEntity<Wallet> createWallet(@RequestBody Wallet wallet) {
        walletService.save(wallet);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{walletId}")
                .buildAndExpand(wallet.getWalletId())
                .toUri();
        return ResponseEntity.status(HttpStatus.CREATED).location(uri).body(wallet);
    }

    @PostMapping("wallet")
    public ResponseEntity<WalletOperationRequestDro> executeOperation(@RequestBody WalletOperationRequestDro request) {
        return walletService.chooseOperation(request) != null
                ? ResponseEntity.ok().body(request) : ResponseEntity.notFound().build();
    }
}
