package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSubstitution;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;

@Mapper
public interface StudentMapper {

	@Select("SELECT S.ID, CONCAT(A.CODE,'-',P.CODE,'-',STUDENTCODE) studentUniqueCode, uploadstatus, S.PROJECTID, STUDENTNAME, GENDER, DATEOFBIRTH, S.ADDRESS, HOBBIES, S.STATUS,TALENT, "
			+ "RECENTACHIVEMENTS, SOFTLOCKED, P.NAME PROJECTNAME, P.ADDRESS PROJECTADDRESS, A.NAME AGENCYNAME, studentCode, imageLinkRef,grade,favColor,favGame,nameOfGuardian,occupationOfGuardian,baseLanguage, "
			+ "DATE_FORMAT(SMAX.maxOut, '%M %Y') renewalDue FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID "
			+ "LEFT JOIN AGENCY A ON P.AGENCYID = A.ID, STUDENT_MAXOUT SMAX "
			+ "WHERE S.ID = #{id} AND S.ID = SMAX.STUDENTID")
	Student findById(@Param("id") Long id);
	
	@Select("SELECT S.ID, STUDENTNAME, GENDER, P.NAME projectName, A.NAME agencyName, studentCode, imageLinkRef FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE S.STATUS = 0 ")
	List<Student> list();
	////+ "IF(profilePicture IS NULL,0,1) imagePresent "
	@Select("SELECT S.ID, STUDENTNAME, GENDER, P.NAME projectName, A.NAME agencyName, studentCode, imageLinkRef, uploadstatus"
			+ " FROM STUDENT S "
			+ " LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE "
			+ " projectid IN ( #{id} ) AND S.STATUS = 0  ORDER BY studentCode")
	List<Student> listByProjectId(@Param("id") Long id);
	
	@Select("SELECT S.ID FROM STUDENT S LEFT JOIN PROJECT P ON S.PROJECTID = P.ID WHERE  projectid IN ( #{id} ) AND S.STATUS = #{status}")
	List<Student> listByProjectIdAndStatus(@Param("id") Long id, @Param("status") int status);
	
	@Select("SELECT S.ID STUDENTID,S.STUDENTNAME, S.STUDENTCODE, GENDER, GRADE, NAMEOFGUARDIAN, OCCUPATIONOFGUARDIAN, DATEOFBIRTH, "
			+ "DATE_FORMAT(SM.MAXOUT, '%b %Y') maxout ,DATEDIFF(SM.MAXOUT, NOW()) AS DAYS, SP.FIRSTNAME SPONSORFIRSTNAME, SP.MIDDLEINITIAL SPONSORMIDDLEINITIAL, "
			+ "SP.LASTNAME SPONSORLASTNAME,SP.SPONSORCODE,PR.NAME PARISHNAME,PR.CITY PARISHCITY FROM STUDENT S , PROJECT P, "
			+ "STUDENT_MAXOUT SM, ENROLLMENT E, SPONSOR SP, PARISH PR WHERE PROJECTID IN (#{id}) AND S.PROJECTID = P.ID AND "
			+ "S.ID=SM.STUDENTID AND SP.PARISHID=PR.ID AND E.SPONSORID =SP.ID AND E.ID =SM.ENROLLMENTID AND S.STATUS=0 AND "
			+ "E.STATUS=0 GROUP BY S.ID ORDER BY SM.MAXOUT, NAMEOFGUARDIAN")
	List<StudentSummary> summaryByProjectId(@Param("id") Long id);
	
	@Select("SELECT ST.id studentId, ST.projectId projectId, E.ID enrollmentId,E.SPONSORID sponsorId, E.EFFECTIVEDATE effectiveDate, ST.ID studentId, "
			+ "ST.STUDENTNAME studentName,ST.STUDENTCODE studentCode,ST.STATUS STATUS, ST.GENDER, ST.GRADE, "
			+ "S.EXPIRATIONMONTH maxOutMonth,S.EXPIRATIONYEAR maxOutYear,P.NAME projectName FROM ENROLLMENT E, "
			+ "SPONSEE S, STUDENT ST, PROJECT P WHERE E.ID=S.ENROLLMENTID AND S.STUDENTID = ST.ID "
			+ "AND ST.PROJECTID=P.ID AND E.SPONSORID=#{sponsorId} AND E.STATUS =0 AND S.STATUS =0")
	List<StudentSummary> summaryBySponsorId(@Param("sponsorId") Long id);		
	
