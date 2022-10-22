package com.fse.controller;

import com.fse.constants.Constants;
import com.fse.exception.CompanyException;
import com.fse.response.CompanyResponse;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;


import org.springframework.http.HttpStatus;

import java.util.stream.Collectors;

@RestControllerAdvice
public class RestExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<CompanyResponse> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        CompanyResponse companyResponse = new CompanyResponse();
        String finalErrorMessage=   ex.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(","));
        companyResponse.setErrorMessage(finalErrorMessage);
        return new ResponseEntity<>(companyResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<CompanyResponse> handleDuplicateKeyExceptions(
            DuplicateKeyException ex) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setErrorMessage(Constants.DUPLICATE_COMPANY_CODE);
        return new ResponseEntity<>(companyResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CompanyException.class)
    public ResponseEntity<CompanyResponse> handleCompanyException(
            CompanyException ex) {
        CompanyResponse companyResponse = new CompanyResponse();
        companyResponse.setErrorMessage(ex.getMessage());
        return new ResponseEntity<>(companyResponse, HttpStatus.BAD_REQUEST);
    }


}
