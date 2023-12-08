package com.taskmanagement.system;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.taskmanagement.system.dto.TaskDto;
import com.taskmanagement.system.dto.custom.LoginRequest;
import com.taskmanagement.system.dto.custom.LoginResponse;
import com.taskmanagement.system.entity.types.Priority;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static com.taskmanagement.system.entity.types.Status.ONGOING;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test-application.properties")
public class TaskAPITest {

    @Autowired
    private MockMvc mockMvc;
    private static String token;

    @Test
    @Order(1)
    public void obtainJWT() throws Exception {

        LoginRequest loginRequest = new LoginRequest(
                "user1@gmail.com", "123456"
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(loginRequest);

        RequestBuilder requestBuilder =
                MockMvcRequestBuilders.post("/auth/login")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON);

        String responseBody = mockMvc.perform(requestBuilder)
                .andDo(print())
                .andExpect(status().is(200))
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = objectMapper.readerFor
                (new TypeReference<LoginResponse>() {});
        LoginResponse response = reader.readValue(responseBody);
        Assertions.assertNotNull(Objects.requireNonNull(response.getToken()));

        token = Objects.requireNonNull(response.getToken());
    }


    @Test
    @Order(2)
    public void createTaskTest() throws Exception {
        TaskDto taskDto = TaskDto.builder()
                .title("Title")
                .description("some text")
                .priority(Priority.HIGH)
                .status(ONGOING)
                .authorId(1)
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String content = objectMapper.writeValueAsString(taskDto);

        String responseBody = mockMvc.perform(
                        MockMvcRequestBuilders.post("/task")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals("Successfully created!", responseBody);
    }



    @Test
    @Order(2)
    public void getTaskByIdTest() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        String responseBody = mockMvc.perform(
                        MockMvcRequestBuilders.get("/task/1")
                                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        ObjectReader reader = objectMapper.readerFor
                (new TypeReference<TaskDto>() {});
        TaskDto taskDto = reader.readValue(responseBody);

        Assertions.assertNotNull(taskDto);
        Assertions.assertEquals(1, taskDto.getId());
        Assertions.assertEquals("Title", taskDto.getTitle());
        Assertions.assertEquals("some text", taskDto.getDescription());
        Assertions.assertEquals(Priority.HIGH, taskDto.getPriority());
        Assertions.assertEquals(ONGOING, taskDto.getStatus());

    }



}
