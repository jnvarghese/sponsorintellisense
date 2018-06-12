package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;

@Mapper
public interface ReceiptsMapper {

	@Insert("insert into receipts (rdate, firstName, middleName, lastName, fullName, transaction, amount,"
			+ "initiativeId, streetAddress, city, state, zipCode, parishId, email1, email2, phone1, phone2, type, status, createdby) "
			+ "values (#{rdate}, #{firstName}, #{middleName}, #{lastName}, #{fullName}, #{transaction}, #{amount},"
			+ "#{initiativeId}, #{streetAddress}, #{city}, #{state}, #{zipCode}, #{parishId}, #{email1}, #{email2},"
			+ "#{phone1}, #{phone2}, #{type}, #{status}, #{createdby})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "receiptId",
			before = false, resultType= Long.class)
	void insert(Receipts receipt);
	
	@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, r.city, "
			+ "state, zipCode, centerId, parishId, email1, email2, phone1, phone2, type, r.status, r.createdby FROM receipts r, center c, parish p "		
			+ "WHERE parishid = p.id and p.centerId = c.id and receiptId = #{id}")
	Receipts findById(@Param("id") Long id);
	
	@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, city, "
			+ "state, zipCode, parishId, email1, email2, phone1, phone2, type, status, createdby FROM receipts "		
			+ "WHERE parishId = #{parishId}")
	List<Receipts> listByParishId(@Param("parishId") Long parishId);
	
	
	@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, city, "
			+ "state, zipCode, parishId, email1, email2, phone1, phone2, type, status, createdby FROM receipts "		
			+ "WHERE receiptId = #{id}")
	List<Receipts> list();
	
	
	@Update("UPDATE receipts SET rdate= #{rdate} , firstName = #{firstName}, "
			+ "middleName= #{middleName}, lastName= #{lastName}, fullName= #{fullName},"
			+ " transaction= #{transaction}, amount= #{amount}, initiativeId= #{initiativeId}, streetAddress= #{streetAddress}, "
			+ " city= #{city}, state= #{state}, zipCode= #{zipCode}, parishId= #{parishId}, email1= #{email1}, email2= #{email2},"
			+ " phone1= #{phone1}, phone2= #{phone2}, type= #{type}, status= #{status}, updatedBy = #{updatedBy} WHERE receiptId=#{receiptId}")	
	void update(Receipts r);
}
