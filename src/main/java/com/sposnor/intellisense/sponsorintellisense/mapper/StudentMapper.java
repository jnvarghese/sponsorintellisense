package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;

@Mapper
public interface StudentMapper {

	@Select("SELECT S.ID, S.PROJECTID, LASTNAME, FIRSTNAME, MIDDLENAME, GENDER, DATEOFBIRTH, S.ADDRESS, HOBBIES, S.STATUS,TALENT, "
			+ "RECENTACHIVEMENTS, PROFILEPICTURE, SOFTLOCKED, P.NAME PROJECTNAME, A.NAME AGENCYNAME FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID "
			+ "LEFT JOIN AGENCY A ON P.AGENCYID = A.ID "
			+ "WHERE S.ID = #{id}")
	Student findById(@Param("id") Long id);
	
	@Select("SELECT S.ID, LASTNAME, FIRSTNAME, MIDDLENAME, GENDER, P.NAME projectName, A.NAME agencyName FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE S.STATUS = 0 ")
	List<Student> list();
	
	@Insert("INSERT INTO STUDENT (PROJECTID, FIRSTNAME, LASTNAME, MIDDLENAME, GENDER, DATEOFBIRTH, ADDRESS, HOBBIES, TALENT, "
			+ "RECENTACHIVEMENTS, PROFILEPICTURE, SOFTLOCKED) "
			+ "VALUES (#{projectId}, #{firstName}, #{lastName}, #{middleName}, #{gender}, #{dateOfBirth}, #{address},"
			+ "#{hobbies}, #{talent}, #{recentAchivements}, #{profilePicture}, #{softlocked})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Student student);
	
	@Insert("UPDATE STUDENT SET projectId= #{projectId} , firstName= #{firstName}, lastName= #{lastName}, middleName= #{middleName}, "
			+ "gender= #{gender}, dateOfBirth= #{dateOfBirth}, address= #{address},"
			+ " status= #{status}, hobbies= #{hobbies}, talent= #{talent}, recentAchivements= #{recentAchivements}, profilePicture= #{profilePicture},"
			+ " softlocked= #{softlocked} WHERE id=#{id}")	
	void update(Student student);
	
	@Select("SELECT ID, FIRSTNAME, LASTNAME, MIDDLENAME FROM STUDENT WHERE STATUS = 0 AND FIRSTNAME LIKE #{name} ")
	List<Student> searchByName(@Param("name") String name);
	
	/*@Select("SELECT S.ID, SOFTLOCKED, FIRSTNAME, LASTNAME, MIDDLENAME, SE.EXPIRATIONMONTH, SE.EXPIRATIONYEAR FROM STUDENT S "
			+ "LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID "
			+ "WHERE S.STATUS = 0 AND FIRSTNAME LIKE #{name}")*/
	
	/*@Select("SELECT * FROM STUDENT S, PROJECT PRJ, PARISH_PROJECT PP WHERE S.PROJECTID = PRJ.ID  AND PRJ.ID = PP.PROJECTID AND PP.PARISHID=#{parishId}"
			+ " AND S.STATUS = 0 AND FIRSTNAME LIKE #{name}"
			+ " AND (VALIDUNTIL IS NULL OR VALIDUNTIL < #{effectiveDate})"
			+ " ORDER BY VALIDUNTIL")*/
	
	@Select("SELECT S.ID, S.FIRSTNAME, S.LASTNAME, S.MIDDLENAME, MAX(MAXOUT) MAXOUT FROM STUDENT S LEFT JOIN STUDENT_MAXOUT MX "
			+ "ON S.ID = MX.STUDENTID, PROJECT PRJ, PARISH_PROJECT PP "
			+ "WHERE S.PROJECTID = PRJ.ID "
			+ "AND PRJ.ID = PP.PROJECTID "
			+ "AND PP.PARISHID IN(SELECT PARISHID FROM PARISH_PROJECT WHERE PROJECTID = #{projectId}) "
			+ "AND (MAXOUT IS NULL OR DATE(MAXOUT) < #{effectiveDate}) "
			+ "AND S.STATUS = 0 "
			+ "GROUP BY S.ID, S.FIRSTNAME, S.LASTNAME, S.MIDDLENAME, MX.STUDENTID")
	List<Student> findStudentsBySponsorshipStatus(
			@Param("name") String name,
			@Param("projectId") Long projectId,
			@Param("effectiveDate") String effectiveDate
			);
	
	@Select("SELECT S.ID, FIRSTNAME, LASTNAME, MIDDLENAME FROM STUDENT S "
			+ "LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID "
			+ "WHERE S.STATUS = 0 AND FIRSTNAME LIKE #{name}  GROUP BY S.ID, FIRSTNAME, LASTNAME, MIDDLENAME")
	List<Student> listMatchingStudentsByName(@Param("name") String name);
	
	@Select("SELECT EN.ID, CONCAT(R.CODE,'-',C.CODE,'-',P.CODE,'-',SP.ID) UNIQUEID, "
			+ "CONCAT(FIRSTNAME,' ',MIDDLEINITIAL,' ',LASTNAME ) FULLNAME, NICKNAME,  P.NAME parishName,P.CITY parishCity,C.NAME centerName, R.NAME regionName, "
			+ "APPARTMENTNUMBER,STREET, SP.CITY sponsorCity, STATE sponsorState,POSTALCODE, SP.EMAILADDRESS,  DATE_FORMAT(PAYMENTDATE, \"%M %D %Y\") PAYMENTDATE,  "
			+ "(CONTRIBUTIONAMOUNT + MISCAMOUNT) CONTRIBUTION, EN.CREATEDDATE FROM SPONSOR SP, PARISH P, ENROLLMENT EN , CENTER C, "
			+ "REGION R WHERE P.ID = SP.PARISHID  AND SPONSORID = SP.ID  AND P.CENTERID = C.ID AND C.REGIONID = R.ID "
			+ "AND EN.ID = = #{id}")
	SponsorReport findSponsorByEnrolmentId(@Param("id") Long id);
}
