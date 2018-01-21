package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Agency;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Project;

@Mapper
public interface InitMapper {

	@Select("SELECT * FROM AGENCY WHERE STATUS= #{status}")
	List<Agency> getAgencyList(@Param("status") int status);
	
	@Select("SELECT * FROM PROJECT WHERE STATUS= #{status}")
	List<Project> getProjectList(@Param("status")  int status);
	
	@Select("SELECT * FROM PARISH WHERE STATUS= #{status}")
	List<Parish> getParishList(@Param("status")  int status);
}
