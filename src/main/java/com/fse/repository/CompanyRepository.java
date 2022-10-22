package com.fse.repository;

import com.fse.model.Company;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;


@Repository
public interface CompanyRepository extends MongoRepository<Company, String>
{
}
