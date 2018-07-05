package com.example.rest_security.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.rest_security.dao.PermissionDao;
import com.example.rest_security.dao.UserDao;
import com.example.rest_security.entity.Permission;
import com.example.rest_security.entity.SysUser;

@Service
public class CustomUserService implements UserDetailsService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public UserDetails loadUserByUsername(String username) {
        SysUser user = this.userDao.findByUserName(username);
        
        if (user == null){
            throw new UsernameNotFoundException("userName"+ username+"not found");
        }
        List<Permission> permissions = this.permissionDao.findByAdminUserId(user.getId());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
        
        for (Permission permission : permissions) {
        	if (permission != null && !StringUtils.isEmpty(permission.getName())) {
        		GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(permission.getName());
        		grantedAuthorities.add(grantedAuthority);
        	}
        }
        
        User securityUser = new User(user.getUsername(), user.getPassword(), grantedAuthorities);
        return securityUser;
    }
}