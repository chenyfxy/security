package com.example.rest_security.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.rest_security.dao.UserDao;
import com.example.rest_security.entity.SysRole;
import com.example.rest_security.entity.SysUser;

@Service
public class CustomUserService implements UserDetailsService { //自定义UserDetailsService 接口

    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) { //重写loadUserByUsername 方法获得 userdetails 类型用户

        SysUser user = this.userDao.findByUserName(username);
        
        if (user == null){
            throw new UsernameNotFoundException("userName"+ username+"not found");
        }
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        List<SysRole> userRoles = user.getRoles();
        
        if (userRoles != null) {
            for (SysRole role : userRoles) {
                SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.getName());
                authorities.add(authority);
            }
        }
        User securityUser = new User(user.getUsername(), user.getPassword(), authorities);
        return securityUser;
    }
}