package com.universidad.chatbot.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SpringAiChatService {

    private final ChatClient chatClient;

    public SpringAiChatService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String chat(String message, String domain) {
        String prompt = domain == null || domain.isBlank()
                ? message
                : "Dominio: " + domain + "\nPregunta: " + message;
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}
