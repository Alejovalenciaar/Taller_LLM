package com.universidad.chatbot.config;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;

public final class ChatDtos {

    private ChatDtos() {
    }

    public record ChatRequest(
            @JsonAlias("pregunta")
            @NotBlank(message = "La pregunta no puede estar vacía") String message,
            String dominio) {
    }

    public record ChatResponse(String response) {
    }
}
