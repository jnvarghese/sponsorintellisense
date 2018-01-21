package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface DashboardMapper {

	@Select("SELECT COUNT(*) FROM STUDENT WHERE STATUS=0")
	Long getCountOfActiveStudent();
	
	@Select("SELECT COUNT(*) FROM SPONSOR WHERE ISACTIVE=0;")
	Long getCountOfActiveSponsor();
	
}
