package com.github.yo;

import com.github.yo.service.ChatModelHelper;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Slf4j
public class ApplicationTests {

    @Resource
    private ChatModelHelper chatModelHelper;

    @Test
    public void simpleTextChat() {
        String resp = chatModelHelper.simpleTextChat("你好");
        log.info("resp: {}", resp);
    }
}
