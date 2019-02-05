package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;

@Mapper
public interface EnrollmentMapper {
    // #{paymentDate}
	@Insert("INSERT INTO ENROLLMENT (SPONSORID, PAYMENTDATE, EFFECTIVEDATE, actualamount, CONTRIBUTIONAMOUNT, MISCAMOUNT, receiptId, createdBy, createdDate)"
			+ " values  (#{sponsorId}, #{effectiveDate}, #{effectiveDate}, #{actualamount}, #{contributionAmount}, #{miscAmount},#{receiptId}, #{createdBy}, #{createdDate})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Enrollment enrollment);	
	
	@Insert("INSERT INTO SPONSEE (ENROLLMENTID, EXPIRATIONMONTH, EXPIRATIONYEAR, STUDENTID)"
			+ " values  (#{enrollmentId}, #{expirationMonth}, #{expirationYear}, #{studentId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertSponsee(Sponsee sponsee);
	
	@Select("SELECT ID FROM ENROLLMENT WHERE SPONSORID = #{sponsorId} AND PAYMENTDATE = #{paymentDate} AND EFFECTIVEDATE = #{effectiveDate} ORDER BY ID DESC LIMIT 1")
	Enrollment selectEnrollmentForId(@Param("sponsorId") Long sponsorId,@Param("paymentDate") String paymentDate, @Param("effectiveDate") String effectiveDate);
}
