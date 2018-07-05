package com.example.rest_security.dao;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.FetchType;
import org.apache.ibatis.annotations.Many;

import com.example.rest_security.entity.SysUser;

public interface UserDao {
	@Select("select u.* ,r.name from sys_user u LEFT JOIN sys_role_user sru on u.id= sru.sys_user_id LEFT JOIN sys_role r on sru.sys_role_id=r.id where username= #{username}")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="username",property="username"),
		@Result(column="password",property="password"),
 		@Result(column="id",property="roles",many=@Many(select="com.example.rest_security.dao.RoleDao.selectByUserId",fetchType=FetchType.LAZY)),
	})
    public SysUser findByUserName(String username);
	
	@Select("select u.* ,r.name from sys_user u LEFT JOIN sys_role_user sru on u.id= sru.sys_user_id LEFT JOIN sys_role r on sru.sys_role_id=r.id where u.id= #{userId}")
	@Results({
		@Result(id=true,column="id",property="id"),
		@Result(column="username",property="username"),
		@Result(column="password",property="password"),
 		@Result(column="id",property="roles",many=@Many(select="com.example.rest_security.dao.RoleDao.selectByUserId",fetchType=FetchType.LAZY)),
	})
    public SysUser findByUserId(Integer userId);
}