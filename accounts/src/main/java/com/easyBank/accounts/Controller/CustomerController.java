package com.easyBank.accounts.Controller;

import com.easyBank.accounts.ClinetDto.CustomerDetailDto;
import com.easyBank.accounts.Service.ICustomerService;
import jakarta.validation.constraints.Pattern;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Validated
public class CustomerController {

    private final ICustomerService moICustomerService;

    public CustomerController(ICustomerService customerService)
    {
        this.moICustomerService = customerService;
    }

    @GetMapping("/fetchCustomerDetail")
    public ResponseEntity<CustomerDetailDto> fetchCardDetails(@RequestParam
                                                              @Pattern(regexp="(^$|[0-9]{10})",message = "Mobile number must be 10 digits")
                                                              String mobileNumber)
    {
      CustomerDetailDto moCustomerDetailDto =  moICustomerService.fetchCustomerDetail(mobileNumber);
      return ResponseEntity.status(HttpStatus.OK).body(moCustomerDetailDto);
    }
}
