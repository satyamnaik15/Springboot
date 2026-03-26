package net.enjoy.springboot.registrationlogin.controller;

import lombok.RequiredArgsConstructor;
import net.enjoy.springboot.registrationlogin.dto.ChatRequest;
import net.enjoy.springboot.registrationlogin.dto.ChatResponse;
import net.enjoy.springboot.registrationlogin.service.OllamaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private static final Logger log = LoggerFactory.getLogger(ChatController.class);

    private final OllamaService ollamaService;

    @PostMapping("/message")
    public ChatResponse sendMessage(
            @RequestBody ChatRequest request,
            @AuthenticationPrincipal UserDetails userDetails) {

        log.info("Chat message from [{}]: {}", userDetails.getUsername(), request.getMessage());
        String reply = ollamaService.getTrashTalkReply(request.getMessage());
        log.info("Ollama reply for [{}]: {}", userDetails.getUsername(), reply);
        return new ChatResponse(reply);
    }
}