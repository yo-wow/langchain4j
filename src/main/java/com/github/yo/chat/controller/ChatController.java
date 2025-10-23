package com.github.yo.chat.controller;

import com.github.yo.chat.AiServiceHelper;
import dev.langchain4j.data.message.UserMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final AiServiceHelper aiServiceHelper;

    @GetMapping("/sse")
    public Flux<ServerSentEvent<String>> chat(int memoryId, String userMessage) {
        return aiServiceHelper.chatStream(memoryId, UserMessage.userMessage(userMessage))
                .map(text -> ServerSentEvent.builder(text).build());
    }
}
