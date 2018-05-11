package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
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
	
	@Select("SELECT S.ID, STUDENTNAME, GENDER, P.NAME projectName, A.NAME agencyName, studentCode, imageLinkRef FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE "
			+ " projectid IN ( #{id} ) AND S.STATUS = 0 ")
	List<Student> listByProjectId(@Param("id") Long id);
	
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
			+ " softlocked= #{softlocked}, updatedBy= #{updatedBy} WHERE id=#{id}")	
	void update(Student student);

	@Update("UPDATE STUDENT SET profilePicture = #{profilePicture}, updatedBy= #{updatedBy} WHERE id=#{id}")
	void uploadImage(Student student);
	
	@Select("SELECT ID, STUDENTNAME, studentCode FROM STUDENT WHERE STATUS = 0 AND FIRSTNAME LIKE #{name} ")
	List<Student> searchByName(@Param("name") String name);
	
	@Select("SELECT S.ID, S.STUDENTNAME, MAX(MAXOUT) MAXOUT "
			+ "FROM STUDENT S LEFT JOIN STUDENT_MAXOUT MX ON S.ID = MX.STUDENTID, PARISH_PROJECT PP "
			+ "WHERE S.PROJECTID = PP.PROJECTID AND PP.PROJECTID = #{projectId} "
			+ "AND (MAXOUT IS NULL OR DATE(MAXOUT) < #{effectiveDate}) "
			+ "AND S.STATUS = 0 GROUP BY S.ID, S.STUDENTNAME")
	List<Student> findStudentsBySponsorshipStatus(
			@Param("name") String name,
			@Param("projectId") Long projectId,
			@Param("effectiveDate") String effectiveDate
			);
	
	@Select("SELECT S.ID, STUDENTNAME, studentCode FROM STUDENT S "
			+ "LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID "
			+ "WHERE S.STATUS = 0 AND FIRSTNAME LIKE #{name}  GROUP BY S.ID, STUDENTNAME, studentCode")
	List<Student> listMatchingStudentsByName(@Param("name") String name);
	
	
	@Select("SELECT EN.ID, DATE_FORMAT(SM.maxOut, '%M %Y') renewalDue, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.SPONSORCODE) UNIQUEID, "
			+ "CASE hasAnyCoSponser WHEN '1' THEN CONCAT(FIRSTNAME,' ','&',' ',coSponserName ) ELSE CONCAT(FIRSTNAME,' ',COALESCE(MIDDLEINITIAL, ''),' ',LASTNAME ) END sponsorName, "
			+ "NICKNAME,  P.NAME parishName,P.CITY parishCity,C.NAME centerName, R.NAME regionName, "
			+ "APPARTMENTNUMBER,STREET, SP.CITY sponsorCity, STATE sponsorState,POSTALCODE, SP.EMAILADDRESS emailAddress, "
			+ "emailAddress2, phone1, phone2, DATE_FORMAT(effectiveDate, \"%M 1 %Y\") effectiveDate,  "
			+ "(CONTRIBUTIONAMOUNT + MISCAMOUNT) CONTRIBUTION, EN.CREATEDDATE FROM SPONSOR SP, PARISH P, ENROLLMENT EN , CENTER C, "
			+ "REGION R, SPONSOR_MAXOUT SM  WHERE P.ID = SP.PARISHID AND EN.ID=SM.ENROLLMENTID AND EN.SPONSORID = SP.ID  AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND EN.ID = #{id}")
	SponsorReport findSponsorByEnrolmentId(@Param("id") Long id);
}
