package com.fse.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document
public class StockPrice 
{
	@Id
	private String id;
	private String companyCode;
	private double price;
	private String date;
	private String time;
}
