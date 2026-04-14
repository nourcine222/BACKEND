package com.greenbuilding.trainingbackend.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StatisticsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void overviewIsAvailableToAdmin() throws Exception {
        mockMvc.perform(get("/api/statistics/overview")
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin", "admin123")))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalUsers").value(1))
                .andExpect(jsonPath("$.totalRoles").value(3))
                .andExpect(jsonPath("$.totalDomaines").value(3))
                .andExpect(jsonPath("$.totalProfils").value(3))
                .andExpect(jsonPath("$.totalStructures").value(2))
                .andExpect(jsonPath("$.totalEmployeurs").value(1));
    }
}
