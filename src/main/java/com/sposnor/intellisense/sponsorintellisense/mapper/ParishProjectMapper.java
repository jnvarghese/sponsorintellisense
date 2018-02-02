package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.ParishProject;

@Mapper
public interface ParishProjectMapper {

	@Select("SELECT ID projectId ,CODE,NAME,"
			+ "CASE WHEN PARISHID IS NULL OR PARISHID = '' THEN FALSE ELSE TRUE END AS selected "
			+ "FROM PROJECT  P LEFT JOIN (SELECT * FROM  PARISH_PROJECT PRJ WHERE PRJ.PARISHID = #{parishId} AND STATUS= 1) "
			+ "PRJ ON P.ID = PRJ.PROJECTID AND P.STATUS= 1 ORDER BY NAME")
	List<ParishProject> findByParishId(@Param("parishId") Long parishId);
	
	@Insert("insert into parish_project (parishId, projectId, status) values (#{parishId}, #{projectId}, #{selected})")
	void insertBatch(ParishProject parishProject);
	/*@Insert("{\"<script>\", "
			+ "insert into parish_project (parishId, projectId, status) values, "
			+ "<foreach collection='ppList' item='param' index='index' open='(' separator = '),(' close=')' >"
			+ "#{param.parishId},#{param.projectId},#{param.selected}"
			+ "</foreach>\",\"</script>\"}")
	void insertBatch(@Param("ppList") List<ParishProject> ppList);*/
}
