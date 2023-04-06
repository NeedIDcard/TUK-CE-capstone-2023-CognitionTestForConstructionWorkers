package com.tukorea.cogTest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestConfiguration.class)
@AutoConfigureMockMvc

class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;



    @Test
    @WithMockUser(roles = "ADMIN")
    void getSubject() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.
                        post("/site")
                        .requestAttr("name", "field1")
                        .requestAttr("numOfWorkers", 0)
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .with(csrf())

        ).andDo(print());
        mockMvc.perform(
                MockMvcRequestBuilders
                        .post("/admin/subject?mode=sole")
                        .requestAttr("name", "subject1")
                        .requestAttr("username", "username1")
                        .requestAttr("role", 0)
                        .requestAttr("age", 24)
                        .requestAttr("detailedJob", 0)
                        .requestAttr("career", 10)
                        .requestAttr("risk", 0)
                        .with(csrf())
        ).andDo(print());
        mockMvc.perform(
                MockMvcRequestBuilders.
                        get("/admin/subject/1")
                        .accept(MediaType.APPLICATION_JSON)
                        .with(csrf())
        ).andDo(print());
    }

    @Test
    void addWorker() {
    }

    @Test
    void updateWorker() {
    }

    @Test
    void deleteWorker() {
    }
}