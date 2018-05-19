package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import com.sposnor.intellisense.sponsorintellisense.data.model.Receipt;

@Mapper
public interface ReceiptMapper {

	@Insert("insert into receipt (receivedfrom, address, parish, missionname, total, paymentmethod, createdBy) "
			+ "values (#{receivedfrom}, #{address}, #{parish}, #{missionname}, #{total}, #{paymentmethod}, #{createdby})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Receipt receipt);
	
	
}
