package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.MaxOutOverview;

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
			+ "WHERE SM.ENROLLMENTID = E.ID AND DATE(MAXOUT) > CURDATE() AND STATUS <> 1")
	int getCountOfActiveEnrollments();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT < NOW() "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxedOut();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT BETWEEN NOW() AND  DATE_ADD(NOW(), INTERVAL 1 MONTH) "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxOutInOneMonth();
	
	@Select(SELECT_FOR_EXPIRED + " AND MAXOUT BETWEEN DATE_ADD(NOW(), INTERVAL 1 MONTH) AND  DATE_ADD(NOW(), INTERVAL 2 MONTH) "+SELECT_ORDER_BY)
	List<MaxOutOverview> getMaxOutInTwoMonth();
}
