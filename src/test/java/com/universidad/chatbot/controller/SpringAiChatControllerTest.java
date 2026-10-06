package com.universidad.chatbot.controller;

import com.universidad.chatbot.service.SpringAiChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SpringAiChatController.class)
class SpringAiChatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SpringAiChatService chatService;

    @Test
    void healthCheckDoesNotCallChatService() throws Exception {
        mockMvc.perform(get("/api/v2/chat/salud"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("ok"));
    }

    @Test
    void quickChatAcceptsQuestionAndDomainAsQueryParameters() throws Exception {
        when(chatService.chat("Explica Maven", "Java")).thenReturn("Maven gestiona proyectos Java.");

        mockMvc.perform(post("/api/v2/chat/rapido")
                        .queryParam("pregunta", "Explica Maven")
                        .queryParam("dominio", "Java"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.response").value("Maven gestiona proyectos Java."));

        verify(chatService).chat("Explica Maven", "Java");
    }

    @Test
    void quickChatRejectsBlankQuestion() throws Exception {
        mockMvc.perform(post("/api/v2/chat/rapido")
                        .queryParam("pregunta", " ")
                        .queryParam("dominio", "Java"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("La pregunta no puede estar vacía"));
    }
}