    @Select("SELECT E.ID enrollmentId, A.ID AGENCYID, A.NAME AGENCYNAME, PROJECTID,SP.PARISHID PARISHID,P.NAME PROJECTNAME, S.ID STUDENTID,S.STUDENTNAME, CONCAT(A.CODE,'-',P.CODE,'-',S.STUDENTCODE) UNIQUEID, "
    		+ "GENDER, GRADE, NAMEOFGUARDIAN, OCCUPATIONOFGUARDIAN, DATEOFBIRTH, DATE_FORMAT(SM.MAXOUT, '%b %Y') maxout,"
    		+ "DATEDIFF(SM.MAXOUT, NOW()) AS DAYS, "
    		+ "SP.FIRSTNAME SPONSORFIRSTNAME, SP.MIDDLEINITIAL SPONSORMIDDLEINITIAL, SP.LASTNAME SPONSORLASTNAME,SP.SPONSORCODE, "
    		+ "SP.ID SPONSORID,PR.NAME PARISHNAME,PR.CITY PARISHCITY FROM STUDENT S , PROJECT P, AGENCY A, STUDENT_MAXOUT SM, "
    		+ "ENROLLMENT E, SPONSOR SP, PARISH PR WHERE  S.PROJECTID = P.ID AND S.ID=SM.STUDENTID AND SP.PARISHID=PR.ID "
    		+ "AND E.SPONSORID =SP.ID AND P.AGENCYID =A.ID AND SM.STATUS = 0 AND E.ID =SM.ENROLLMENTID AND S.STATUS=1 AND E.STATUS=0 "
    		+ "ORDER BY A.NAME, P.NAME, STUDENTNAME")
	List<StudentSummary> getActiveInactiveStudent();
    
	@Select("SELECT studentCode, MX.ENROLLMENTID, S.ID, S.STUDENTNAME, S.GENDER, S.GRADE, DATE_FORMAT(MAXOUT,'%m/%d/%Y') MAXOUT, PRJ.NAME PROJECTNAME, PRJ.CODE PROJECTCODE, ACY.NAME AGENCYNAME, "
			+ "ACY.CODE AGENCYCODE FROM STUDENT S LEFT JOIN STUDENT_MAXOUT MX ON S.ID = MX.STUDENTID, PROJECT PRJ, AGENCY ACY "
			+ "WHERE S.PROJECTID = PRJ.ID AND PRJ.AGENCYID = ACY.ID AND MX.ENROLLMENTID=  #{id} "
			+ "AND S.STATUS = 0 GROUP BY S.ID, S.STUDENTNAME ORDER BY S.STUDENTNAME")
	List<Student> listByEnrollmentId(@Param("id") Long id);
	
	
	@Select("select (CASE WHEN max(seq_val) IS NULL THEN 1000 ELSE max(seq_val) END) sequence from student_sequence where projectId=#{id}")
	Sequence getSequenceByProjectId(@Param("id") Long id);
	
