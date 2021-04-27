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
        //root
        return username -> new User(username,"$2a$10$cf2OVaxDWlbqEt6aUNNbEuTs3svZ1PxcR97vhz5eJq3CB9Vp.LVz.", AuthorityUtils.NO_AUTHORITIES);
    }
}
