package com.example.rest_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.example.rest_security.service.CustomUserService;
import com.example.rest_security.service.MyPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	CustomUserService customUserService;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    			.authorizeRequests()
    				.antMatchers("/", "/home", "/about").permitAll()
    				.anyRequest().authenticated() //任何请求,登录后可以访问
                .and()
                .formLogin()
                	.loginPage("/login")
                	.permitAll() //登录页面用户任意访问
                	.successForwardUrl("/home").and()
                .logout()
                	.permitAll().invalidateHttpSession(true); //注销行为任意访问
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.customUserService).passwordEncoder(new MyPasswordEncoder());
    }
}