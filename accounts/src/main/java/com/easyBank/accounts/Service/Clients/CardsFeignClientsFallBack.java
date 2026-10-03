package com.easyBank.accounts.Service.Clients;

import com.easyBank.accounts.ClinetDto.CardsDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class CardsFeignClientsFallBack implements CardsFeignClients{
    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public ResponseEntity<CardsDto> fetchCardDetails(String mobileNumber) {
        return null;
    }
}
