package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;

@Mapper
public interface EnrollmentMapper {

	@Insert("INSERT INTO ENROLLMENT (SPONSORID, PAYMENTDATE, EFFECTIVEDATE, CONTRIBUTIONAMOUNT, MISCAMOUNT)"
			+ " values  (#{sponsorId}, #{paymentDate}, #{effectiveDate}, #{contributionAmount}, #{miscAmount})")
	@Options(useGeneratedKeys = true, keyProperty = "id")
	void insert(Enrollment enrollment);
}
