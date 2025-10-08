package com.github.yo.chat.factory;

import com.github.yo.chat.AiServiceHelper;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AiServiceFactory {

    private final ChatModel qwenChatModel;

    @Bean
    public AiServiceHelper aiServiceHelper() {
        return AiServices.create(AiServiceHelper.class, qwenChatModel);
    }
}
