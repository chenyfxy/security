package com.example.rest_security.dao;

import com.example.rest_security.entity.SysRole;

import java.util.List;

import org.apache.ibatis.annotations.Select;

public interface RoleDao {
	@Select("select * from sys_role where id in (select sys_role_id from sys_role_user where sys_user_id = #{user_id})")
	List<SysRole> selectByUserId(Integer user_id);
}
