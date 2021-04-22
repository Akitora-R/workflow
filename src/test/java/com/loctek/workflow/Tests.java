package com.loctek.workflow;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Tests {
    @Test
    void test1(){
        System.out.println(new BCryptPasswordEncoder().encode("root"));
    }
}
