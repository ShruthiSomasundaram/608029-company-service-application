package com.fse.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fse.config.BigDecimalSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyAndStockDto {

    private String id;
    private String companyName;
    private String companyCode;
    private String companyCeo;
    @JsonSerialize(using = BigDecimalSerializer.class)
    private BigDecimal companyTurnover;
    private String companyWebsite;
    private String stockExchangeNames;
    private double price;
}