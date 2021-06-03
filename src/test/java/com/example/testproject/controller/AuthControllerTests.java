package com.example.testproject.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Random;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;

import static org.hamcrest.Matchers.containsString;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Test
    void login() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Username")))
                .andExpect(content().string(containsString("Password")))
                .andExpect(content().string(containsString("sign in")));
    }

    @Test
    public void registration() throws Exception{
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("username")))
                .andExpect(content().string(containsString("password")))
                .andExpect(content().string(containsString("Sign up")));
    }

    @Test
    public void correctRegistration() throws Exception{
        this.mockMvc.perform(post("/registration")
                .param("login", "grin" + new Random().nextInt(1000))
                .param("password", "123456"))
                .andDo(print())
                .andExpect(content().string(containsString("Login")))
                .andExpect(content().string(containsString("Password")))
                .andExpect(content().string(containsString("Sign up")));
    }
}
