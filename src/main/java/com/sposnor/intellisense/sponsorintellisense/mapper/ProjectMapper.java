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
	
	@Select("SELECT P.ID, P.CODE, P.AGENCYID, P.NAME FROM PROJECT P WHERE P.STATUS = 1 and AGENCYID = #{agencyId}")
	List<Project> listByAgency(@Param("agencyId") Long agencyId);
	
	@Select("SELECT P.ID, P.CODE, P.AGENCYID, P.NAME FROM PROJECT P WHERE P.STATUS = 1 and P.NAME LIKE  CONCAT(#{terms}, '%') ORDER BY P.NAME")
	List<Project> searchProjects(@Param("terms") String term);
	
	@Select("SELECT P.ID ID ,PPID, P.CODE, CONCAT(P.NAME, '- Agency: ', A.NAME) NAME "
			+ "FROM PROJECT P, AGENCY A, PARISH_PROJECT PRJ "
			+ "WHERE P.ID = PRJ.PROJECTID "
			+ "AND P.AGENCYID= A.ID AND PRJ.PARISHID = #{parishId} AND P.STATUS= 1 ORDER BY P.NAME")
	List<Project> findProjectsByParishId(@Param("parishId") Long parishId);
	
	
	@Insert("insert into project (code, agencyId, name, address, contactNumber, contactEmail, status, createdBy, createdDate) "
			+ "values (#{code}, #{agencyId}, #{name}, #{address}, #{contactNumber}, #{contactEmail}, #{status}, #{createdBy}, #{createdDate})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Project p);
	
	@Update("update project set code= #{code}, agencyId= #{agencyId}, name= #{name}, address=#{address}, contactNumber=#{contactNumber},"
			+ " contactEmail= #{contactEmail}, status= #{status}, updatedBy= #{updatedBy} where id = #{id}")	
	void update(Project p);
}
