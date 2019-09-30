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
	
	@Select("SELECT P.ID, P.CODE, P.NAME, P.CITY, P.CENTERID, P.STATUS, R.NAME REGIONNAME, C.NAME CENTERNAME FROM PARISH P, "
			+ "REGION R, CENTER C WHERE P.CENTERID = C.ID AND C.REGIONID = R.ID AND P.STATUS= 1 AND P.NAME LIKE  CONCAT(#{terms}, '%') ORDER BY P.NAME, P.CITY;")
	List<Parish> search(@Param("terms") String terms);
	
	@Insert("insert into parish (code, name, status, city, centerId, createdBy, createdDate) values (#{code}, #{name}, #{status}, #{city}, #{centerId} , #{createdBy}, #{createdDate})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
			before = false, resultType= Long.class)
	void insert(Parish parish) throws java.sql.SQLIntegrityConstraintViolationException;
	
	@Update("update parish set code= #{code}, name= #{name}, status= #{status}, city = #{city}, centerId = #{centerId}, updatedBy= #{updatedBy} where id = #{id}")	
	void update(Parish parish);
}
