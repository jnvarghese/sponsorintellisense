package com.sposnor.intellisense.sponsorintellisense.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.sposnor.intellisense.sponsorintellisense.data.model.User;

@Mapper
public interface UserMapper extends UserDetailsService {

	@Select("SELECT id, username, password, firstname, lastname, middlename, enabled from users where username=#{username}")
	public User findOne(String username);
	
	@Select("SELECT id, username, password, firstname, lastname, middlename, enabled from users where username=#{username}")
	public User loadUserByUsername(@Param("username") String username);
}
