package ru.wallet.wallet_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.wallet.wallet_service.dto.WalletBalanceDto;
import ru.wallet.wallet_service.dto.WalletOperationRequestDro;
import ru.wallet.wallet_service.model.OperationType;
import ru.wallet.wallet_service.model.Wallet;
import ru.wallet.wallet_service.service.WalletService;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WalletController.class)
class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @TestConfiguration
    static class MockConfig {
        @Bean
        @Primary
        public WalletService walletService() {
            return Mockito.mock(WalletService.class);
        }
    }

    @Autowired
    private WalletService walletService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getWalletBalance_returnsOk_whenWalletExists() throws Exception {
        UUID walletId = UUID.randomUUID();
        WalletBalanceDto dto = new WalletBalanceDto(walletId, 500L);

        Mockito.when(walletService.getBalance(walletId)).thenReturn(dto);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(500));
    }

    @Test
    void getWalletBalance_returnsNotFound_whenWalletMissing() throws Exception {
        UUID walletId = UUID.randomUUID();

        Mockito.when(walletService.getBalance(walletId)).thenReturn(null);

        mockMvc.perform(get("/api/v1/wallets/" + walletId))
                .andExpect(status().isNotFound());
    }

    @Test
    void createWallet_returnsCreated_withLocation() throws Exception {
        UUID walletId = UUID.randomUUID();
        Wallet wallet = new Wallet(walletId, 0L);

        Mockito.when(walletService.save(any(Wallet.class))).thenReturn(wallet);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wallet)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.balance").value(0));
    }

    @Test
    void executeOperation_returnsOk_whenOperationSuccess() throws Exception {
        WalletOperationRequestDro request = new WalletOperationRequestDro();
        UUID walletId = UUID.randomUUID();
        request.setWalletId(walletId);
        request.setAmount(100L);
        request.setOperationType(OperationType.DEPOSIT);

        Mockito.when(walletService.chooseOperation(any(WalletOperationRequestDro.class)))
                .thenReturn(new WalletBalanceDto(walletId, 600L));

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.walletId").value(walletId.toString()))
                .andExpect(jsonPath("$.amount").value(100))
                .andExpect(jsonPath("$.operationType").value("DEPOSIT"));
    }

    @Test
    void executeOperation_returnsNotFound_whenWalletMissing() throws Exception {
        WalletOperationRequestDro request = new WalletOperationRequestDro();
        request.setWalletId(UUID.randomUUID());
        request.setAmount(100L);
        request.setOperationType(OperationType.WITHDRAW);

        Mockito.when(walletService.chooseOperation(any(WalletOperationRequestDro.class)))
                .thenReturn(null);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isNotFound());
    }
}