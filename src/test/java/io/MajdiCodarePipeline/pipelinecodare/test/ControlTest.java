package io.MajdiCodarePipeline.pipelinecodare.test;

import io.MajdiCodarePipeline.pipelinecodare.controller.MessageController;
import io.MajdiCodarePipeline.pipelinecodare.Repository.MySqlRepository;
import io.MajdiCodarePipeline.pipelinecodare.entity.Message;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
public class ControlTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MySqlRepository mySqlRepository;

    private Message sampleMessage;

    @BeforeEach
    void setUp() {
        sampleMessage = new Message();
        sampleMessage.setId(1L);
        sampleMessage.setContent("This is a test message");

    }

    @Test
    public void addMessageTest() throws Exception {
        given(mySqlRepository.save(any(Message.class))).willReturn(sampleMessage);

        mockMvc.perform(post("/api/messages/addMessage")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"This is a test message\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(sampleMessage.getId()))
                .andExpect(jsonPath("$.content").value(sampleMessage.getContent()));

        verify(mySqlRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void getMessagesTest() throws Exception {
        given(mySqlRepository.findAll()).willReturn(Arrays.asList(sampleMessage));

        mockMvc.perform(get("/api/messages/getMessages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(sampleMessage.getId()))
                .andExpect(jsonPath("$[0].content").value(sampleMessage.getContent()));

        verify(mySqlRepository, times(1)).findAll();
    }

    @Test
    public void getOneMessageTest() throws Exception {
        given(mySqlRepository.findById(sampleMessage.getId())).willReturn(Optional.of(sampleMessage));

        mockMvc.perform(get("/api/messages/{id}", sampleMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sampleMessage.getId()))
                .andExpect(jsonPath("$.content").value(sampleMessage.getContent()));

        verify(mySqlRepository, times(1)).findById(sampleMessage.getId());
    }

    @Test
    public void editOneMessageTest() throws Exception {
        Message updatedMessage = new Message();
        updatedMessage.setId(sampleMessage.getId());
        updatedMessage.setContent("Updated content");
        // Update other fields as needed for the test

        given(mySqlRepository.findById(sampleMessage.getId())).willReturn(Optional.of(sampleMessage));
        given(mySqlRepository.save(any(Message.class))).willReturn(updatedMessage);

        mockMvc.perform(patch("/api/messages/{id}", sampleMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\": \"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedMessage.getId()))
                .andExpect(jsonPath("$.content").value(updatedMessage.getContent()));

        verify(mySqlRepository, times(1)).findById(sampleMessage.getId());
        verify(mySqlRepository, times(1)).save(any(Message.class));
    }

    @Test
    public void deleteMessageTest() throws Exception {
        given(mySqlRepository.existsById(sampleMessage.getId())).willReturn(true);

        mockMvc.perform(delete("/api/messages/{id}", sampleMessage.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Message with ID " + sampleMessage.getId() + " was deleted."));

        verify(mySqlRepository, times(1)).deleteById(sampleMessage.getId());
    }


}
