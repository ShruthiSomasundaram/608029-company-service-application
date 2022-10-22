package com.fse.repository;

import com.fse.model.StockPrice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockPriceRepository extends MongoRepository<StockPrice, String>
{
    StockPrice findByCompanyCode(String companyCode);
}
