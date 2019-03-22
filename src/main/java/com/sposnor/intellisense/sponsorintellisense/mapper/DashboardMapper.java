package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.MaxOutOverview;
import com.sposnor.intellisense.sponsorintellisense.data.model.graph.GraphData;
import com.sposnor.intellisense.sponsorintellisense.data.model.graph.Receipt;

@Mapper
public interface DashboardMapper {

	static final String SELECT_FOR_EXPIRED = "SELECT R.NAME REGIONNAME, C.NAME CENTERNAME, P.NAME PARISHNAME,ROUND(EN.MISCAMOUNT,2) miscAmount, "
			+ "CASE hasAnyCoSponser WHEN '1' THEN CONCAT(SP.FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(SP.FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',SP.LASTNAME ) END SPONSORNAME, "
			+ "DATE_FORMAT(MAXOUT,'%M %D %Y') MAXOUT "
			+ "FROM SPONSOR_MAXOUT SM, ENROLLMENT EN, SPONSOR SP, PARISH P, CENTER C, REGION R "
			+ "WHERE EN.ID = SM.ENROLLMENTID AND SM.SPONSORID = SP.ID AND SP.PARISHID = P.ID "
			+ "AND P.CENTERID = C.ID AND C.REGIONID = R.ID";
			//+ " ";
	
	static final String SELECT_ORDER_BY = "ORDER BY REGIONNAME, CENTERNAME, PARISHNAME;";
			
	@Select("SELECT COUNT(*) studentCount FROM STUDENT WHERE STATUS=0")
	int getCountOfActiveStudent();
	
	@Select("SELECT COUNT(*) sponsorCount FROM SPONSOR WHERE sponsorStatus=0")
	int getCountOfActiveSponsor();
	
	@Select("SELECT COUNT(ENROLLMENTID) enrollmentCount FROM STUDENT_MAXOUT SM , ENROLLMENT E "
			+ "WHERE SM.ENROLLMENTID = E.ID AND DATE(MAXOUT) > CURDATE() AND E.STATUS = 0")
	int getCountOfActiveEnrollments();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT < NOW() "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxedOut();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT BETWEEN NOW() AND  DATE_ADD(NOW(), INTERVAL 1 MONTH) "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxOutInOneMonth();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT BETWEEN DATE_ADD(NOW(), INTERVAL 1 MONTH) AND  DATE_ADD(NOW(), INTERVAL 2 MONTH) "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxOutInTwoMonth();
	
	@Select("SELECT COUNT(*) YAXIS, DATE_FORMAT(EFFECTIVEDATE, \"%M %Y\") XAXIS FROM ENROLLMENT WHERE STATUS=0 GROUP BY XAXIS ORDER BY EFFECTIVEDATE")
	List<GraphData> getEnrollmentEffectiveDataElement();
	
	@Select("SELECT COUNT(*) YAXIS, DATE_FORMAT(MAXOUT, \"%M %Y\") XAXIS FROM SPONSOR_MAXOUT, ENROLLMENT EN WHERE "
			+ "ENROLLMENTID = EN.ID AND EN.STATUS=0 GROUP BY XAXIS ORDER BY MAXOUT")
	List<GraphData> getEnrollmentExiprationDataElement();
	
	@Select("SELECT COUNT(*) YAXIS, CONCAT(R.NAME,'-',COUNT(*)) XAXIS FROM SPONSOR SP, PARISH P , CENTER C, REGION R "
			+ "WHERE SP.PARISHID = P.ID AND P.CENTERID = C.ID AND C.REGIONID=R.ID AND SP.SPONSORSTATUS = 0 GROUP BY R.NAME")
	List<GraphData> getSponsorsByRegion();
	
	@Select("SELECT COUNT(*) YAXIS, CONCAT(R.NAME,' ',C.NAME, '-',COUNT(*)) XAXIS FROM SPONSOR SP, PARISH P , CENTER C, REGION R "
			+ "WHERE SP.PARISHID = P.ID AND P.CENTERID = C.ID AND C.REGIONID=R.ID AND SP.SPONSORSTATUS = 0 GROUP BY R.NAME, C.NAME")
	List<GraphData> getSponsorsByCenter();
	
	@Select("SELECT COUNT(*) TOTAL,INITIATIVEID,TYPE, DATE_FORMAT(STR_TO_DATE(RDATE, '%m/%d/%Y'), '%M %Y') RECEIPTDATE "
			+ "FROM RECEIPTS WHERE INITIATIVEID IS NOT NULL GROUP BY RECEIPTDATE")
	List<Receipt> getReceipts();
	
	@Select("SELECT COUNT(*) YAXIS, DATE_FORMAT(CREATEDDATE, \"%M %Y\") XAXIS  FROM SPONSOR WHERE SPONSORSTATUS=0 GROUP BY XAXIS ORDER BY CREATEDDATE")
	List<GraphData> getSponsors();
	
	@Select("SELECT SUM(CONTRIBUTIONAMOUNT+MISCAMOUNT) YAXIS, CONCAT(P.NAME,'- ',P.CITY) XAXIS, COUNT(SP.ID) XAXIS2 "
			+ "FROM ENROLLMENT EN, SPONSOR SP, PARISH P WHERE EN.SPONSORID = SP.ID AND SP.PARISHID = P.ID  "
			+ "AND SP.SPONSORSTATUS= 0 AND EN.STATUS =0 GROUP BY P.ID ORDER BY YAXIS DESC")
	List<GraphData> getContributionsAndSponsorCount();
}
