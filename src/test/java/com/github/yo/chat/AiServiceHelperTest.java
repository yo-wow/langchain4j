package com.github.yo.chat;

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
}