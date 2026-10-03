package com.easyBank.accounts.Service;

import com.easyBank.accounts.ClinetDto.CardsDto;
import com.easyBank.accounts.ClinetDto.CustomerDetailDto;
import com.easyBank.accounts.ClinetDto.LoansDto;
import com.easyBank.accounts.Dto.accountDto;
import com.easyBank.accounts.Entity.account;
import com.easyBank.accounts.Entity.customer;
import com.easyBank.accounts.Excepction.ResourceNotFoundExcepction;
import com.easyBank.accounts.Mapper.AccountMapper;
import com.easyBank.accounts.Mapper.CustomerMapper;
import com.easyBank.accounts.Repository.accountRepository;
import com.easyBank.accounts.Repository.customerRepository;
import com.easyBank.accounts.Service.Clients.CardsFeignClients;
import com.easyBank.accounts.Service.Clients.LoansFeignClients;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServicesImpl implements ICustomerService{

    private accountRepository moAccountRepository;
    private customerRepository moCustomerRepository;
    private CardsFeignClients moCardsFeignClients;
    private LoansFeignClients moLoansFeignClients;

    /**
     * @param mobileNumber
     * @return
     */
    @Override
    public CustomerDetailDto fetchCustomerDetail(String mobileNumber) {
        customer mocuCustomer = moCustomerRepository.findBymobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundExcepction("Customer","mobileNumer",mobileNumber));

        account moaccount = moAccountRepository.findByCustomerId(mocuCustomer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundExcepction("Customer","mobileNumer",mobileNumber));

        CustomerDetailDto customerDetailDto = CustomerMapper.mapToCustomerDetailsDto(mocuCustomer, new CustomerDetailDto());
        customerDetailDto.setMoAccountDto(AccountMapper.mapToAccountsDto(moaccount, new accountDto()));

        ResponseEntity<LoansDto> loLoansDto =  moLoansFeignClients.fetchLoanDetails(mobileNumber);
        if(loLoansDto != null) {
            customerDetailDto.setMoLoansDto(loLoansDto.getBody());
        }

        ResponseEntity<CardsDto> loCardsDto =  moCardsFeignClients.fetchCardDetails(mobileNumber);
        if(loCardsDto != null) {
            customerDetailDto.setMoCardsDto(loCardsDto.getBody());
        }

        return customerDetailDto;
    }
}
