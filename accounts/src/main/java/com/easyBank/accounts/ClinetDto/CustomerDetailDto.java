package com.easyBank.accounts.ClinetDto;

import com.easyBank.accounts.Dto.accountDto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CustomerDetailDto {

    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email address is not valid")
    private String email;

    @NotEmpty
    @Pattern(regexp = "($|[0-9]{10})",message = "Mobile Number must 10 digit")
    private String mobileNumber;

    private accountDto moAccountDto;

    private LoansDto moLoansDto;

    private CardsDto moCardsDto;


}
