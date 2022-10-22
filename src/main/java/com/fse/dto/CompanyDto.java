package com.fse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto
{

    @NotNull(message = "companyName cannot be null")
    @NotBlank(message = "companyName cannot be blank")
    private String companyName;
    @NotNull(message = "companyCode cannot be null")
    @NotBlank(message = "companyCode cannot be blank")
    private String companyCode;
    @NotNull(message = "companyCeo cannot be null")
    @NotBlank(message = "companyCeo cannot be blank")
    private String companyCeo;
    @NotNull(message = "companyTurnover cannot be null")
    @DecimalMin(value = "100000001",message="companyTurnover must be greater than 10Cr")
    private BigDecimal companyTurnover;
    @NotNull(message = "companyWebsite cannot be null")
    @NotBlank(message = "companyWebsite cannot be blank")
    private String companyWebsite;
    @NotNull(message = "stockExchangeNames cannot be null")
    @NotBlank(message = "stockExchangeNames cannot be blank")
    private String stockExchangeNames;
}
