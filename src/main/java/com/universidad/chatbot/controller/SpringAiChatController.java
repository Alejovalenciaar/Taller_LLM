package com.universidad.chatbot.controller;

import java.util.Map;

import com.universidad.chatbot.config.ChatDtos.ChatRequest;
import com.universidad.chatbot.config.ChatDtos.ChatResponse;
import com.universidad.chatbot.service.SpringAiChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v2/chat")
public class SpringAiChatController {

    private final SpringAiChatService chatService;

    public SpringAiChatController(SpringAiChatService chatService) {
        this.chatService = chatService;
    }

    @PostMapping
    public ChatResponse chat(@Valid @RequestBody ChatRequest request) {
        return new ChatResponse(chatService.chat(request.message(), request.dominio()));
    }

    @GetMapping("/salud")
    public Map<String, String> health() {
        return Map.of("estado", "ok");
    }

    @PostMapping("/rapido")
    public ResponseEntity<?> chatQuick(
            @RequestParam(required = false) String pregunta,
            @RequestParam(required = false) String dominio) {
        if (!StringUtils.hasText(pregunta)) {
            return ResponseEntity.badRequest().body(Map.of("error", "La pregunta no puede estar vacía"));
        }
        return ResponseEntity.ok(new ChatResponse(chatService.chat(pregunta, dominio)));
    }
}
