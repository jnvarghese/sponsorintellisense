package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;

@Mapper
public interface StudentMapper {

	@Select("SELECT S.ID, S.PROJECTID, STUDENTNAME, GENDER, DATEOFBIRTH, S.ADDRESS, HOBBIES, S.STATUS,TALENT, "
			+ "RECENTACHIVEMENTS, SOFTLOCKED, P.NAME PROJECTNAME, A.NAME AGENCYNAME, studentCode, imageLinkRef,grade,favColor,favGame,nameOfGuardian,occupationOfGuardian,baseLanguage FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID "
			+ "LEFT JOIN AGENCY A ON P.AGENCYID = A.ID "
			+ "WHERE S.ID = #{id}")
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
	
	@Select("SELECT MX.ENROLLMENTID, S.ID, S.STUDENTNAME, S.GENDER, S.GRADE, DATE_FORMAT(MAXOUT,'%m/%d/%Y') MAXOUT, PRJ.NAME PROJECTNAME, PRJ.CODE PROJECTCODE, ACY.NAME AGENCYNAME, "
			+ "ACY.CODE AGENCYCODE FROM STUDENT S LEFT JOIN STUDENT_MAXOUT MX ON S.ID = MX.STUDENTID, PROJECT PRJ, AGENCY ACY "
			+ "WHERE S.PROJECTID = PRJ.ID AND PRJ.AGENCYID = ACY.ID AND MX.ENROLLMENTID=  #{id} "
			+ "AND S.STATUS = 0 GROUP BY S.ID, S.STUDENTNAME ORDER BY S.STUDENTNAME")
	List<Student> listByEnrollmentId(@Param("id") Long id);
	
	
	@Select("select (CASE WHEN max(seq_val) IS NULL THEN 1000 ELSE max(seq_val) END) sequence from student_sequence where projectId=#{id}")
	Sequence getSequenceByProjectId(@Param("id") Long id);
	
	@Insert("INSERT INTO STUDENT (PROJECTID, STUDENTNAME, GENDER, DATEOFBIRTH, ADDRESS, HOBBIES, TALENT, "
			+ "RECENTACHIVEMENTS, PROFILEPICTURE, SOFTLOCKED,grade,favColor,favGame,nameOfGuardian,occupationOfGuardian,baseLanguage,studentCode, createdBy, createdDate) "
			+ "VALUES (#{projectId}, #{studentName},  #{gender}, #{dateOfBirth}, #{address},"
			+ "#{hobbies}, #{talent}, #{recentAchivements}, #{profilePicture}, #{softlocked}, #{grade}, #{favColor}, #{favGame}, #{nameOfGuardian}, "
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

	@Update("UPDATE STUDENT SET profilePicture = #{profilePicture}, updatedBy= #{updatedBy} WHERE id=#{id}")
	void uploadImage(Student student);
	
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
}