	@Insert("INSERT INTO STUDENT (PROJECTID, STUDENTNAME, GENDER, DATEOFBIRTH, ADDRESS, HOBBIES, TALENT, "
			+ "RECENTACHIVEMENTS, SOFTLOCKED,grade,favColor,favGame,nameOfGuardian,occupationOfGuardian,baseLanguage,studentCode, createdBy, createdDate) "
			+ "VALUES (#{projectId}, #{studentName},  #{gender}, #{dateOfBirth}, #{address},"
			+ "#{hobbies}, #{talent}, #{recentAchivements}, #{softlocked}, #{grade}, #{favColor}, #{favGame}, #{nameOfGuardian}, "
			+ "#{occupationOfGuardian}, #{baseLanguage}, #{studentCode}, #{createdBy}, #{createdDate})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Student student);
	
	@Update("UPDATE STUDENT SET projectId= #{projectId} , studentName = #{studentName}, "
			+ "gender= #{gender}, dateOfBirth= #{dateOfBirth}, address= #{address},"
			+ " status= #{status}, hobbies= #{hobbies}, talent= #{talent}, recentAchivements= #{recentAchivements}, "
			+ " grade= #{grade}, favColor= #{favColor}, favGame= #{favGame}, nameOfGuardian= #{nameOfGuardian}, occupationOfGuardian= #{occupationOfGuardian}, baseLanguage= #{baseLanguage},"
			+ " softlocked= #{softlocked}, studentCode= #{studentCode}, updatedBy= #{updatedBy} WHERE id=#{id}")	
	void update(Student student);
	
	@Update("UPDATE STUDENT SET uploadstatus= #{uploadstatus} WHERE id=#{id}")	
	void updateUploadStatus(Student student);

	
	
	@Select("SELECT ID, STUDENTNAME, studentCode FROM STUDENT WHERE STATUS = 0 AND FIRSTNAME LIKE #{name} ")
	List<Student> searchByName(@Param("name") String name);
	
	// 
	@Select("SELECT S.ID, S.STUDENTNAME, S.GENDER, S.GRADE, MAX(MAXOUT) MAXOUT "
			+ "FROM STUDENT S LEFT JOIN STUDENT_MAXOUT MX ON S.ID = MX.STUDENTID, PARISH_PROJECT PP "
			+ "WHERE S.PROJECTID = PP.PROJECTID AND PP.PROJECTID = #{projectId} "
			+ "AND (MAXOUT IS NULL OR DATE(MAXOUT) < #{effectiveDate}) "
			+ "AND S.STATUS = 0 GROUP BY S.ID, S.STUDENTNAME ORDER BY S.STUDENTNAME")
	List<Student> findStudentsBySponsorshipStatus(
			@Param("name") String name,
			@Param("projectId") Long projectId,
			@Param("effectiveDate") String effectiveDate
			);
	
	@Select("SELECT S.ID, S.STUDENTCODE, S.STUDENTNAME, S.GENDER, S.GRADE FROM STUDENT S LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID, "
			+ "PARISH_PROJECT PP WHERE S.PROJECTID = PP.PROJECTID  AND PP.PARISHID = #{parishId} "
			+ "AND PP.PROJECTID = #{projectId} AND SE.expirationMonth IS NULL "
			+ "AND S.STATUS = 0 ORDER BY S.STUDENTNAME")
	List<Student> findUnEnrolledStudents(
			@Param("parishId") Long parishId,
			@Param("projectId") Long projectId
			);
	
	@Select("SELECT *,p.name as projectName FROM STUDENT ST, PROJECT P WHERE ST.PROJECTID = P.ID AND ST.ID NOT IN (SELECT DISTINCT STUDENTID FROM SPONSEE SP, STUDENT ST WHERE "
			+ "SP.STUDENTID = ST.ID AND ST.PROJECTID= #{projectId} AND SP.STATUS = 0 AND ST.STATUS=0) AND PROJECTID= #{projectId} AND ST.STATUS=0")
	List<Student> availableStudentsByProject(@Param("projectId") Long projectId);
	
	@Select("SELECT S.ID, S.STUDENTCODE, S.STUDENTNAME, S.GENDER, S.GRADE FROM STUDENT S LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID, "
			+ "PARISH_PROJECT PP WHERE S.PROJECTID = PP.PROJECTID and parishId=#{parishId} AND SE.expirationMonth IS NULL AND STUDENTNAME "
			+ "LIKE CONCAT(#{name}, '%') AND S.STATUS = 0 AND PP.STATUS=0  GROUP BY s.id ORDER BY S.STUDENTNAME")
	List<Student> findUnEnrolledStudentsByParishAndName(@Param("parishId") Long parishId,@Param("name") String name);
	
	@Select("SELECT S.ID, S.STUDENTCODE, S.STUDENTNAME, S.GENDER, S.GRADE, P.NAME projectName FROM STUDENT S LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID, "
			+ "PARISH_PROJECT PP, PROJECT P WHERE S.PROJECTID = PP.PROJECTID AND PP.PROJECTID= P.ID and PP.PARISHID=#{parishId} AND SE.expirationMonth IS NULL "
			+ "AND S.STATUS = 0 AND PP.STATUS=0  GROUP BY s.id ORDER BY P.NAME, S.STUDENTNAME")
	List<Student> findUnEnrolledStudentsByParish(@Param("parishId") Long parishId);
	
	@Select("SELECT S.ID, STUDENTNAME, studentCode FROM STUDENT S "
			+ "LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID "
			+ "WHERE S.STATUS = 0 AND STUDENTNAME LIKE #{name}  GROUP BY S.ID, STUDENTNAME, studentCode")
	List<Student> listMatchingStudentsByName(@Param("name") String name);
	
	
	@Select("SELECT CONCAT(A.CODE,'-',P.CODE,'-',ST.STUDENTCODE) UNIQUEID, STUDENTNAME,ST.ID studentId, uploadstatus, P.ID projectId, imageLinkRef,"
			+ " DATE_FORMAT(str_to_date(DATEOFBIRTH, '%m/%d/%Y'), '%M %d %Y') DATEOFBIRTH, GENDER, GRADE,FAVCOLOR,FAVGAME,NAMEOFGUARDIAN,OCCUPATIONOFGUARDIAN,BASELANGUAGE,"
			+ " HOBBIES hobby, A.NAME AGENCYNAME, P.NAME PROJECTNAME, P.ADDRESS ADDRESS,"
			+ " DATE_FORMAT(EN.effectiveDate, '%M %Y') startingDate, DATE_FORMAT(SMAX.maxOut, '%M %Y') renewalDue FROM ENROLLMENT EN, SPONSEE SPE,"
			+ " STUDENT_MAXOUT SMAX, STUDENT ST, PROJECT P, AGENCY A WHERE EN.ID = SPE.ENROLLMENTID AND SPE.STUDENTID = ST.ID"
			+ " AND ST.ID = SMAX.STUDENTID AND EN.ID = SMAX.ENROLLMENTID AND ST.PROJECTID = P.ID AND P.AGENCYID = A.ID"
			+ " AND EN.ID = #{id} ORDER BY SMAX.maxOut ")
	List<SponseeReport> listSponseesByEnrolmentId(@Param("id") Long id);

	@Select("SELECT CONCAT(A.CODE,'-',P.CODE,'-',ST.STUDENTCODE) STUDENTUNIQUECODE,STUDENTNAME,ST.ID,P.ID PROJECTID, GENDER, "
			+ "GRADE,A.NAME AGENCYNAME,P.NAME PROJECTNAME FROM STUDENT ST,SPONSEE SP, PROJECT P, AGENCY A WHERE ST.PROJECTID = P.ID "
			+ "AND P.AGENCYID = A.ID AND ST.ID = SP.STUDENTID AND ST.STATUS = 0 AND STUDENTNAME LIKE CONCAT(#{name}, '%') "
			+ "GROUP BY STUDENTNAME")
	List<Student> search(@Param("name") String name);
	
	@Select("SELECT E.ID ENROLLMENTID, E.EFFECTIVEDATE,E.ACTUALAMOUNT,E.CONTRIBUTIONAMOUNT,E.MISCAMOUNT, "
			+ "CONCAT(U.FIRSTNAME,' ', U.LASTNAME) CREATEDBY,E.CREATEDDATE,SM.STUDENTID,SM.MAXOUT,SP.ID SPONSEEID,SM.ID STUMAXOUTID, "
			+ "ST.STUDENTNAME, ST.STUDENTCODE FROM ENROLLMENT E, SPONSEE SP, STUDENT_MAXOUT SM , STUDENT ST, USERS U "
			+ "WHERE E.ID=SP.ENROLLMENTID AND E.ID = SM.ENROLLMENTID AND SP.STUDENTID=ST.ID AND SP.STUDENTID = SM.STUDENTID "
			+ "AND E.CREATEDBY = U.ID AND SPONSORID=#{sponsorId} AND E.STATUS=0")
	List<StudentSummary> getEnrollmentBySponsorId(@Param("sponsorId") Long sponsorId);
	
	@Select("SELECT * from sponsee where studentId= #{studentId} and enrollmentId=#{enrollmentId}")
	Sponsee findSponsee(@Param("studentId") Long studentId, @Param("enrollmentId") Long enrollmentId);
	
	@Insert("INSERT INTO student_substitution (id, enrollmentId, expirationMonth, expirationYear, studentId, substitutedStudentId, createdBy, createdDate, maxOut, reason) "
			+ "VALUES (#{id}, #{enrollmentId}, #{expirationMonth}, #{expirationYear}, #{studentId}, #{substitutedStudentId}, #{createdBy}, #{createdDate}, #{maxOut}, #{reason})")
	void insertSponseeSoftDelete(StudentSubstitution sp);
	
	@Select("delete from sponsee where id= #{id}")
	void deleteSponsee(@Param("id") Long id);
	
	@Insert("INSERT INTO SPONSEE (ENROLLMENTID, EXPIRATIONMONTH, EXPIRATIONYEAR, STUDENTID) values (#{enrollmentId}, #{expirationMonth}, #{expirationYear}, #{studentId})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()",  keyProperty="id", before = false, resultType= Long.class)
	long insertSponsee(Sponsee sponsee);
	
	@Select("SELECT * from student_maxout where studentId= #{studentId} and enrollmentId=#{enrollmentId}")
	StudentMaxOut findStudentMaxOut(@Param("studentId") Long studentId, @Param("enrollmentId") Long enrollmentId);
	
	@Update("UPDATE student_maxout SET status= 1 WHERE id=#{id}")	
	void updateStudentMaxOut(@Param("id") int id);
	
	@Insert("insert into student_maxout (studentId, enrollmentId, maxOut) values (#{studentId}, #{enrollmentId}, #{maxOut})")
	void insertStudentMaxOut(StudentMaxOut e);
}
