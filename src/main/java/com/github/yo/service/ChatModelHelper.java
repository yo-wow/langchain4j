package com.github.yo.service;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatModelHelper {
    private final ChatModel qwenChatModel;

    public String simpleTextChat(String message) {
        return qwenChatModel.chat(message);
    }

    public String multimodalityChat(UserMessage message) {
        ChatResponse chatResponse = qwenChatModel.chat(message);

        return chatResponse.aiMessage().text();
    }
}
