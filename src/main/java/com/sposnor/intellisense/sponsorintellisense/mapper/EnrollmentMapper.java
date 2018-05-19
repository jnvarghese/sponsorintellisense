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

	@Insert("INSERT INTO ENROLLMENT (SPONSORID, PAYMENTDATE, EFFECTIVEDATE, CONTRIBUTIONAMOUNT, MISCAMOUNT, receiptId, createdBy)"
			+ " values  (#{sponsorId}, #{paymentDate}, #{effectiveDate}, #{contributionAmount}, #{miscAmount},#{receiptId}, #{createdBy})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Enrollment enrollment);	
	
	@Insert("INSERT INTO SPONSEE (ENROLLMENTID, EXPIRATIONMONTH, EXPIRATIONYEAR, STUDENTID)"
			+ " values  (#{enrollmentId}, #{expirationMonth}, #{expirationYear}, #{studentId})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insertSponsee(Sponsee sponsee);
	
	@Select("SELECT ID FROM ENROLLMENT WHERE SPONSORID = #{sponsorId} AND PAYMENTDATE = #{paymentDate} AND EFFECTIVEDATE = #{effectiveDate} LIMIT 1")
	Enrollment selectEnrollmentForId(@Param("sponsorId") Long sponsorId,@Param("paymentDate") String paymentDate, @Param("effectiveDate") String effectiveDate);
}
