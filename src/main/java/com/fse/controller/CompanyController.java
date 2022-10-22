package com.fse.controller;

import com.fse.constants.Constants;
import com.fse.dto.CompanyAndStockDto;
import com.fse.dto.CompanyDto;
import com.fse.response.CompanyDetailResponse;
import com.fse.response.CompanyResponse;
import com.fse.service.CompanyService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import javax.validation.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@RestController
@Slf4j
@CrossOrigin(origins="http://localhost:4200")
@RequestMapping(Constants.COMPANY_V1_PATH)
public class CompanyController {

    @Autowired
    CompanyService companyService;


    @ApiOperation(value = "Register a new company",
            notes = "Registers a new company given company details")
    @PostMapping(path = "/register")
    public ResponseEntity<CompanyResponse> registerCompanyDetails(@Valid @RequestBody CompanyDto companyDto) {
        CompanyResponse companyResponse = new CompanyResponse();
        log.info("Register company details --begin");
        companyService.registerCompany(companyDto);
        companyResponse.setMessage(Constants.SUCCESS_MSG);
        log.info("Register company deatils --end");
        return new ResponseEntity<>(companyResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Deletes company and stock price details",
            notes = "Deletes company and stock price details given companyCode")
    @DeleteMapping(path = "delete/{companyCode}")
    public ResponseEntity<CompanyResponse> deleteCompany(@PathVariable("companyCode") String companyCode) {
        CompanyResponse companyResponse = new CompanyResponse();
        log.info("Delete company details --begin");
        int count = companyService.deleteCompany(companyCode);
        if(count>0) {
            companyResponse.setMessage("Deleted company details successfully");
        } else {
            return new ResponseEntity<>(companyResponse,HttpStatus.NO_CONTENT);
        }
        log.info("Delete company details --end");
        return new ResponseEntity<>(companyResponse,HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves all company details with latest stock price details",
            notes = "Retrieves all company details with latest stock price details")
    @GetMapping(path = "/getall")
    public ResponseEntity<CompanyDetailResponse> getAllCompanies()
    {
        log.info("Retrieve all company details --begin");
        CompanyDetailResponse companyDetailResponse =new CompanyDetailResponse();
        try {
            List<CompanyAndStockDto> companyDetails = companyService.getCompanies();
            companyDetailResponse.setStatusMessage(Constants.READ_SUCCESSFULL);
            companyDetailResponse.setCompanyDetails(companyDetails);
        } catch(Exception e) {
            companyDetailResponse.setErrorMessage(Constants.READ_ERROR);
            log.error("Exception occurred while retrieving company details :{}",e.getMessage());
            return new ResponseEntity<>(companyDetailResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Retrieve all company details --end");
        return new ResponseEntity<>(companyDetailResponse,HttpStatus.OK);
    }

    @ApiOperation(value = "Retrieves a company details with latest stock price details",
            notes = "Retrieves a company details with latest stock price details given a company code")
    @GetMapping(path = "/info/{companyCode}")
    public ResponseEntity<CompanyDetailResponse> getCompanyDetails(@PathVariable("companyCode") String companyCode)
    {
        log.info("Retrieve company details for companyCode : {} --begin",companyCode);
        CompanyDetailResponse companyDetailResponse =new CompanyDetailResponse();
        try {
            CompanyAndStockDto companyDetails = companyService.getCompanyDetails(companyCode);
            if(Objects.isNull(companyDetails)) {
                return new ResponseEntity<>(companyDetailResponse,HttpStatus.NO_CONTENT);
            }
            companyDetailResponse.setStatusMessage(Constants.READ_SUCCESSFULL);
            companyDetailResponse.setCompanyDetails(Arrays.asList(companyDetails));
        } catch(Exception e) {
            companyDetailResponse.setErrorMessage(Constants.READ_ERROR);
            log.error("Exception occurred while retrieving company details :{}",e.getMessage());
            return new ResponseEntity<>(companyDetailResponse,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("Retrieve company details for companyCode : {} --end",companyCode);
        return new ResponseEntity<>(companyDetailResponse,HttpStatus.OK);
    }

    @ApiOperation(value = "validates companyCode",
            notes = "validates companyCode")
    @GetMapping(path = "/invalid/{companyCode}")
    public ResponseEntity<Boolean> companyCodeInvalid(@PathVariable("companyCode") String companyCode) {
        boolean invalid = companyService.companyCodeInvalid(companyCode);
        return new ResponseEntity<>(invalid,HttpStatus.OK);
    }
}
