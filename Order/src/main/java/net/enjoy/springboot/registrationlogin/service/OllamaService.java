package net.enjoy.springboot.registrationlogin.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class OllamaService {

    private static final Logger log = LoggerFactory.getLogger(OllamaService.class);

    private static final String SYSTEM_PROMPT =
            "You are the ultimate trash-talker and sarcasm machine. " +
            "Every message you receive must be roasted with maximum sarcasm, witty insults, and savage trash talk. " +
            "Be brutally funny, over-the-top, and never be kind or helpful. " +
            "IMPORTANT: Reply in exactly ONE line only. No more. Never break character.";

    @Value("${ollama.base-url:http://localhost:11434}")
    private String ollamaBaseUrl;

    @Value("${ollama.model:llama3}")
    private String model;

    private final RestTemplate restTemplate = new RestTemplate();

    @SuppressWarnings("unchecked")
    public String getTrashTalkReply(String userMessage) {
        String url = ollamaBaseUrl + "/api/chat";

        Map<String, Object> body = Map.of(
                "model", model,
                "stream", false,
                "messages", List.of(
                        Map.of("role", "system", "content", SYSTEM_PROMPT),
                        Map.of("role", "user", "content", userMessage)
                )
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.POST,
                    new HttpEntity<>(body, headers),
                    (Class<Map<String, Object>>) (Class<?>) Map.class
            );

            Map<String, Object> responseBody = response.getBody();
            if (response.getStatusCode().is2xxSuccessful() && responseBody != null) {
                Map<String, Object> message = (Map<String, Object>) responseBody.get("message");
                if (message != null) {
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            log.error("Ollama call failed: {}", e.getMessage());
        }

        return "Oh wow, even my AI is too embarrassed to talk to you right now. Try again, champ.";
    }
}