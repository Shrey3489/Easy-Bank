package com.easyBank.accounts.Service.Clients;

import com.easyBank.accounts.ClinetDto.LoansDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class LoansFeignClientsFallBack implements LoansFeignClients{
    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public ResponseEntity<LoansDto> fetchLoanDetails(String mobileNumber) {
        return null;
    }
}
