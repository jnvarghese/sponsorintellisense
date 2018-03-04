package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;

@Mapper
public interface ParishMapper {
	
	@Select("select * from parish where id= #{id}")
	Parish findById(@Param("id") Long id);
	
	@Select("SELECT P.ID, P.CODE, P.NAME, P.CITY, P.STATUS, P.CENTERID, R.NAME regionName, C.NAME centerName FROM PARISH P, "
			+ "REGION R, CENTER C WHERE P.CENTERID = C.ID "
			+ "AND C.REGIONID = R. ID "
			+ "AND P.CENTERID= #{centerId} "
			+ "AND P.STATUS = 1")
	List<Parish> listAllParishByCenterId(@Param("centerId") Long centerId);
	
	@Select("SELECT P.ID, P.CODE, P.NAME, P.CITY, P.CENTERID, P.STATUS, R.NAME REGIONNAME, C.NAME CENTERNAME FROM PARISH P, "
			+ "REGION R, CENTER C WHERE P.CENTERID = C.ID AND C.REGIONID = R.ID AND P.STATUS= 1 ORDER BY P.NAME, P.CITY;")
	List<Parish> list();
	
	@Insert("insert into parish (code, name, status, city, centerId) values (#{code}, #{name}, #{status}, #{city}, #{centerId})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Parish parish) throws java.sql.SQLIntegrityConstraintViolationException;
	
	@Update("update parish set code= #{code}, name= #{name}, status= #{status}, city = #{city}, centerId = #{centerId} where id = #{id}")	
	void update(Parish parish);
}
