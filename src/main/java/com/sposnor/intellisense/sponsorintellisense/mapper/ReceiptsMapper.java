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
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentExtendedMonth;

@Mapper
public interface ReceiptsMapper {
	
	
	@Insert("insert into receipts (rdate, firstName, middleName, lastName, fullName, coSponsorName, transaction, amount, amountInWords, "
			+ "initiativeId, streetAddress, city, state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby) "
			+ "values (#{rdate}, #{firstName}, #{middleName}, #{lastName}, #{fullName}, #{coSponsorName}, #{transaction}, #{amount}, #{amountInWords}, "
			+ "#{initiativeId}, #{streetAddress}, #{city}, #{state}, #{zipCode}, #{receiptType}, #{referenceId}, #{email1}, #{email2},"
			+ "#{phone1}, #{phone2}, #{type}, #{status}, #{createdby})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "receiptId",
			before = false, resultType= Long.class)
	void insert(Receipts receipt);
	
	@Select("SELECT r.receiptId, rdate, receiptType,referenceId, r.firstName, r.middleName, r.lastName, coSponsorName, r.amount, amountInWords, org.name orgName, p.name parishName, " + 
			" i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, sr.sponsorId, sum(sr.amount) sponsorReceiptAmount, r.uploaded, remoteFileName " + 
			" FROM sponsor_receipts sr right JOIN receipts r ON r.receiptId = sr.receiptId and sr.STATUS<>1 left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, " + 
			" initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id and r.receiptId = #{id}")
	List<Receipts> receiptsById(@Param("id") Long id);  
	
	@Select("SELECT r.receiptId, rdate, receiptType,referenceId, r.firstName, r.middleName, r.lastName, coSponsorName, r.amount, amountInWords, org.name orgName, p.name parishName, " + 
			" i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, sr.sponsorId, sum(sr.amount) sponsorReceiptAmount, r.uploaded, remoteFileName " + 
			" FROM sponsor_receipts sr right JOIN receipts r ON r.receiptId = sr.receiptId and sr.STATUS<>1 left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, " + 
			" initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id "
			+ "AND r.FIRSTNAME LIKE CONCAT(#{firstName}, '%') AND r.LASTNAME LIKE CONCAT(#{lastName}, '%') GROUP BY r.receiptId order by firstname, lastname;")
	List<Receipts> receiptsByMatchingFnAndLn(@Param("firstName") String firstName, @Param("lastName") String lastName);  
	
	/*@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, transaction, amount,initiativeId, streetAddress, city, "
			+ "state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby FROM receipts "		
			+ "WHERE receiptId = #{receiptId}")
	List<Receipts> listByParishId(@Param("parishId") Long parishId);*/
	
	@Select("SELECT r.receiptId, rdate, receiptType,referenceId, r.firstName, r.middleName, r.lastName, coSponsorName, r.amount,"
			+ " CONCAT(r.streetAddress, ' ,', r.city, ' ,', r.state) receiptAddress, amountInWords, org.name orgName, p.name parishName, "
			+ "i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, streetAddress, r.city, state, sr.sponsorId, sum(sr.amount) sponsorReceiptAmount, r.uploaded, remoteFileName "
			+ "FROM sponsor_receipts sr right JOIN receipts r ON r.receiptId = sr.receiptId and sr.STATUS<>1 left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, "
			+ "initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id  "
			+ "and date_format(str_to_date(rdate, '%m/%d/%Y'), '%Y-%m-%d') >= CURDATE() - INTERVAL #{range} DAY GROUP BY r.receiptId order by r.receiptId desc;")
	List<Receipts> listByRange(@Param("range") int range);
	
	
	@Select("SELECT receiptId, rdate, firstName, middleName, lastName, fullName, coSponsorName, transaction, amount, amountInWords, initiativeId, subInitiativeId, streetAddress, city, "
			+ "state, zipCode, receiptType, referenceId, email1, email2, phone1, phone2, type, status, createdby, uploaded, remoteFileName FROM receipts "		
			+ "WHERE receiptId = #{id}")
	List<Receipts> list(); // @Deprecated
	
	@Select("SELECT r.receiptId, rdate, noOfRenewal, receiptType,referenceId, r.firstName, r.middleName, r.lastName, r.coSponsorName, r.amount, amountInWords, org.name orgName, p.name parishName, "
			+ "i.name initiativeName, email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, sr.sponsorId, sum(sr.amount) sponsorReceiptAmount, r.uploaded, remoteFileName "
			+ "FROM sponsor_receipts sr right JOIN receipts r ON r.receiptId = sr.receiptId and sr.STATUS<>1 left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, "
			+ "initiative i, users u  WHERE r.status=0 and initiativeId = i.id and r.createdby = u.id  "
			+ "and noOfRenewal>0 and r.type=#{type} GROUP BY r.receiptId order by r.receiptId desc;")
	List<Receipts> listByType(@Param("type") int type); 
	
	
	@Update("UPDATE receipts SET rdate= #{rdate} , firstName = #{firstName}, "
			+ "middleName= #{middleName}, lastName= #{lastName}, fullName= #{fullName},"
			+ " transaction= #{transaction}, amount= #{amount}, amountInWords= #{amountInWords}, initiativeId= #{initiativeId}, streetAddress= #{streetAddress}, "
			+ " city= #{city}, state= #{state}, zipCode= #{zipCode}, receiptType = #{receiptType}, "
			+ " referenceId = #{referenceId}, email1= #{email1}, email2= #{email2},"
			+ " phone1= #{phone1}, phone2= #{phone2}, type= #{type}, status= #{status}, updatedBy = #{updatedBy} WHERE receiptId=#{receiptId}")	
	void update(Receipts r);
	
