package com.easyBank.accounts.Mapper;

import com.easyBank.accounts.ClinetDto.CustomerDetailDto;
import com.easyBank.accounts.Dto.customerDto;
import com.easyBank.accounts.Entity.customer;

public class CustomerMapper {

    public static customerDto mapToCustomerDto(customer customer, customerDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }

    public static customer mapToCustomer(customerDto customerDto, customer customer) {
        customer.setName(customerDto.getName());
        customer.setEmail(customerDto.getEmail());
        customer.setMobileNumber(customerDto.getMobileNumber());
        return customer;
    }

    public static CustomerDetailDto mapToCustomerDetailsDto(customer customer, CustomerDetailDto customerDto) {
        customerDto.setName(customer.getName());
        customerDto.setEmail(customer.getEmail());
        customerDto.setMobileNumber(customer.getMobileNumber());
        return customerDto;
    }
}
