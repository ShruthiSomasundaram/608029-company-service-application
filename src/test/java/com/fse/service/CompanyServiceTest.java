package com.fse.service;

import com.fse.dto.CompanyAndStockDto;
import com.fse.dto.CompanyDto;
import com.fse.mapper.CompanyMapper;
import com.fse.model.Company;
import com.fse.repository.CompanyRepository;
import com.fse.model.StockPrice;
import com.fse.repository.StockPriceRepository;
import com.fse.service.impl.CompanyServiceImpl;
import com.mongodb.client.result.DeleteResult;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyServiceTest {

    @InjectMocks
    CompanyServiceImpl companyService;

    @Mock
    MongoTemplate mongoTemplate;

    @Mock
    private CompanyRepository companyRepository;
    @Mock
    private StockPriceRepository stockPriceRepository;
    @Mock
    private CompanyMapper companyMapper;

    @Mock
    private AggregationResults<StockPrice> aggregationResults;

    CompanyDto companyDto;

    Company company;

    @Before
    public void setUp() {
        companyDto = new CompanyDto();
        companyDto.setCompanyCode("company1");
        company = new Company();
        company.setCompanyCode("company1");
        company.setCompanyCeo("ceo");
        company.setCompanyName("companyName");
    }

    @Test
    public void testRegisterCompany() {
        Mockito.when(companyMapper.toCompany(Mockito.any())).thenReturn(company);
        Mockito.when(companyRepository.save(Mockito.any())).thenReturn(company);
        Mockito.when(companyMapper.toCompanyDto(Mockito.any())).thenReturn(companyDto);
        CompanyDto companyDtoResponse  = companyService.registerCompany(companyDto);
        Assert.assertEquals("company1",companyDtoResponse.getCompanyCode());
    }

    @Test
    public void testDeleteCompany() {
        Mockito.when(mongoTemplate.findAndRemove(Mockito.any(),Mockito.eq(Company.class))).thenReturn(company);
        Mockito.when(mongoTemplate.remove(Mockito.any(),Mockito.eq(StockPrice.class))).thenReturn(DeleteResult.acknowledged(1));
        int count =companyService.deleteCompany("company1");
        Assert.assertEquals(1,count);


    }

    @Test
    public void testGetCompanies() {
        List<Company> companies = new ArrayList<>();
        companies.add(company);
        List<StockPrice> stockPrices = new ArrayList<>();
        StockPrice stockPrice = new StockPrice();
        stockPrice.setCompanyCode("company1");
        stockPrice.setPrice(14.50);
        Mockito.when(companyRepository.findAll()).thenReturn(companies);
        Mockito.when(mongoTemplate.aggregate(Mockito.any(Aggregation.class),Mockito.anyString(),Mockito.eq(StockPrice.class))).thenReturn(aggregationResults);
        CompanyAndStockDto companyAndStockDto = new CompanyAndStockDto();
        companyAndStockDto.setCompanyCode("company1");
        Mockito.when(companyMapper.getCompanyAndStockDto(Mockito.any())).thenReturn(companyAndStockDto);
        List<CompanyAndStockDto> companyAndStockDtos = companyService.getCompanies();
        Assert.assertEquals(1,companyAndStockDtos.size());
    }

    @Test
    public void testGetCompanyDetails() {
        StockPrice stockPrice = new StockPrice();
        stockPrice.setCompanyCode("company1");
        stockPrice.setPrice(14.50);
        Mockito.when(mongoTemplate.findOne(Mockito.any(),Mockito.eq(Company.class))).thenReturn(company);
        Mockito.when(mongoTemplate.findOne(Mockito.any(),Mockito.eq(StockPrice.class))).thenReturn(stockPrice);
        CompanyAndStockDto companyAndStockDto = new CompanyAndStockDto();
        companyAndStockDto.setCompanyCode("company1");
        Mockito.when(companyMapper.getCompanyAndStockDto(Mockito.any())).thenReturn(companyAndStockDto);
        CompanyAndStockDto companyAndStockDtos = companyService.getCompanyDetails("company1");
        Assert.assertEquals("company1",companyAndStockDtos.getCompanyCode());
    }

    @Test
    public void testCompanyExists() {
        Mockito.when(mongoTemplate.findOne(Mockito.any(),Mockito.eq(Company.class))).thenReturn(company);
        boolean exists = companyService.companyCodeInvalid("company1");
        Assert.assertEquals(false,exists);

    }

    @Test
    public void testCompanyExistsInvalid() {
        Mockito.when(mongoTemplate.findOne(Mockito.any(),Mockito.eq(Company.class))).thenReturn(null);
        boolean exists = companyService.companyCodeInvalid("company1");
        Assert.assertEquals(true,exists);

    }
}
