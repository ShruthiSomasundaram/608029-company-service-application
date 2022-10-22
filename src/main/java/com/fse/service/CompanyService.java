
package com.fse.service;

import com.fse.dto.CompanyAndStockDto;
import com.fse.dto.CompanyDto;

import java.util.List;

public interface CompanyService {

     CompanyDto registerCompany(CompanyDto companyDto);

     int deleteCompany(String id);

     List<CompanyAndStockDto> getCompanies();

    CompanyAndStockDto getCompanyDetails(String companyCode);

    boolean companyCodeInvalid(String companyCode);
}
