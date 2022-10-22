package com.fse.controller;

import com.fse.dto.CompanyAndStockDto;
import com.fse.dto.CompanyDto;
import com.fse.response.CompanyDetailResponse;
import com.fse.response.CompanyResponse;
import com.fse.service.CompanyService;
import com.mongodb.MongoClientException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
public class CompanyControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    CompanyController companyController;

    @Mock
    CompanyService companyService;

    CompanyDto companyDto;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(companyController).build();
        companyDto = new CompanyDto();
        companyDto.setCompanyCode("company1");
        companyDto.setCompanyCeo("Helen");
        companyDto.setCompanyName("Zeta Pvt Ltd");
        companyDto.setCompanyTurnover(new BigDecimal(100000000));
        companyDto.setCompanyWebsite("zeta@abc.com");
        companyDto.setStockExchangeNames("NSE");
    }

    @Test
    public void testRegisterCompanyDetails() {
        Mockito.when(companyService.registerCompany(Mockito.any())).thenReturn(companyDto);
        ResponseEntity<CompanyResponse> companyResponseResponseEntity = companyController.registerCompanyDetails(companyDto);
        Assert.assertEquals( HttpStatus.OK,companyResponseResponseEntity.getStatusCode());
    }

    @Test
    public void testDeleteCompanyDetails() {
        Mockito.when(companyService.deleteCompany(Mockito.anyString())).thenReturn(1);
        ResponseEntity<CompanyResponse> companyResponseResponseEntity = companyController.deleteCompany("company1");
        Assert.assertEquals(HttpStatus.OK,companyResponseResponseEntity.getStatusCode());
        Assert.assertEquals("Deleted company details successfully",companyResponseResponseEntity.getBody().getMessage());

    }

    @Test
    public void testDeleteCompanyDetailsFailed() {
        Mockito.when(companyService.deleteCompany(Mockito.anyString())).thenReturn(0);
        ResponseEntity<CompanyResponse> companyResponseResponseEntity = companyController.deleteCompany("company1");
        Assert.assertEquals(HttpStatus.NO_CONTENT,companyResponseResponseEntity.getStatusCode());
    }

    @Test
    public void testGetAllCompanies() {
        List<CompanyAndStockDto> companyDetails = Arrays.asList(populateCompanyDetails());
        Mockito.when(companyService.getCompanies()).thenReturn(companyDetails);
        ResponseEntity<CompanyDetailResponse> companyResponseResponseEntity = companyController.getAllCompanies();
        Assert.assertEquals( HttpStatus.OK, companyResponseResponseEntity.getStatusCode());
        Assert.assertEquals( 1, companyResponseResponseEntity.getBody().getCompanyDetails().size());

    }

    @Test
    public void testGetAllCompaniesException() {
        Mockito.when(companyService.getCompanies()).thenThrow(MongoClientException.class);
        companyController.getAllCompanies();

    }

    @Test
    public void testGetCompanyDetails() {
        CompanyAndStockDto companyDetails = populateCompanyDetails();
        Mockito.when(companyService.getCompanyDetails(Mockito.anyString())).thenReturn(companyDetails);
        ResponseEntity<CompanyDetailResponse> companyResponseResponseEntity = companyController.getCompanyDetails("company1");
        Assert.assertEquals(HttpStatus.OK,companyResponseResponseEntity.getStatusCode());

    }

    @Test
    public void testGetCompanyDetailsNoContent() {
        CompanyAndStockDto companyDetails = null;
        Mockito.when(companyService.getCompanyDetails(Mockito.anyString())).thenReturn(companyDetails);
        ResponseEntity<CompanyDetailResponse> companyResponseResponseEntity = companyController.getCompanyDetails("company1");
        Assert.assertEquals(HttpStatus.NO_CONTENT,companyResponseResponseEntity.getStatusCode());

    }


    @Test
    public void testGetCompanyDetailsException() {
        Mockito.when(companyService.getCompanyDetails(Mockito.anyString())).thenThrow(MongoClientException.class);
        companyController.getAllCompanies();

    }

    @Test
    public void testCompanyCodeNotInvalid() {
        Mockito.when(companyService.companyCodeInvalid(Mockito.anyString())).thenReturn(false);
        ResponseEntity<Boolean> response = companyController.companyCodeInvalid("company1");
        Assert.assertEquals(false,response.getBody());
    }

    @Test
    public void testCompanyCodeInvalid() {
        Mockito.when(companyService.companyCodeInvalid(Mockito.anyString())).thenReturn(true);
        ResponseEntity<Boolean> response = companyController.companyCodeInvalid("company1");
        Assert.assertEquals(true,response.getBody());
    }

    private CompanyAndStockDto populateCompanyDetails() {
        CompanyAndStockDto companyAndStockDto = new CompanyAndStockDto();
        companyAndStockDto.setCompanyCode("company1");
        companyAndStockDto.setCompanyName("Zeta Pvt Ltd");
        companyAndStockDto.setCompanyWebsite("zeta@abc.com");
        companyAndStockDto.setPrice(14.50);
        return companyAndStockDto;

    }

}
