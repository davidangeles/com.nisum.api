package com.nisum.api.controller;

import com.nisum.api.properties.TokenJwtProperties;
import com.nisum.api.service.TokenService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class TokenControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TokenService tokenService;

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testGetTokenRest() throws Exception {
        mockMvc.perform(get("/token")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void testGetTokenService() {
        String token = tokenService.generateTokenBySecurityType("test@test.cl");
        assertThat(token).isNotEmpty().isNotBlank();

        boolean isValid = tokenService.isTokenValid(TokenJwtProperties.TokenType.SECURITY, token);
        assertThat(isValid).isTrue();

        isValid = tokenService.isTokenValid(TokenJwtProperties.TokenType.SECURITY, "@" + token );
        assertThat(isValid).isFalse();
    }

}
