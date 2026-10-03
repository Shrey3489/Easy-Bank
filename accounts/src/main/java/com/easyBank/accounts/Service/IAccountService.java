package com.easyBank.accounts.Service;

import com.easyBank.accounts.Dto.customerDto;

public interface IAccountService {

    void createAccount(customerDto customerDto);

    customerDto fetchAccount(String mobileNumber);

    public boolean updateAcount(customerDto foCustomerDto);

    public boolean deleteAccount(String customerId);
}
