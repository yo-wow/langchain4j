package com.github.yo.chat.config;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.listener.ChatModelListener;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "langchain4j.community.dashscope.chat-model")
@Data
@Slf4j
@RequiredArgsConstructor
public class MyQwenChatModelConfig {

    private String modelName;

    private String apiKey;

    // 监听器
    private final ChatModelListener chatModelListener;

    @Bean
    public ChatModel myQwenChatModel() {
        return QwenChatModel.builder()
                .modelName(modelName)
                .apiKey(apiKey)
                .listeners(List.of(chatModelListener))
                .build();
    }
}
