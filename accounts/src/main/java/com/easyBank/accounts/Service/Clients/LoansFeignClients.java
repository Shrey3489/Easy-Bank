package com.easyBank.accounts.Service.Clients;

import com.easyBank.accounts.ClinetDto.CardsDto;
import com.easyBank.accounts.ClinetDto.LoansDto;
import jakarta.validation.constraints.Pattern;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="loans", fallback = LoansFeignClientsFallBack.class)
public interface LoansFeignClients {

    @GetMapping("/loan/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestParam String mobileNumber);
}
