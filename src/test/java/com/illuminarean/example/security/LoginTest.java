package com.illuminarean.example.security;


import com.illuminarean.example.user.domain.User;
import com.illuminarean.example.user.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    @DisplayName("로그인 성공 테스트")
    void loginSuccessTest() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"tester@gmail.com\",\"password\":\"1234\"}")
        );
        result.andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().exists(Jwt.HEADER_KEY));

        User user = userService.findByEmail("tester@gmail.com");

        Assertions.assertNotNull(user.getLastLoginDate());
    }

    @Test
    @DisplayName("로그인 실패 - 아이디가 올바르지 않은 경우 ")
    void loginFailureTestIllegalId() throws Exception {
        ResultActions result = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"tester1@gmail.com\",\"password\":\"1234\"}")
        );

        result.andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(header().doesNotExist(Jwt.HEADER_KEY))
                .andExpect(content().string("UsernameNotFoundException"));
    }

    @Test
    @DisplayName("로그인 실패 - 비밀번호가 올바르지 않은 경우")
    void loginFailureTestIllegalPassword() throws Exception {
        User before = userService.findByEmail("tester@gmail.com");

        ResultActions result = mockMvc.perform(
                post("/api/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{\"email\":\"tester@gmail.com\",\"password\":\"12341\"}")
        );

        result.andDo(print())
                .andExpect(status().is(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(header().doesNotExist(Jwt.HEADER_KEY))
                .andExpect(content().string("BadCredentialsException"));

    }

}
