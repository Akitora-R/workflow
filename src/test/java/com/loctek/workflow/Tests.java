package com.loctek.workflow;

import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class Tests {
    @Test
    void test1(){
        System.out.println(new BCryptPasswordEncoder().encode("root"));
    }

    @Test
    void get(){
        String body = HttpRequest.get("http://localhost:10081/leave/proc/ins/all").execute().body();
        System.out.println(body);
    }
}
