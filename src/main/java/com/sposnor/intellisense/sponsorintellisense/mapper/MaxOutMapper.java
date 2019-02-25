package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;

@Mapper
public interface MaxOutMapper {

	@Insert("insert into sponsor_maxout (sponsorId, enrollmentId, maxOut) values (#{sponsorId}, #{enrollmentId}, #{maxOut})")
	void insertSponsorMaxOut(SponsorMaxOut e);
	
	@Insert("insert into student_maxout (studentId, enrollmentId, maxOut) values (#{studentId}, #{enrollmentId}, #{maxOut})")
	void insertStudentMaxOut(StudentMaxOut e);
	
	@Select({"<script>",
        "SELECT * FROM student_maxout WHERE enrollmentId = #{enrollmentId} AND STATUS =0 AND studentId IN", 
          "<foreach item='item' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{item}",
          "</foreach>",
        "</script>"}) 
	List<StudentMaxOut> findStudentMaxOut(@Param("list") List<Long> studentIds, @Param("enrollmentId") Long enrollmentId);
	
	
	/*** Update Sponsor Max Out **/
	@Update({"<script>",
        "UPDATE sponsor_maxout SET STATUS = 1 WHERE id IN", 
          "<foreach item='item' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{item}",
          "</foreach>",
        "</script>"})
	void updateSponsorMaxOutStatus(@Param("list") List<Integer> sponsnorMaxOutIds);
	
	@Select("SELECT * FROM sponsor_maxout WHERE sponsorId = #{sponsorId} AND enrollmentId = #{enrollmentId} AND STATUS =0 ")
	List<SponsorMaxOut> findSponsorMaxOut(@Param("sponsorId") Long sponsorId, @Param("enrollmentId") Long enrollmentId);
	
	/*** Update Student Max Out **/
	@Update({"<script>",
        "UPDATE student_maxout SET STATUS = 1 WHERE ID IN", 
          "<foreach item='item' index='index' collection='list'",
            "open='(' separator=',' close=')'>",
            "#{item}",
          "</foreach>",
        "</script>"})
	void updateStudentMaxOutStatus(@Param("list") List<Integer> studentIds);
	
}
