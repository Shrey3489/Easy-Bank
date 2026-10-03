package com.easyBank.accounts.Service;

import com.easyBank.accounts.Constants.accountConstants;
import com.easyBank.accounts.Dto.accountDto;
import com.easyBank.accounts.Dto.customerDto;
import com.easyBank.accounts.Entity.account;
import com.easyBank.accounts.Entity.customer;
import com.easyBank.accounts.Excepction.CustomerAlreadyExistExcepction;
import com.easyBank.accounts.Excepction.ResourceNotFoundExcepction;
import com.easyBank.accounts.Mapper.AccountMapper;
import com.easyBank.accounts.Mapper.CustomerMapper;
import com.easyBank.accounts.Repository.accountRepository;
import com.easyBank.accounts.Repository.customerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountServiceImpl implements IAccountService{

    private accountRepository moAccountRepository;
    private customerRepository moCustomerRepository;

    @Override
    public void createAccount(customerDto customerDto) {

        Optional<customer> optionalCustomer = moCustomerRepository.findBymobileNumber(customerDto.getMobileNumber());
        if(optionalCustomer.isPresent())
        {
            throw new CustomerAlreadyExistExcepction("Customer already registered by the Phone number "+customerDto.getMobileNumber());
        }
        customer moCustomer = CustomerMapper.mapToCustomer(customerDto, new customer());
        customer savedCustomer = moCustomerRepository.save(moCustomer);
        moAccountRepository.save(createNewCustomer(savedCustomer));
    }

    private account createNewCustomer(customer foCustomer)
    {
        account moAccount = new account();
        moAccount.setCustomerId(foCustomer.getCustomerId());
        long randomAccNumber = 1000000000L +new Random().nextInt(900000000);
        moAccount.setAccountNumber(randomAccNumber);
        moAccount.setAccountType(accountConstants.SAVINGS);
        moAccount.setBranchAddress(accountConstants.ADDRESS);
        return moAccount;
    }

    @Override
    public customerDto fetchAccount(String mobileNumber) {
        customer mocuCustomer = moCustomerRepository.findBymobileNumber(mobileNumber).orElseThrow(
        () -> new ResourceNotFoundExcepction("Customer","mobileNumer",mobileNumber));

        account moaccount = moAccountRepository.findByCustomerId(mocuCustomer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundExcepction("Customer","mobileNumer",mobileNumber));

        customerDto newcust = CustomerMapper.mapToCustomerDto(mocuCustomer,new customerDto());
        newcust.setMoAccountDto(AccountMapper.mapToAccountsDto(moaccount, new accountDto()));
        return  newcust;
    }

    /**
     * @param foCustomerDto
     * @return
     */
    @Override
    public boolean updateAcount(customerDto foCustomerDto) {
        boolean isUpdated = false;
        accountDto loAccountDto = foCustomerDto.getMoAccountDto();
        if(loAccountDto!=null)
        {
            account loAccount = moAccountRepository.findById(loAccountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundExcepction("Account", "Account Number ",loAccountDto.getAccountNumber().toString())
            );

            AccountMapper.mapToAccounts(loAccountDto,loAccount);
            loAccount = moAccountRepository.save(loAccount);

            Long customerId = loAccount.getCustomerId();
            customer loCustomer = moCustomerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundExcepction("Customer","Customer Id",customerId.toString())
            );

            CustomerMapper.mapToCustomer(foCustomerDto,loCustomer);
            moCustomerRepository.save(loCustomer);
            isUpdated=true;

        }
        return isUpdated;
    }

    /**
     * @param customerId
     * @return
     */
    @Override
    public boolean deleteAccount(String MobileNumber) {
        customer loCustomer = moCustomerRepository.findBymobileNumber(MobileNumber).orElseThrow(
                () -> new ResourceNotFoundExcepction("Customer","mobileNumber",MobileNumber)
        );
        moAccountRepository.deleteByCustomerId(loCustomer.getCustomerId());
        moCustomerRepository.deleteById(loCustomer.getCustomerId());

        return true;
    }

}
