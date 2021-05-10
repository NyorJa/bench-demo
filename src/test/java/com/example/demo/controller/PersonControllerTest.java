package com.example.demo.controller;

import com.example.demo.DemoApplication;
import com.example.demo.model.Person;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = DemoApplication.class)
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private static final String ROOT_PATH = "/api/v1/person";


    @Test
    void testFindByName() throws Exception {
        mockMvc.perform(get(ROOT_PATH + "/{name}", "rod"))
               .andDo(print())
               .andExpect(status().isOk());
    }

    @Test
    void testFindByNameNotFound() throws Exception {
        mockMvc.perform(get(ROOT_PATH + "/{name}", "nyor"))
               .andDo(print())
               .andExpect(status().isNotFound());
    }

    @Test
    void testCreatePerson() throws Exception {


        Person person = Person.builder()
                              .address("add")
                              .name("rods")
                              .birthDate(LocalDate.of(1989,01,01))
                              .createdDate(LocalDate.now())
                              .build();

        mockMvc.perform(post(ROOT_PATH + "/").contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(mapper.writeValueAsString(person)))
               .andDo(print())
               .andExpect(status().isCreated());
    }

    @Test
    void testCreatePersonWithExistingEmail() throws Exception {


        Person person = Person.builder()
                              .address("add")
                              .name("rods")
                              .email("a@a.com")
                              .birthDate(LocalDate.of(1989,01,01))
                              .createdDate(LocalDate.now())
                              .build();

        mockMvc.perform(post(ROOT_PATH+ "/").contentType(MediaType.APPLICATION_JSON_VALUE)
                                        .content(mapper.writeValueAsString(person)))
               .andDo(print())
               .andExpect(status().isBadRequest());
    }

}


