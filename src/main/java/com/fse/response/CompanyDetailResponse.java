package com.fse.response;

import com.fse.dto.CompanyAndStockDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CompanyDetailResponse {
    private String statusMessage;
    private String errorMessage;
    private List<CompanyAndStockDto> companyDetails;
}
