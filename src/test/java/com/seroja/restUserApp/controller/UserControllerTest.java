package com.seroja.restUserApp.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.seroja.restUserApp.dto.UserDto;
import com.seroja.restUserApp.mockData.UserMockData;
import com.seroja.restUserApp.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@ExtendWith(SpringExtension.class)
class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService service;
    private static final ObjectMapper mapper = new ObjectMapper();
    private final UserDto userDto1 = UserMockData.getUserDto1();
    private final UserDto userDto2 = UserMockData.getUserDto2();
    private final int userId = 1;

    @BeforeAll
    public static void init() {
        mapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        List<UserDto> userDtoList = Arrays.asList(userDto1, userDto2);

        Mockito.when(service.listAll()).thenReturn(userDtoList);

        mvc.perform(get("/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(userDto1.getId())))
                .andExpect(jsonPath("$[0].email", is(userDto1.getEmail())))
                .andExpect(jsonPath("$[0].firstName", is(userDto1.getFirstName())))
                .andExpect(jsonPath("$[0].secondName", is(userDto1.getSecondName())))
                .andExpect(jsonPath("$[0].birthDate", is(userDto1.getBirthDate().toString())))
                .andExpect(jsonPath("$[0].address", is(userDto1.getAddress())))
                .andExpect(jsonPath("$[0].phoneNumber", is(userDto1.getPhoneNumber())))
                .andExpect(jsonPath("$[1].id", is(userDto2.getId())))
                .andExpect(jsonPath("$[1].email", is(userDto2.getEmail())))
                .andExpect(jsonPath("$[1].firstName", is(userDto2.getFirstName())))
                .andExpect(jsonPath("$[1].secondName", is(userDto2.getSecondName())))
                .andExpect(jsonPath("$[1].birthDate", is(userDto2.getBirthDate().toString())))
                .andExpect(jsonPath("$[1].address", is(userDto2.getAddress())))
                .andExpect(jsonPath("$[1].phoneNumber", is(userDto2.getPhoneNumber())));
    }

    @Test
    void shouldGetUserById() throws Exception {
        Mockito.when(service.getDto(userId)).thenReturn(userDto1);

        mvc.perform(get("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(userDto1.getId())))
                .andExpect(jsonPath("$.email", is(userDto1.getEmail())))
                .andExpect(jsonPath("$.firstName", is(userDto1.getFirstName())))
                .andExpect(jsonPath("$.secondName", is(userDto1.getSecondName())))
                .andExpect(jsonPath("$.birthDate", is(userDto1.getBirthDate().toString())))
                .andExpect(jsonPath("$.address", is(userDto1.getAddress())))
                .andExpect(jsonPath("$.phoneNumber", is(userDto1.getPhoneNumber())));
    }

    @Test
    void shouldSave() throws Exception {
        Mockito.when(service.save(userDto1)).thenReturn(userDto1);

        String reqBody = mapper.writeValueAsString(userDto1);

        mvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(reqBody))
                .andExpect(MockMvcResultMatchers.status().isOk());
        Mockito.verify(service, Mockito.times(1)).save(userDto1);
    }

    @Test
    void shouldUpdateUser() throws Exception {
        String reqBody = mapper.writeValueAsString(userDto1);

        mvc.perform(put("/users/" + userId)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(reqBody)
        ).andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1))
                .update(userDto1, 1);
    }

    @Test
    void shouldUpdateSeveralUserFields() throws Exception {
        String reqBody = mapper.writeValueAsString(userDto1);
        mvc.perform(patch("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .content(reqBody))
                .andExpect(MockMvcResultMatchers.status().isOk());

        Mockito.verify(service, Mockito.times(1))
                .update(userDto1, 1);
    }

    @Test
    void shouldDeleteUser() throws Exception {
        int userId = 1;
        mvc.perform(
                MockMvcRequestBuilders.delete("/users/" + userId)
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }
}