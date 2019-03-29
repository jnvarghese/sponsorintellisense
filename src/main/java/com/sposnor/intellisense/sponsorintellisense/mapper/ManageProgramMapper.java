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
	
	
	@Select("SELECT EN.ID enrollmentId,  CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.SPONSORCODE) uniqueId, netAmount NETCONTRIBUTION, renewed,"
			+ " CASE hasAnyCoSponser WHEN '1' THEN CONCAT(SP.FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(SP.FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',SP.LASTNAME ) END sponsorName, "
			+ " NICKNAME sponsorNickName, P.NAME parishName, DATE_FORMAT(paymentDate, \"%M %D %Y\") paymentDate,"
			+ " DATE_FORMAT(effectiveDate, \"%M %D %Y\") effectiveDate, (CONTRIBUTIONAMOUNT + MISCAMOUNT) CONTRIBUTION, "
			+ " DATE_FORMAT(EN.createdDate, \"%M %D %Y\") createdDate, "
			+ " DATE_FORMAT(EN.updatedDate, \"%M %D %Y\") updatedDate FROM SPONSOR SP, PARISH P, ENROLLMENT EN,CENTER C,REGION R WHERE P.ID = SP.PARISHID "
			+ " AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ " AND P.ID= #{id} AND EN.STATUS = 0 " 
			+ " AND SPONSORID = SP.ID ORDER BY sponsorName, effectiveDate desc") //  EN.CREATEDDATE DESC
	List<ViewEnroll> selectEnrollments(@Param("id") Long parishId);
	
	@Select("select r.id, receivedfrom,address,parish,missionname,total, paymentmethod, DATE_FORMAT(r.createddate, '%M %d %Y') createddate from enrollment ern, "
			+ "receipt r where ern.receiptId= r.id and ern.id = #{ernId}")
	Receipt getReceipt(@Param("ernId") Long ernId);
	
	@Select("SELECT S.ID sponsorId, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',S.SPONSORCODE) sponsorCode, EN.id enrollmentId, "
			+ "FIRSTNAME sponsorFirstName, LASTNAME sponsorLastName, MIDDLEINITIAL sponsorMi, p.name parishName, p.city parishCity, "
			+ "NICKNAME sponsorNickName, (ROUND(contributionAmount)+ROUND(miscAmount, 2)) contribution FROM ENROLLMENT EN, "
			+ "SPONSOR S, PARISH P, CENTER C,REGION R "
			+ "WHERE EN.sponsorId = S.ID "
			+ "AND S.PARISHID = P.ID  AND P.CENTERID = C.ID AND C.REGIONID = R.ID AND S.PARISHID = #{id} AND EN.STATUS= 0 AND S.SPONSORSTATUS = 0")
	List<EnrollmentSummary> getSummaryByParishId(@Param("id") Long parishId); 
	
	@Select(" SELECT CONCAT(A.CODE,'-',P.CODE,'-',ST.STUDENTCODE)  studentCode, SM.maxOut, DATE_FORMAT(SM.maxOut, \"%M\") maxOutMonth,"
			+ " DATE_FORMAT(SM.maxOut, \"%Y\") maxOutYear FROM ENROLLMENT EN, student_maxout SM, STUDENT ST, PROJECT P, AGENCY A "
			+ " WHERE EN.ID = SM.ENROLLMENTID AND SM.STUDENTID = ST.ID AND ST.PROJECTID = P.ID AND P.AGENCYID = A.ID "
			+ "AND EN.ID = #{id} ORDER BY studentCode")
	List<StudentSummary> getStudentByEnrollmentId(@Param("id") Long enrollmentId); 
}
