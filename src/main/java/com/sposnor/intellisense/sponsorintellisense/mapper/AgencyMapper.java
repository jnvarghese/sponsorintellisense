package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Agency;

@Mapper
public interface AgencyMapper {

	@Select("select * from agency where id= #{id}")
	Agency findById(@Param("id") Long id);
	
	@Select("select * from agency where status = 1")
	List<Agency> list();
	
	@Insert("insert into agency (code, name, status, createdBy, createdDate) values (#{code}, #{name}, #{status}, #{createdBy}, #{createdDate})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Agency agency) throws java.sql.SQLIntegrityConstraintViolationException;
	
	@Update("update agency set code= #{code}, name= #{name}, status= #{status}, updatedBy= #{updatedBy} where id = #{id}")	
	void update(Agency sponsor);
}
