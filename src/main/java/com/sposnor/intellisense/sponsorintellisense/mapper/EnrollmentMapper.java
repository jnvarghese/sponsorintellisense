package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;

@Mapper
public interface EnrollmentMapper {
    // #{paymentDate}
	@Insert("INSERT INTO ENROLLMENT (SPONSORID, PAYMENTDATE, EFFECTIVEDATE, actualamount, CONTRIBUTIONAMOUNT, MISCAMOUNT, netAmount, receiptId, createdBy, createdDate)"
			+ " values  (#{sponsorId}, #{effectiveDate}, #{effectiveDate}, #{actualamount}, #{contributionAmount}, #{miscAmount}, #{netAmount}, #{receiptId}, #{createdBy}, #{createdDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Enrollment enrollment);	
	
	@Insert("INSERT INTO ENROLLMENT (ID, SPONSORID, PAYMENTDATE, EFFECTIVEDATE, actualamount, CONTRIBUTIONAMOUNT, MISCAMOUNT, receiptId, createdBy, createdDate)"
			+ " values  (#{id}, #{sponsorId}, #{effectiveDate}, #{effectiveDate}, #{actualamount}, #{contributionAmount}, #{miscAmount},#{receiptId}, #{createdBy}, #{createdDate})")
	void insertWithId(Enrollment enrollment);
	
	@Insert("INSERT INTO SPONSEE (ENROLLMENTID, EXPIRATIONMONTH, EXPIRATIONYEAR, STUDENTID)"
			+ " values  (#{enrollmentId}, #{expirationMonth}, #{expirationYear}, #{studentId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertSponsee(Sponsee sponsee);
	
	@Select("SELECT * FROM SPONSEE WHERE ENROLLMENTID = #{id} AND STATUS =0 ")
	List<Sponsee> findSponseesByEnrollmentId(@Param("id") Long enrollmentId);
	
	//@Update("UPDATE SPONSEE SET STATUS = 1 WHERE id IN (#{id})")
	//void updateSponseeStatus(@Param("id") String ids);
	
	@Update({"<script>",
        "UPDATE SPONSEE SET STATUS = 1 WHERE id IN", 
          "<foreach item='item' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{item}",
          "</foreach>",
        "</script>"})
	void updateSponseeStatus(@Param("list") List<Long> sponseeIds);
	
	@Select("SELECT ID FROM ENROLLMENT WHERE SPONSORID = #{sponsorId} AND PAYMENTDATE = #{paymentDate} AND EFFECTIVEDATE = #{effectiveDate} ORDER BY ID DESC LIMIT 1")
	Enrollment selectEnrollmentForId(@Param("sponsorId") Long sponsorId,@Param("paymentDate") String paymentDate, @Param("effectiveDate") String effectiveDate);

	@Select("SELECT * FROM ENROLLMENT WHERE ID = #{id} AND STATUS =0 ")
	Enrollment findEnrollment(@Param("id") Long enrollmentId);
	
	@Update("UPDATE ENROLLMENT SET STATUS = 1, RENEWED= 'Y', updatedBy= #{updatedBy} WHERE id=#{id}")
	void updateEnrollmentStatus(@Param("id") Long enrollmentId, @Param("updatedBy") int updatedBy);
	
	
}
