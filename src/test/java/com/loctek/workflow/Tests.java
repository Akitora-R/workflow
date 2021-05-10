package com.loctek.workflow;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.nio.charset.StandardCharsets;

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

    @Test
    void url(){
        UrlBuilder urlBuilder = UrlBuilder.ofHttp("localhost:10081", StandardCharsets.UTF_8)
                .appendPath("/leave")
                .appendPath("proc");
        System.out.println(urlBuilder.build());
    }
}
