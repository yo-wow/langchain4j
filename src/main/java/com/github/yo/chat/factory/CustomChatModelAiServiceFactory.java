package com.github.yo.chat.factory;

import com.github.yo.chat.CustomChatModelAiServiceApi;
import com.github.yo.chat.utils.InterviewQuestionUtil;
import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.AiServices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class CustomChatModelAiServiceFactory {
    // 自定义带监听器的会话模型
    private final ChatModel myQwenChatModel;
    // 自定义的RAG
    private final ContentRetriever contentRetriever;
    // 自定义的tools之一
    private final InterviewQuestionUtil interviewQuestionUtil;
    // MCP工具
    private final McpToolProvider mcpToolProvider;

    @Bean
    public CustomChatModelAiServiceApi customChatModelAiServiceApi() {
        return AiServices.builder(CustomChatModelAiServiceApi.class)
                .chatModel(myQwenChatModel) // 会话模型
                .contentRetriever(contentRetriever) // 自定义RAG
                .tools(interviewQuestionUtil) // 添加自定义工具
                .toolProvider(mcpToolProvider) // 添加MCP工具
                .build();
    }
}
