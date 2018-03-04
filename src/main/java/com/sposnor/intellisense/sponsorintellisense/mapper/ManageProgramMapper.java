package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;

@Mapper
public interface ManageProgramMapper {

	@Select("SELECT SUM(EN.CONTRIBUTIONAMOUNT) totalContribution, SP.FIRSTNAME spFn, SP. LASTNAME spLn, SP.MIDDLEINITIAL spMI,"
			+ "SP.SPONSORSTATUS, SP.ID FROM SPONSOR SP , ENROLLMENT EN, SPONSEE SPE, STUDENT ST WHERE SP.ID = EN.SPONSORID "
			+ "AND EN.ID = SPE.ENROLLMENTID "
			+ "AND ST.ID = SPE.STUDENTID  AND ST.ID = #{studentId}  GROUP BY EN.SPONSORID,SP.FIRSTNAME, SP. LASTNAME, "
			+ "SP.MIDDLEINITIAL, SP.SPONSORSTATUS, SP.ID")
	List<SponsorshipInfo> getSponsorshipInfoByStudentId(@Param("studentId") Long studentId);	
}
