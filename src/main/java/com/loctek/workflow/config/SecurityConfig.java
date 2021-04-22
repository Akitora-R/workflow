package com.loctek.workflow.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> new User(username,"pw", AuthorityUtils.NO_AUTHORITIES);
    }
}
