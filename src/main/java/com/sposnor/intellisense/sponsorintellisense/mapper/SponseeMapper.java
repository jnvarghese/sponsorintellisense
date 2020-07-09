package com.sposnor.intellisense.sponsorintellisense.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;

@Mapper
public interface SponseeMapper {

	@Select({"<script>",
        "SELECT * FROM SPONSEE SP WHERE SP.studentId IN ", 
          "<foreach item='id' index='index' collection='studentIds'",
            "open='(' separator=',' close=')'>",
            "#{id}",
          "</foreach>",
        "</script>"})
	List<Sponsee> listSponseeByStudentIds(@Param("studentIds") Long[] studentIds);
}
