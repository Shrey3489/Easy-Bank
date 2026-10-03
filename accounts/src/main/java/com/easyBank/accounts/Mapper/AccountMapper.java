package com.easyBank.accounts.Mapper;

import com.easyBank.accounts.Dto.accountDto;
import com.easyBank.accounts.Entity.account;

public class AccountMapper {

    public static accountDto mapToAccountsDto(account accounts, accountDto accountsDto) {
        accountsDto.setAccountNumber(accounts.getAccountNumber());
        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        return accountsDto;
    }

    public static account mapToAccounts(accountDto accountsDto, account accounts) {
        accounts.setAccountNumber(accountsDto.getAccountNumber());
        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        return accounts;
    }
}
