package com.fse.mapper;

import com.fse.dto.CompanyAndStockDto;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import com.fse.dto.CompanyDto;
import com.fse.model.Company;

@Component
public class CompanyMapper
{
    public CompanyDto toCompanyDto(Company company)
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(company, CompanyDto.class);
    }

    public Company toCompany(CompanyDto companyDto)
    {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(companyDto, Company.class);
    }

    public CompanyAndStockDto getCompanyAndStockDto(Company company) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper.map(company, CompanyAndStockDto.class);
    }


}