	@Update("UPDATE receipts SET referenceId = #{referenceId}, updatedBy = #{updatedBy} WHERE receiptId=#{receiptId}")	
	void updateReferenceId(@Param("referenceId") Long referenceId,  
							@Param("receiptId") Long receiptId, 
							@Param("updatedBy") Long updatedBy);
	
	@Update("UPDATE receipts SET referenceId = #{referenceId}, updatedBy = #{updatedBy}, type = #{donationType} WHERE receiptId=#{receiptId}")	
	void updateReferenceIdAndDonationType(@Param("referenceId") Long referenceId,  
							@Param("receiptId") Long receiptId, 
							@Param("updatedBy") Long updatedBy,
							@Param("donationType") int donationType);

	@Select("SELECT receiptId, rdate, receiptType,referenceId,transaction, r.firstName, r.middleName, r.lastName,  fullName, amount,initiativeId, "
			+ "coSponsorName , amountInWords, org.name orgName, p.name parishName, p.city parishCity, r.streetAddress, r.city, r.state, r.zipCode, "
			+ "email1, phone1, r.type, concat(u.firstname, ' ', u.lastname) createdbyName, r.uploaded, remoteFileName "
			+ "FROM receipts r left join organization org on org.id = referenceId  left join parish p on p.id=referenceId, "
			+ "users u  WHERE r.status=0 AND r.createdby = u.id  and receiptId = #{receiptId}")
	Receipts getReceipt(@Param("receiptId") Long rId);
	
	@Insert("INSERT INTO sponsor_receipts(sponsorId, receiptId, amount, type, createdBy, noOfRenewal) "
			+ "values (#{sponsorId}, #{receiptId}, #{amount}, #{type}, #{createdBy}, #{noOfRenewal})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id", before = false, resultType= Long.class)
	void insertSponsorReceipts(SponsorReceipts sr);
	
	@Update("update sponsor_receipts set (sponsorId, receiptId, amount, type, createdBy, noOfRenewal) "
			+ "values (#{sponsorId}, #{receiptId}, #{amount}, #{type}, #{createdBy}, #{noOfRenewal})")
	void updateSponsorReceipts(SponsorReceipts sr);
	
	@Insert("INSERT INTO student_extended_month(receiptId, studentId, month) "
			+ "values (#{receiptId}, #{studentId}, #{month})")
	void insertStudentExtendedMonth(StudentExtendedMonth sem);
	
	@Select("SELECT sponsorId, receiptId, noOfRenewal from sponsor_receipts where id=#{id} and status <> 1")
	SponsorReceipts getSponsorReceipt(@Param("id") Long id);
	
	@Select("SELECT sponsorId, receiptId, noOfRenewal from sponsor_receipts where receiptId=#{id} and status <> 1")
	SponsorReceipts getSponsorReceiptByReceiptId(@Param("id") Long id);
	
	@Select("SELECT sponsorId, receiptId, noOfRenewal from sponsor_receipts where receiptId=#{id} and sponsorId= #{sponsorId} and status <> 1")
	SponsorReceipts getSponsorReceiptByReceiptIdAndSponsorId(@Param("id") Long id, @Param("sponsorId") Long sponsorId);
	
	@Select("SELECT id, sponsorId, receiptId, sum(amount) from sponsor_receipts where receiptId=#{receiptId} and status <> 1 GROUP BY sponsorId")
	List<SponsorReceipts> listByReceiptId(@Param("receiptId") Long receiptId);
	
	@Update("UPDATE sponsor_receipts SET status= 1,updatedBy = #{userId}, updatedDate=#{currentTime}  WHERE id=#{id}")	
	void deleteSponsorReceipts(@Param("id") Long id, @Param("userId") Long userId, @Param("currentTime") String currentTime);
	
	@Select(" SELECT SR.SPONSORID, SUM(CASE WHEN SR.AMOUNT = 0 THEN R.AMOUNT ELSE 0 END) AS RECEIPTAMOUNT," + 
			" SUM(SR.AMOUNT) SPONSORRECEIPTAMOUNT " + 
			" FROM SPONSOR_RECEIPTS SR LEFT JOIN RECEIPTS R ON R.RECEIPTID=SR.RECEIPTID,  SPONSOR S" + 
			" WHERE  SR.`STATUS` <> 1   AND R.REFERENCEID = #{parishId} AND SR.SPONSORID = S.ID" + 
			" GROUP BY SR.SPONSORID  ORDER BY SR.SPONSORID, R.RECEIPTID;")
	List<SponsorReceipts> getContributionsByParishId(@Param("parishId") Long parishId);
	
	@Select("SELECT sr.receiptId,SUM(sr.amount) sponsorReceiptAmount FROM sponsor_receipts sr "
			+ "WHERE sr.receiptId=#{receiptId} and status=0 GROUP BY sr.receiptId")
	Receipts getSponsorReceiptAmount(@Param("receiptId") Long receiptId);
	
	/**
	 * This method will be used to consolidate the receipt details.
	 * @param parishId
	 * @return
	 */
	@Select("SELECT sr.sponsorId, r.receiptId, rdate, TRANSACTION, receiptType, sr.amount, sr.type FROM sponsor s, sponsor_receipts sr, receipts r "
			+ "WHERE sponsorid= s.id AND sr.receiptId = r.receiptId and s.parishId = #{id}")
	List<SponsorReceipts> getReceiptDetails(@Param("id") Long parishId);
	
	
	@Update("UPDATE RECEIPTS SET uploaded= 1, remoteFileName= #{fileName} WHERE receiptId=#{id}")	
	void updateReceiptUploadStatus(@Param("id") Long receiptId, @Param("fileName") String fileName);
}
