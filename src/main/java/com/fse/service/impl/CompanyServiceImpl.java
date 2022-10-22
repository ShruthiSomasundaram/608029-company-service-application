package com.fse.service.impl;

import com.fse.constants.Constants;
import com.fse.dto.CompanyAndStockDto;
import com.fse.dto.CompanyDto;
import com.fse.mapper.CompanyMapper;
import com.fse.model.Company;
import com.fse.repository.CompanyRepository;
import com.fse.model.StockPrice;
import com.fse.repository.StockPriceRepository;
import com.fse.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;


@Service
@Slf4j
public class CompanyServiceImpl implements CompanyService {

    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private StockPriceRepository stockPriceRepository;
    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public CompanyDto registerCompany(CompanyDto companyDto) {
        log.info("CompanyServiceImpl class registerCompany method - begin");
        Company company = companyMapper.toCompany(companyDto);
        company = companyRepository.save(company);
        companyDto = companyMapper.toCompanyDto(company);
        log.info("CompanyServiceImpl class registerCompany method - end");
        return companyDto;

    }

    @Override
    public int deleteCompany(String companyCode) {
        log.info("CompanyServiceImpl class deleteCompany method - begin");
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.COMPANY_CODE).is(companyCode));
        Company company = mongoTemplate.findAndRemove(query, Company.class);
        if(Objects.nonNull(company)) {
            mongoTemplate.remove(query, StockPrice.class);
            log.info("CompanyServiceImpl class deleteCompany method - end");
            return 1;
        }
        return 0;
    }


    @Override
    public List<CompanyAndStockDto> getCompanies() {
        log.info("CompanyServiceImpl class getCompanies method - begin");
        List<Company> companies = companyRepository.findAll();
        List<String> companyCodes = companies.stream().map(Company::getCompanyCode).collect(Collectors.toList());
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.COMPANY_CODE).in(companyCodes));
        query.with(Sort.by(Sort.Direction.DESC, Constants.DATE)).limit(1);
        List<StockPrice> stockPrices = mongoTemplate.find(query, StockPrice.class);
        Map<String, StockPrice> stockPriceMap = stockPrices.stream().collect(Collectors.toMap(StockPrice::getCompanyCode, stockPrice -> stockPrice));
        List<CompanyAndStockDto> companyAndStockDto = populateCompanyAndStockDto(companies, stockPriceMap);
        log.info("CompanyServiceImpl class getCompanies method - end");
        return companyAndStockDto;
    }

    private List<CompanyAndStockDto> populateCompanyAndStockDto(List<Company> companies, Map<String, StockPrice> stockPriceMap) {
        List<CompanyAndStockDto> companyAndStockDtos = new ArrayList<>();
        for (Company company : companies) {
            CompanyAndStockDto companyAndStockDto = companyMapper.getCompanyAndStockDto(company);
            if (stockPriceMap.containsKey(company.getCompanyCode())) {
                Double stockPrice = stockPriceMap.get(company.getCompanyCode()).getPrice();
                companyAndStockDto.setPrice(stockPrice);
            }
            companyAndStockDtos.add(companyAndStockDto);
        }
        return companyAndStockDtos;
    }

    @Override
    public CompanyAndStockDto getCompanyDetails(String companyCode) {
        log.info("CompanyServiceImpl class getCompanyDetails method for companyCode :{}  - begin",companyCode);
        CompanyAndStockDto companyAndStockDto = null;
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.COMPANY_CODE).in(companyCode));
        Company company = mongoTemplate.findOne(query, Company.class);
        query.with(Sort.by(Sort.Direction.DESC, Constants.DATE)).limit(1);
        StockPrice stockPrice = mongoTemplate.findOne(query, StockPrice.class);
        if (Objects.nonNull(company)) {
            companyAndStockDto = companyMapper.getCompanyAndStockDto(company);
            if(Objects.nonNull(stockPrice))
                companyAndStockDto.setPrice(stockPrice.getPrice());

        }
        log.info("CompanyServiceImpl class getCompanyDetails method for companyCode :{}  - end",companyCode);
        return companyAndStockDto;
    }

    @Override
    public boolean companyCodeInvalid(String companyCode) {
        Query query = new Query();
        query.addCriteria(Criteria.where(Constants.COMPANY_CODE).in(companyCode));
        Company company = mongoTemplate.findOne(query, Company.class);
        if(Objects.nonNull(company)) {
            return false;
        }
        return true;
    }
}


