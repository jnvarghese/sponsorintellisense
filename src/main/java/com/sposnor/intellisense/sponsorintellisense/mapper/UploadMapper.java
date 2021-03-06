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

	@Insert("insert into file_upload (referenceId, userId, fileName,fileData, type, initiativeId) "
			+ " VALUES (#{referenceId}, #{userId}, #{fileName}, #{fileData}, #{type}, #{initiativeId})")
	@SelectKey(statement="SELECT LAST_INSERT_ID()", keyProperty= "id",
	before = false, resultType= Long.class)
	void uploadFile(FileUpload fileUpload);
	
	
	@Select("SELECT FU.ID ID, A.NAME AGENCYNAME, P.NAME PROJECTNAME, CONCAT(U.FIRSTNAME,' ',U.LASTNAME) UPLOADEDBY, "
			+ "FU.FILENAME FILENAME, BATCHEXECUTIONSTATUS, DATE_FORMAT(FU.CREATEDDATE, '%b %d %y') CREATEDDATE,jobid, EXIT_CODE status "
			+ "FROM FILE_UPLOAD FU LEFT JOIN batch_job_execution ON FU.JOBID = JOB_INSTANCE_ID, AGENCY A, PROJECT P, USERS U  WHERE TYPE =#{type}  AND P.AGENCYID = A.ID "
			+ "AND FU.referenceId = P.ID AND FU.USERID = U.ID AND FU.STATUS = 'U' ORDER BY FU.CREATEDDATE DESC ")
	List<UploadDocument> listStudentUploads(@Param("type") String type);
	
	@Select("SELECT FU.ID ID, C.NAME CENTERNAME, P.NAME PARISHNAME, CONCAT(U.FIRSTNAME,' ',U.LASTNAME) UPLOADEDBY, "
			+ "FU.FILENAME FILENAME, BATCHEXECUTIONSTATUS, DATE_FORMAT(FU.CREATEDDATE, '%b %d %y') CREATEDDATE,jobid, EXIT_CODE status "
			+ "FROM FILE_UPLOAD FU LEFT JOIN batch_job_execution ON FU.JOBID = JOB_INSTANCE_ID, CENTER C, PARISH P, USERS U  "
			+ "WHERE TYPE =#{type} AND P.CENTERID = C.ID "
			+ "AND FU.referenceId = P.ID AND FU.USERID = U.ID  AND FU.STATUS = 'U' ORDER BY FU.CREATEDDATE DESC ")
	List<UploadDocument> listSponsorUploads(@Param("type") String type);
	
}
