package com.loctek.workflow;

import cn.hutool.core.io.IoUtil;
import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.http.HttpRequest;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

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

    @Test
    void floor(){
        System.out.println(new BigDecimal("1.00000000").scale());
    }

    @Test
    void nativePatch() throws IOException {
        String address = "https://www.baidu.com/";
        allowMethods("PATCH");
        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Authentication","Bearer .....");
        connection.connect();
        String s = IoUtil.read(connection.getInputStream()).toString(StandardCharsets.UTF_8);
        System.out.println(s);
    }

    private void allowMethods(String... methods) {
        try {
            Field methodsField = HttpURLConnection.class.getDeclaredField("methods");

            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

            methodsField.setAccessible(true);

            String[] oldMethods = (String[]) methodsField.get(null);
            Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
            methodsSet.addAll(Arrays.asList(methods));
            String[] newMethods = methodsSet.toArray(new String[0]);

            methodsField.set(null/*static field*/, newMethods);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
