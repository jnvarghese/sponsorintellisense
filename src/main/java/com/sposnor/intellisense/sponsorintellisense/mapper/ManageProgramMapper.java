package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipt;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;

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
	
	
	@Select("SELECT EN.ID enrollmentId,  CONCAT(U.firstname,' ', U.lastname) createdBy, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.SPONSORCODE) uniqueId, "
			+ "CASE hasAnyCoSponser WHEN '1' THEN CONCAT(SP.FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(SP.FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',SP.LASTNAME ) END sponsorName, "
			+ "NICKNAME sponsorNickName, P.NAME parishName, DATE_FORMAT(effectiveDate, \"%M %D %Y\") paymentDate,(CONTRIBUTIONAMOUNT + MISCAMOUNT) CONTRIBUTION, "
			+ "EN.CREATEDDATE FROM SPONSOR SP, PARISH P, ENROLLMENT EN, USERS U, CENTER C,REGION R WHERE P.ID = SP.PARISHID "
			+ "AND EN.CREATEDBY = U.ID AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND P.ID= #{id} "
			+ "AND SPONSORID = SP.ID ORDER BY sponsorName") //  EN.CREATEDDATE DESC
	List<ViewEnroll> selectEnrollments(@Param("id") Long parishId);
	
	@Select("select r.id, receivedfrom,address,parish,missionname,total, paymentmethod, DATE_FORMAT(r.createddate, '%M %d %Y') createddate from enrollment ern, "
			+ "receipt r where ern.receiptId= r.id and ern.id = #{ernId}")
	Receipt getReceipt(@Param("ernId") Long ernId);
}
