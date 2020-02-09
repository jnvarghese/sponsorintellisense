package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReceipts;

@Mapper
public interface ReceiptsMapper {
	
	
	@Insert("insert into receipts (rdate, firstName, middleName, lastName, fullName, transaction, amount,"
			+ "initiativeId, streetAddress, city, state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby) "
			+ "values (#{rdate}, #{firstName}, #{middleName}, #{lastName}, #{fullName}, #{transaction}, #{amount},"
			+ "#{initiativeId}, #{streetAddress}, #{city}, #{state}, #{zipCode}, #{receiptType}, #{referenceId}, #{email1}, #{email2},"
			+ "#{phone1}, #{phone2}, #{type}, #{status}, #{createdby})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "receiptId",
			before = false, resultType= Long.class)
	void insert(Receipts receipt);
	
	@Select("SELECT receiptId, rdate, r.firstName,  r.middleName,  r.lastName, fullName, transaction, amount,initiativeId, "
			+ "streetAddress, r.city, state, zipCode, centerId, receiptType, referenceId, email1, email2, phone1, phone2, r.type, "
			+ "r.status, r.createdby  FROM receipts r left join organization org on org.id = referenceId  left join parish p "
			+ "on p.id=referenceId, initiative i, users u WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id and "
			+ "receiptId = #{id}")
	Receipts findById(@Param("id") Long id);
	
	/*@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, city, "
			+ "state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby FROM receipts "		
			+ "WHERE receiptId = #{receiptId}")
	List<Receipts> listByParishId(@Param("parishId") Long parishId);*/
	
	@Select("SELECT r.receiptId, rdate, receiptType,referenceId, r.firstName, r.middleName, r.lastName, r.amount, org.name orgName, p.name parishName, "
			+ "i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, sr.sponsorId "
			+ "FROM sponsor_receipts sr right JOIN receipts r ON r.receiptId = sr.receiptId and sr.STATUS<>1 left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, "
			+ "initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id  "
			+ "and date_format(str_to_date(rdate, '%m/%d/%Y'), '%Y-%m-%d') >= CURDATE() - INTERVAL #{range} DAY GROUP BY r.receiptId order by r.receiptId desc;")
	List<Receipts> listByRange(@Param("range") int range);
	
	
	@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, city, "
			+ "state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby FROM receipts "		
			+ "WHERE receiptId = #{id}")
	List<Receipts> list();
	
	
	@Update("UPDATE receipts SET rdate= #{rdate} , firstName = #{firstName}, "
			+ "middleName= #{middleName}, lastName= #{lastName}, fullName= #{fullName},"
			+ " transaction= #{transaction}, amount= #{amount}, initiativeId= #{initiativeId}, streetAddress= #{streetAddress}, "
			+ " city= #{city}, state= #{state}, zipCode= #{zipCode}, receiptType = #{receiptType}, "
			+ " referenceId = #{referenceId}, email1= #{email1}, email2= #{email2},"
			+ " phone1= #{phone1}, phone2= #{phone2}, type= #{type}, status= #{status}, updatedBy = #{updatedBy} WHERE receiptId=#{receiptId}")	
	void update(Receipts r);

	@Select("SELECT receiptId, rdate, receiptType,referenceId,transaction, r.firstName, r.middleName, r.lastName,  fullName, amount, "
			+ "org.name orgName, p.name parishName, p.city parishCity, r.streetAddress, r.city, r.state, r.zipCode, "
			+ "i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName "
			+ "FROM receipts r left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, "
			+ "initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id  and receiptId = #{receiptId}")
	Receipts getReceipt(@Param("receiptId") Long rId);
	
	@Insert("INSERT INTO sponsor_receipts(sponsorId, receiptId, amount, createdBy) values (#{sponsorId}, #{receiptId}, #{amount}, #{createdBy})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id", before = false, resultType= Long.class)
	void insertSponsorReceipts(SponsorReceipts sr);
	
	@Select("SELECT sponsorId, receiptId from sponsor_receipts where id=#{id} and status <> 1")
	SponsorReceipts getSponsorReceipt(@Param("id") Long id);
	
	@Select("SELECT id, sponsorId, receiptId,amount from sponsor_receipts where receiptId=#{receiptId} and status <> 1")
	List<SponsorReceipts> listByReceiptId(@Param("receiptId") Long receiptId);
	
	@Update("UPDATE sponsor_receipts SET status= 1,updatedBy = #{userId}, updatedDate=#{currentTime}  WHERE id=#{id}")	
	void deleteSponsorReceipts(@Param("id") Long id, @Param("userId") Long userId, @Param("currentTime") String currentTime);
}
