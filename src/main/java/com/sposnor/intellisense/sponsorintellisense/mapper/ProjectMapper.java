package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Project;

@Mapper
public interface ProjectMapper {

	@Select("select * from project where id= #{id}")
	Project findById(@Param("id") Long id);
	
	@Select("SELECT P.ID, P.CODE, P.AGENCYID, P.NAME, P.ADDRESS, P.CONTACTNUMBER, P.CONTACTEMAIL,P.STATUS, "
			+ "A.CODE agencyCode, A.NAME agencyName, false as selected FROM PROJECT P , "
			+ "AGENCY A WHERE  P.AGENCYID = A.ID AND P.STATUS = 1")
	List<Project> list();
	
	@Insert("insert into project (code, agencyId, name, address, contactNumber, contactEmail, status) "
			+ "values (#{code}, #{agencyId}, #{name}, #{address}, #{contactNumber}, #{contactEmail}, #{status})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Project p);
	
	@Update("update project set code= #{code}, agencyId= #{agencyId}, name= #{name}, address=#{address}, contactNumber=#{contactNumber},"
			+ " contactEmail= #{contactEmail}, status= #{status} where id = #{id}")	
	void update(Project p);
}
