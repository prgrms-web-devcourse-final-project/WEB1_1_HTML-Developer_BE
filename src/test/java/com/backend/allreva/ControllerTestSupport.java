package com.backend.allreva;

import com.backend.allreva.auth.application.CustomUserDetailsService;
import com.backend.allreva.auth.util.JwtProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@AutoConfigureMockMvc
public abstract class ControllerTestSupport {
    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected ObjectMapper objectMapper;
    @MockBean
    protected JwtProvider jwtProvider;
    @MockBean
    protected CustomUserDetailsService customUserDetailsService;

    protected String token;

    @BeforeEach
    public void setUp() {
        token = jwtProvider.generateAccessToken("1");
    }
}
