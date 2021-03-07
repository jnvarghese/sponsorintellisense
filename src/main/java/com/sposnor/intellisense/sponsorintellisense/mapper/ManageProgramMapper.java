package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.EnrollmentSummary;
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipt;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;

@Mapper
public interface ManageProgramMapper {

	@Select("SELECT SP.SPONSORCODE, P.NAME PARISHNAME, SUM(EN.CONTRIBUTIONAMOUNT) totalContribution, SP.FIRSTNAME spFn, SP. LASTNAME spLn, SP.MIDDLEINITIAL spMI,"
			+ "SP.SPONSORSTATUS, SP.ID spId FROM SPONSOR SP , ENROLLMENT EN, SPONSEE SPE, STUDENT ST, PARISH P WHERE SP.ID = EN.SPONSORID "
			+ "AND EN.ID = SPE.ENROLLMENTID AND SP.PARISHID = P.ID "
			+ "AND ST.ID = SPE.STUDENTID  AND ST.ID = #{studentId}  GROUP BY EN.SPONSORID,SP.FIRSTNAME, SP. LASTNAME, "
			+ "SP.MIDDLEINITIAL, SP.SPONSORSTATUS, SP.ID")
	List<SponsorshipInfo> getSponsorshipInfoByStudentId(@Param("studentId") Long studentId);	
	
	@Select("SELECT DATE_FORMAT(EFFECTIVEDATE, \"%M %Y\") effectiveDate, effectiveDate efdt, renewed, DATE_FORMAT(maxOut, \"%M %Y\") maxOut, maxOut maxOutDate, "
			+ "CONTRIBUTIONAMOUNT contriAmount, MISCAMOUNT miscAmount, SP_CNT.noOfKids noOfKids, SP_CNT.STUDENTNAME, SP_CNT.NAME projectName "
			+ "FROM SPONSEE SP, ENROLLMENT EN , STUDENT_MAXOUT STM, "
			+ "(SELECT COUNT(SP.ID) NOOFKIDS,SP.ENROLLMENTID, ST.STUDENTNAME, P.NAME FROM SPONSEE SP, ENROLLMENT EN, STUDENT ST, PROJECT P "
			+ "WHERE SP.ENROLLMENTID=EN.ID AND EN.SPONSORID=#{sponsorId} AND SP.STATUS=0 AND SP.studentId= ST.id AND ST.projectId=P.id) SP_CNT "
			+ "WHERE  EN.ID = SP.ENROLLMENTID AND EN.ID = STM.ENROLLMENTID AND EN.ID = STM.ENROLLMENTID "
			+ "AND STM.STUDENTID = SP.STUDENTID AND SP.STUDENTID= #{studentId} AND EN.SPONSORID= #{sponsorId} ORDER BY efdt desc ")
	List<Contribution> getSponsorshipContribution(@Param("studentId") Long studentId, @Param("sponsorId") Long sponsorId);	
	
	
	@Select("SELECT EN.ID enrollmentId, sp.id sponsorId,  CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.SPONSORCODE) uniqueId, netAmount CONTRIBUTION, renewed,"
			+ " CASE hasAnyCoSponser WHEN '1' THEN CONCAT(SP.FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(SP.FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',SP.LASTNAME ) END sponsorName, "
			+ " NICKNAME sponsorNickName, P.NAME parishName, DATE_FORMAT(paymentDate, \"%M %D %Y\") paymentDate,"
			+ " DATE_FORMAT(effectiveDate, \"%M %D %Y\") effectiveDate, "
			+ " DATE_FORMAT(EN.createdDate, \"%M %D %Y\") createdDate, "
			+ " DATE_FORMAT(EN.updatedDate, \"%M %D %Y\") updatedDate FROM SPONSOR SP, PARISH P, ENROLLMENT EN,CENTER C,REGION R WHERE P.ID = SP.PARISHID "
			+ " AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ " AND P.ID= #{id} AND EN.STATUS = 0 " 
			+ " AND SPONSORID = SP.ID ORDER BY sponsorName, effectiveDate desc") //  EN.CREATEDDATE DESC
	List<ViewEnroll> selectEnrollments(@Param("id") Long parishId);
	
	@Select("select r.id, receivedfrom,address,parish,missionname,total, paymentmethod, DATE_FORMAT(r.createddate, '%M %d %Y') createddate from enrollment ern, "
			+ "receipt r where ern.receiptId= r.id and ern.id = #{ernId}")
	Receipt getReceipt(@Param("ernId") Long ernId);
	
	@Select("SELECT S.ID sponsorId, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',S.SPONSORCODE) sponsorCode, effectiveDate, MAX(EN.id) enrollmentId, "
			+ "FIRSTNAME sponsorFirstName, LASTNAME sponsorLastName, MIDDLEINITIAL sponsorMi, p.name parishName, p.city parishCity, "
			+ "NICKNAME sponsorNickName, SUM((ROUND(contributionAmount)+ROUND(miscAmount, 2))) contribution FROM ENROLLMENT EN, "
			+ "SPONSOR S, PARISH P, CENTER C,REGION R "
			+ "WHERE EN.sponsorId = S.ID "
			+ "AND S.PARISHID = P.ID  AND P.CENTERID = C.ID AND C.REGIONID = R.ID AND S.PARISHID = #{id} "
			+ "AND EN.RENEWED IN ('Y','N') AND S.SPONSORSTATUS = 0  GROUP BY EN.sponsorId")
	List<EnrollmentSummary> getSummaryByParishId(@Param("id") Long parishId); 
	
	@Select("SELECT E.ID ENROLLMENTID, CONCAT(A.CODE,'-',P.CODE,'-',ST.STUDENTCODE) STUDENTCODE, STM.MAXOUT, DATE_FORMAT(STM.MAXOUT, \"%b\") MAXOUTMONTH, "
			+ "DATE_FORMAT(STM.MAXOUT, \"%Y\") MAXOUTYEAR FROM ENROLLMENT E, SPONSOR SP,STUDENT_MAXOUT STM, STUDENT ST,PROJECT P, AGENCY A  "
			+ "WHERE E.SPONSORID = SP.ID AND SP.PARISHID= #{id} AND E.ID=STM.ENROLLMENTID AND STM.STUDENTID = ST.ID AND ST.PROJECTID = P.ID AND P.AGENCYID = A.ID "
			+ "AND E.STATUS=0 AND STM.STATUS=0 ORDER BY ENROLLMENTID,MAXOUT")
	List<StudentSummary> getStudentByEnrollmentId(@Param("id") Long parishId); 
}
