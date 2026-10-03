package com.easyBank.accounts.Dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;

@Data
public class accountDto {

    @NotEmpty
    @Pattern(regexp = "($|[0-9]{10})",message = "Mobile Number must 10 digit")
    private Long accountNumber;

    @NotEmpty(message = "account Type cannot be empty")
    private String accountType;

    @NotEmpty(message = "branch Address cannot be empty")
    private String branchAddress;
}
