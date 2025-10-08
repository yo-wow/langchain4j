package com.github.yo.service;

import dev.langchain4j.data.message.SystemMessage;
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

    private static final String SYSTEM_MESSAGE = """
            你是编程领域的小助手，帮助用户解答编程学习和求职面试相关的问题，并给出建议。重点关注 4 个方向：
            1. 规划清晰的编程学习路线
            2. 提供项目学习建议
            3. 给出程序员求职全流程指南（比如简历优化、投递技巧）
            4. 分享高频面试题和面试技巧
            请用简洁易懂的语言回答，助力用户高效学习与求职。
            """;

    public String simpleTextChat(String message) {
        return qwenChatModel.chat(message);
    }

    public String multimodalityChat(UserMessage message) {
        ChatResponse chatResponse = qwenChatModel.chat(message);

        return chatResponse.aiMessage().text();
    }

    public String simpleTextChatWithSystemMessage(UserMessage userMessage) {
        SystemMessage systemMessage = SystemMessage.from(SYSTEM_MESSAGE);
        ChatResponse chatResponse = qwenChatModel.chat(systemMessage, userMessage);

        return chatResponse.aiMessage().text();
    }
}
