package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.sposnor.intellisense.sponsorintellisense.data.model.FileUpload;
import com.sposnor.intellisense.sponsorintellisense.data.model.UploadDocument;

@Mapper
public interface UploadMapper {

	@Insert("insert into file_upload (agencyId,projectId,userId, fileName,fileData) "
			+ " VALUES (#{agencyId},#{projectId}, #{userId}, #{fileName}, #{fileData})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
	before = false, resultType= Long.class)
	void uploadFile(FileUpload fileUpload);
	
	
	@Select("SELECT FU.ID ID, A.NAME AGENCYNAME, P.NAME PROJECTNAME, CONCAT(U.FIRSTNAME,' ',U.LASTNAME) UPLOADEDBY, "
			+ "FU.FILENAME FILENAME, BATCHEXECUTIONSTATUS, DATE_FORMAT(FU.CREATEDDATE, '%M %D %Y') CREATEDDATE "
			+ "FROM FILE_UPLOAD FU, AGENCY A, PROJECT P, USERS U  WHERE TYPE =#{type}  AND FU.AGENCYID = A.ID "
			+ "AND FU.PROJECTID = P.ID AND FU.USERID = U.ID AND FU.STATUS = 'U' ORDER BY FU.CREATEDDATE DESC ")
	List<UploadDocument> list(@Param("type") String type);
	
}
