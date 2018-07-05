package com.example.rest_security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import com.example.rest_security.exception.MyAccessDeniedHandler;
import com.example.rest_security.service.CustomUserService;
import com.example.rest_security.service.MyFilterSecurityInterceptor;
import com.example.rest_security.service.MyPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserService customUserService;
	
	@Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;
	
	@Autowired
	private MyAccessDeniedHandler accessDeniedHandler;
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http
    			.authorizeRequests()
    				.antMatchers("/", "/home", "/about").permitAll()
    				.anyRequest().authenticated()
                .and()
                .formLogin()
                	.loginPage("/login")
                	.permitAll()
                	.successForwardUrl("/home").and()
                .logout()
                	.permitAll().invalidateHttpSession(true).and()
                	.exceptionHandling().accessDeniedHandler(this.accessDeniedHandler);
    	
    	http.addFilterBefore(this.myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder builder) throws Exception {
        builder.userDetailsService(this.customUserService).passwordEncoder(new MyPasswordEncoder());
    }
}