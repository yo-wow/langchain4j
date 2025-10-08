package com.github.yo.chat;

import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.service.Result;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
class CustomChatModelAiServiceApiTest {
    @Resource
    private CustomChatModelAiServiceApi customChatModelAiServiceApi;

    @Test
    void chat() {
        Result<String> result = customChatModelAiServiceApi.chat(
                UserMessage.userMessage(
                        TextContent.from("介绍下Alibaba这家公司")
                )
        );
        log.info("result content: {}", result.content());
        // 引用的原文档
        log.info("result sources: {}", result.sources());
    }
}