package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sposnor.intellisense.sponsorintellisense.data.model.Student;

@Mapper
public interface StudentMapper {

	@Select("SELECT S.ID, S.PROJECTID, LASTNAME, FIRSTNAME, MIDDLENAME, GENDER, DATEOFBIRTH, S.ADDRESS, HOBBIES, S.STATUS,TALENT, "
			+ "RECENTACHIVEMENTS, VALIDUNTIL, PROFILEPICTURE, SOFTLOCKED, P.NAME PROJECTNAME, A.NAME AGENCYNAME FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID "
			+ "LEFT JOIN AGENCY A ON P.AGENCYID = A.ID "
			+ "WHERE S.ID = #{id}")
	Student findById(@Param("id") Long id);
	
	@Select("SELECT S.ID, LASTNAME, FIRSTNAME, MIDDLENAME, GENDER, VALIDUNTIL, P.NAME projectName, A.NAME agencyName FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE S.STATUS = 0 ")
	List<Student> list();
	
	@Insert("INSERT INTO STUDENT (PROJECTID, FIRSTNAME, LASTNAME, MIDDLENAME, GENDER, DATEOFBIRTH, ADDRESS, STATUS, HOBBIES, TALENT, "
			+ "RECENTACHIVEMENTS, PROFILEPICTURE, SOFTLOCKED) "
			+ "VALUES (#{projectId}, #{firstName}, #{lastName}, #{middleName}, #{gender}, #{dateOfBirth}, #{address}, #{status},"
			+ "#{hobbies}, #{talent}, #{recentAchivements}, #{profilePicture}, #{softlocked})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Student student);
	
	@Insert("UPDATE STUDENT SET projectId= #{projectId} , firstName= #{firstName}, lastName= #{lastName}, middleName= #{middleName}, "
			+ "gender= #{gender}, dateOfBirth= #{dateOfBirth}, address= #{address},"
			+ " status= #{status}, hobbies= #{hobbies}, talent= #{talent}, recentAchivements= #{recentAchivements}, profilePicture= #{profilePicture},"
			+ " softlocked= #{softlocked} , validUntil = #{validUntil} WHERE id=#{id}")	
	void update(Student student);
	
	@Select("SELECT ID, FIRSTNAME, LASTNAME, MIDDLENAME FROM STUDENT WHERE STATUS = 0 AND FIRSTNAME LIKE #{name} ")
	List<Student> searchByName(@Param("name") String name);
	
	/*@Select("SELECT S.ID, SOFTLOCKED, FIRSTNAME, LASTNAME, MIDDLENAME, SE.EXPIRATIONMONTH, SE.EXPIRATIONYEAR FROM STUDENT S "
			+ "LEFT JOIN SPONSEE SE ON S.ID = SE.STUDENTID "
			+ "WHERE S.STATUS = 0 AND FIRSTNAME LIKE #{name}")*/
	
	@Select("SELECT S.ID, SOFTLOCKED, FIRSTNAME, LASTNAME, MIDDLENAME, VALIDUNTIL FROM STUDENT S WHERE S.STATUS = 0 "
			+ "AND FIRSTNAME LIKE #{name} AND (VALIDUNTIL IS NULL OR VALIDUNTIL < #{effectiveDate}) ORDER BY VALIDUNTIL")
	List<Student> findStudentsBySponsorshipStatus(@Param("name") String name,@Param("effectiveDate") String effectiveDate	);
}
