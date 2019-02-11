package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Organization;

@Mapper
public interface OrganizationMapper {

	@Select("select * from organization where id= #{id}")
	Organization findById(@Param("id") Long id);
	
	
	@Select("select * from organization where status = 0")
	List<Organization> list();
	
	@Insert("insert into organization (code, name, status, createdBy, createdDate) values (#{code}, #{name}, #{status}, #{createdBy}, #{createdDate})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Organization organization) throws java.sql.SQLIntegrityConstraintViolationException;
	
	@Update("update organization set code= #{code}, name= #{name}, status= #{status}, updatedBy= #{updatedBy} where id = #{id}")	
	void update(Organization sponsor);
}
