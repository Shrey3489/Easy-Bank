package com.easyBank.accounts.Controller;

import com.easyBank.accounts.Constants.accountConstants;
import com.easyBank.accounts.Dto.AccountsContactInfoDto;
import com.easyBank.accounts.Dto.ResponseDto;
import com.easyBank.accounts.Dto.customerDto;
import com.easyBank.accounts.Service.IAccountService;
import io.github.resilience4j.retry.annotation.Retry;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/account",produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Validated
public class AccountController {

    private IAccountService iAccountService;

    @Autowired
    private AccountsContactInfoDto accountsContactInfoDto;

    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody customerDto moCustomerDto)
    {
        iAccountService.createAccount(moCustomerDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(accountConstants.STATUS_201,accountConstants.MESSAGE_201));
    }

    @GetMapping("/fetch")
    public ResponseEntity<customerDto> fetchAccountDetail(@RequestParam
                                                              @Pattern(regexp = "($|[0-9]{10})",message = "Mobile Number must 10 digit")
                                                              String mobileNumber)
    {
       customerDto moCustomerDto = iAccountService.fetchAccount(mobileNumber);
       return  ResponseEntity.status(HttpStatus.OK).body(moCustomerDto);
    }

    @PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetail(@Valid @RequestBody customerDto foCustomerDto)
    {
        boolean isUpdate = iAccountService.updateAcount(foCustomerDto);
        if(isUpdate)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(accountConstants.STATUS_200,accountConstants.STATUS_200));
        }
        else
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(accountConstants.STATUS_500,accountConstants.STATUS_500));
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccount(@RequestParam
                                                         @Pattern(regexp = "($|[0-9]{10})",message = "Mobile Number must 10 digit")
                                                         String mobileNumber)
    {
        boolean isDeleted = iAccountService.deleteAccount(mobileNumber);
        if(isDeleted)
        {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(accountConstants.STATUS_200,accountConstants.STATUS_200));
        }
        else
        {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(accountConstants.STATUS_500,accountConstants.STATUS_500));
        }
    }

    @Retry(name = "getContactInfo", fallbackMethod = "getContactInfoFallBack")
    @GetMapping("/contact-info")
    public ResponseEntity<AccountsContactInfoDto> getContactInfo() {
        System.out.println("Attempt");

        throw new RuntimeException("Testing retry");
//        return ResponseEntity
//                .status(HttpStatus.OK)
//                .body(accountsContactInfoDto);
    }

    public ResponseEntity<AccountsContactInfoDto> getContactInfoFallBack(Throwable throwable) {
        System.out.println("Fallback executed: " + throwable.getMessage());

    return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(null);
    }

}
