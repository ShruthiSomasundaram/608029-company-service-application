package com.fse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class Company {

    @Id
    private String id;
    private String companyName;
    private String companyCode;
    private String companyCeo;
    private BigDecimal companyTurnover;
    private String companyWebsite;
    private String stockExchangeNames;

}