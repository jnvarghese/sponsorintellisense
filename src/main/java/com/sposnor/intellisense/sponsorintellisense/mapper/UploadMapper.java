package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.SelectKey;

import com.sposnor.intellisense.sponsorintellisense.data.model.FileUpload;

@Mapper
public interface UploadMapper {

	@Insert("insert into file_upload (agencyId,projectId,userId, fileName,fileData) "
			+ " VALUES (#{agencyId},#{projectId}, #{userId}, #{fileName}, #{fileData})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
	before = false, resultType= Long.class)
	void uploadFile(FileUpload fileUpload);
	
}
