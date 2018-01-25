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
	
	@Insert("insert into agency (code, name, status) values (#{code}, #{name}, #{status})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Agency agency);
	
	@Update("update agency set code= #{code}, name= #{name}, status= #{status} where id = #{id}")	
	void update(Agency sponsor);
}
