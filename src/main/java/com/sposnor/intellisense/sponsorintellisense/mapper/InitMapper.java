package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Center;
import com.sposnor.intellisense.sponsorintellisense.data.model.Initiative;
import com.sposnor.intellisense.sponsorintellisense.data.model.Region;

@Mapper
public interface InitMapper {

	@Select("SELECT * FROM REGION WHERE STATUS= 1")
	List<Region> getRegions();
	
	@Select("SELECT C.ID id, R.NAME regionName, C.NAME name, C.REGIONID regionId FROM CENTER C, REGION R "
			+ "WHERE C.STATUS = 1 AND C.REGIONID = R.ID AND R.ID")
	List<Center> getCenters();
	
	@Select("SELECT C.ID, C.CODE, C.NAME, C.REGIONID FROM CENTER C, REGION R WHERE C.STATUS = 1 AND C.REGIONID = R.ID AND R.ID = #{regionId}")
	List<Center> getCenterByRegionId(@Param("regionId")  Long regionId);
	
	@Select("SELECT CASE WHEN CHILD.NAME IS NULL THEN PARENT.NAME ELSE CONCAT(PARENT.NAME, ' - ',CHILD.NAME) END initiativeName, "
			+ "CASE WHEN CHILD.ID IS NULL THEN PARENT.ID ELSE CHILD.ID END AS INITIATIVEID FROM INITIATIVE AS PARENT "
			+ "LEFT JOIN INITIATIVE AS CHILD ON CHILD.PARENTID = PARENT.ID WHERE PARENT.PARENTID= 0  ORDER BY initiativeName")
	List<Initiative> getInitiatives();
	
	@Select("SELECT id, name, code, parentId from initiative where id= #{id}")
	Initiative getInitiativeById(@Param("id")  Long id);
	
	@Select("SELECT id, name, code, parentId from initiative where parentId= #{parentId}")
	List<Initiative> getInitiativeByParentId(@Param("parentId")  Long id);
	
	@Select("SELECT * FROM initiative WHERE  active='Y' ORDER BY name;")
	List<Initiative> getActiveInitiatives();
	
}
