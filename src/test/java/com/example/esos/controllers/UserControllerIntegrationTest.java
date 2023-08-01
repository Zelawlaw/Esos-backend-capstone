package com.example.esos.controllers;

import com.example.esos.dto.UserResponse;
import com.example.esos.entities.User;
import com.example.esos.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserRepository userRepository;

    @Test
    @WithMockUser(username = "testuser", roles = {"ADMIN"})
    public void shouldFetchUsers() throws Exception {
        User user1 = new User("lolo1", "asdlkfjlkasjdf", passwordEncoder);
        user1.setId(1);
        user1.setDirectReports(new ArrayList<>());
        User user2 = new User("lolo2", "asdflasjfl", passwordEncoder);
        user2.setId(2);
        user1.setDirectReports(new ArrayList<>());
        UserResponse userResponse1 = new UserResponse(1, "lolo1");
        UserResponse userResponse2 = new UserResponse(2, "lolo2");

        ObjectMapper mapper = new ObjectMapper();
        String expectedJson = mapper.writeValueAsString(Arrays.asList(userResponse1, userResponse2));
        //when
        List<User> userlist = new ArrayList<>();
        userlist.add(user1);
        userlist.add(user2);
        doReturn(userlist).when(userRepository).findAll();

        //do
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/getusers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
