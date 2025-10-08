package com.github.yo.chat.factory;

import com.github.yo.chat.AiServiceHelper;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.ChatMemoryStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AiServiceHelperFactory {

    private final ChatModel qwenChatModel;
    private final ChatMemoryStore persistentChatMemoryStore;

    /**
     * 创建 最基础的 AiServiceHelper
     *
     * @return
     */
//    @Bean
//    public AiServiceHelper aiServiceHelper() {
//        return AiServices.create(AiServiceHelper.class, qwenChatModel);
//    }

    /**
     * 创建 带仅内存存储的会话记忆的 AiServiceHelper
     *
     * @return
     */
    @Bean
    public AiServiceHelper aiServiceHelper() {
        // 会话记忆
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        return AiServices.builder(AiServiceHelper.class)
                .chatModel(qwenChatModel) // 会话模型
                .chatMemory(chatMemory) // 会话记忆
                .build();
    }

//    /**
//     * 创建 带持久化存储的会话记忆的 AiServiceHelper
//     *
//     * @return
//     */
//    @Bean
//    public AiServiceHelper aiServiceHelper() {
//        // 会话记忆
//        String id = UUID.randomUUID().toString();
//        log.info("chat-memory id: {}", id);
//        ChatMemory chatMemory = MessageWindowChatMemory.builder()
//                .id(id)
//                .maxMessages(10)
//                .chatMemoryStore(persistentChatMemoryStore)
//                .build();
//
//        return AiServices.builder(AiServiceHelper.class)
//                .chatModel(qwenChatModel) // 会话模型
//                .chatMemory(chatMemory) // 会话记忆
//                .build();
//    }
}
