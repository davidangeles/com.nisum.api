package com.nisum.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.nisum.api.dto.request.UserRequest;
import com.nisum.api.exception.NotFoundException;
import com.nisum.api.exception.UnprocessableEntity;
import com.nisum.api.service.UserService;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("classpath:application-test.properties")
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    static byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        return mapper.writeValueAsBytes(object);
    }

    @BeforeEach
    void setUp() {
    }

    @Test
    public void testSaveUser() throws Exception {

        final UserRequest userRequestDuplicated = this.convertToObject("/user-request-duplicated.json", UserRequest.class);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userRequestDuplicated)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnprocessableEntity());

        final UserRequest userRequest = this.convertToObject("/user-request.json", UserRequest.class);

        mockMvc.perform(post("/api/v1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(userRequest)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phones[0].number").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phones[1].number").isNotEmpty());
    }

    @Test(expected = UnprocessableEntity.class)
    public void testSaveUserException() throws Exception {
        final UserRequest userRequestDuplicated = this.convertToObject("/user-request-duplicated.json", UserRequest.class);
        Mockito.when(userService.saveUser(userRequestDuplicated)).thenThrow(UnprocessableEntity.class);
    }

    @Test
    public void testFindUserById() throws Exception {
        String uuidExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd661";

        mockMvc.perform(get("/api/v1/" + uuidExist)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phones[0].number").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.phones[1].number").isNotEmpty());

        String uuidNotExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd666";

        mockMvc.perform(get("/api/v1/" + uuidNotExist)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test(expected = NotFoundException.class)
    public void testFindUserByIdException() throws Exception {
        String uuidNotExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd666";
        Mockito.when(userService.findUserById(uuidNotExist)).thenThrow(NotFoundException.class);
    }

    @Test
    public void testDeleteUser() throws Exception {
        String uuidExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd661";

        mockMvc.perform(delete("/api/v1/" + uuidExist)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNoContent());

        String uuidNotExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd666";

        mockMvc.perform(delete("/api/v1/" + uuidNotExist)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isNotFound());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteUserException() {
        String uuidNotExist = "5fa78ebf-01a3-4e68-b8ad-eea4630cd666";
        userService.deleteUser(uuidNotExist);
    }

    protected <T> T convertToObject(String pathJson, Class<T> _class) throws Exception {
        String jsonRequest = getJsonFromPath(pathJson);
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        T t = mapper.readValue(jsonRequest, _class);
        return t;
    }

    protected String getJsonFromPath(String pathJson) throws Exception {
        String jsonRequest = new String(Files.readAllBytes(Paths.get(getClass().getResource(pathJson).toURI())));
        return jsonRequest;
    }
}
