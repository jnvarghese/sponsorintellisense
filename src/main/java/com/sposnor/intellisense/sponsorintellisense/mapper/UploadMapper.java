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
	
	@Select("SELECT S.ID, STUDENTNAME, GENDER, P.NAME projectName, A.NAME agencyName, studentCode, imageLinkRef FROM STUDENT S "
			+ "LEFT JOIN PROJECT P ON S.PROJECTID = P.ID LEFT JOIN AGENCY A ON P.AGENCYID = A.ID WHERE "
			+ " projectid IN ( #{id} ) AND S.STATUS = 0 ")
	List<UploadDocument> list(@Param("type") String type);
	
}
