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
class AiServiceHelperTest {

    @Resource
    private AiServiceHelper aiServiceHelper;

    @Test
    void simpleTextChat() {
        String resp = aiServiceHelper.simpleTextChat("你好");
        log.info("resp: {}", resp);
    }

    @Test
    void simpleTextChatWithMemory() {
        String resp = aiServiceHelper.simpleTextChat("你好，我是鱼丸");
        log.info("resp: {}", resp);

        resp = aiServiceHelper.simpleTextChat("我是谁你还记得吗");
        log.info("resp: {}", resp);
    }

    @Test
    void jsonChat() {
        AiServiceHelper.JsonResp jsonResp = aiServiceHelper.jsonChat(
                UserMessage.userMessage(
                        TextContent.from("我叫鱼丸，练习时长两年半，请帮我制定一份提升计划")
                )
        );
        log.info("jsonResp: {}", jsonResp);
    }

    @Test
    void easyRagChat() {
        String resp = aiServiceHelper.simpleTextChat("有哪些Java相关的常见面试题？");
        log.info("resp: {}", resp);
    }

    @Test
    void customRagChat() {
        Result<String> result = aiServiceHelper.ragChat(
                UserMessage.userMessage(
                        TextContent.from("有哪些Java相关的常见面试题？")
                )
        );
        log.info("result content: {}", result.content());
        // 引用的原文档
        log.info("result sources: {}", result.sources());
    }
}