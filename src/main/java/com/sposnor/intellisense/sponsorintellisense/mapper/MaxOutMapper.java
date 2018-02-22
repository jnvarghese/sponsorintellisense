package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;

@Mapper
public interface MaxOutMapper {

	@Insert("insert into sponsor_maxout (sponsorId, enrollmentId, maxOut) values (#{sponsorId}, #{enrollmentId}, #{maxOut})")
	void insertSponsorMaxOut(SponsorMaxOut e);
	
	@Insert("insert into student_maxout (studentId, enrollmentId, maxOut) values (#{studentId}, #{enrollmentId}, #{maxOut})")
	void insertStudentMaxOut(StudentMaxOut e);
	
}
