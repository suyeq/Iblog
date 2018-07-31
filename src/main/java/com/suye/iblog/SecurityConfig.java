package com.suye.iblog;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 安全权限配置类
 */
@EnableWebSecurity
public class SecurityConfig  extends WebSecurityConfigurerAdapter{

    /**
     * 自定义配置
     * @param httpSecurity
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.authorizeRequests()
                .antMatchers("/css/**","/js/**","/fonts/**","/index").permitAll();
    }
}
