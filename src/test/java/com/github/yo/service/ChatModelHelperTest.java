package com.github.yo.service;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class ChatModelHelperTest {
    @Resource
    private ChatModelHelper chatModelHelper;

    @Test
    void simpleTextChat() {
        String resp = chatModelHelper.simpleTextChat("你好");
        log.info("resp: {}", resp);
    }

    @Test
    void multimodalityChat() {
        UserMessage userMessage = UserMessage.from(
                TextContent.from("描述图片"),
                ImageContent.from("https://avatars.githubusercontent.com/u/88282543?v=4&size=64")
        );
        String resp = chatModelHelper.multimodalityChat(userMessage);
        log.info("resp: {}", resp);
    }

    @Test
    void simpleTextChatWithSystemMessage() {
        UserMessage userMessage = UserMessage.from("你好");
        String resp = chatModelHelper.simpleTextChatWithSystemMessage(userMessage);
        log.info("resp: {}", resp);
    }
}