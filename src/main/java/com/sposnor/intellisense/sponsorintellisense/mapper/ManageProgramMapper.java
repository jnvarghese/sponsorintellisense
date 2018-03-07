package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;

@Mapper
public interface ManageProgramMapper {

	@Select("SELECT SUM(EN.CONTRIBUTIONAMOUNT) totalContribution, SP.FIRSTNAME spFn, SP. LASTNAME spLn, SP.MIDDLEINITIAL spMI,"
			+ "SP.SPONSORSTATUS, SP.ID spId FROM SPONSOR SP , ENROLLMENT EN, SPONSEE SPE, STUDENT ST WHERE SP.ID = EN.SPONSORID "
			+ "AND EN.ID = SPE.ENROLLMENTID "
			+ "AND ST.ID = SPE.STUDENTID  AND ST.ID = #{studentId}  GROUP BY EN.SPONSORID,SP.FIRSTNAME, SP. LASTNAME, "
			+ "SP.MIDDLEINITIAL, SP.SPONSORSTATUS, SP.ID")
	List<SponsorshipInfo> getSponsorshipInfoByStudentId(@Param("studentId") Long studentId);	
	
	@Select("SELECT DATE_FORMAT(PAYMENTDATE, \"%M %Y\") paymentDate, DATE_FORMAT(maxOut, \"%M %Y\") maxOut, "
			+ "CONTRIBUTIONAMOUNT contriAmount, MISCAMOUNT miscAmount "
			+ "FROM SPONSEE SP, ENROLLMENT EN , STUDENT_MAXOUT STM WHERE  EN.ID = SP.ENROLLMENTID AND EN.ID = STM.ENROLLMENTID "
			+ "AND STM.STUDENTID = SP.STUDENTID AND SP.STUDENTID= #{studentId} AND EN.SPONSORID= #{sponsorId}")
	List<Contribution> getSponsorshipContribution(@Param("studentId") Long studentId, @Param("sponsorId") Long sponsorId);	
	
}
