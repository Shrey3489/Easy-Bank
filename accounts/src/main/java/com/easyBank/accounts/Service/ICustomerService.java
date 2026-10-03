package com.easyBank.accounts.Service;

import com.easyBank.accounts.ClinetDto.CustomerDetailDto;

public interface ICustomerService {

    CustomerDetailDto fetchCustomerDetail(String mobileNumber);
}
