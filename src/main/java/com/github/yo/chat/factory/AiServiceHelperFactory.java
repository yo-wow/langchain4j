package com.github.yo.chat.factory;

import com.github.yo.chat.AiServiceHelper;
import com.github.yo.chat.utils.InterviewQuestionUtil;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
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

    // 会话模型
    private final ChatModel qwenChatModel;
    // 持久化的会话记忆存储
    private final ChatMemoryStore persistentChatMemoryStore;
    // 自定义的RAG
    private final ContentRetriever contentRetriever;
    // 自定义的tools之一
    private final InterviewQuestionUtil interviewQuestionUtil;

    /**
     * 创建 带仅内存存储的会话记忆的 AiServiceHelper
     *
     * @return
     */
    @Bean
    public AiServiceHelper aiServiceHelper() {
        // 会话记忆
        ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);

        /**
         // ---------- 简易版RAG ----------
         // 1. 文档收集，无切割
         List<Document> documents = FileSystemDocumentLoader.loadDocuments("src/main/resources/docs");
         // 2. 使用内置的向量模型EmbeddingModel将文本转换为向量，存储到自动注入的内存向量数据库embeddingStore中
         EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();// 内存向量存储
         EmbeddingStoreIngestor.ingest(documents, embeddingStore);

         return AiServices.builder(AiServiceHelper.class)
         .chatModel(qwenChatModel) // 会话模型
         .chatMemory(chatMemory) // 会话记忆
         .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore)) // RAG: 从内存embeddingStore中检索匹配的文本片段
         .build();
         */

        return AiServices.builder(AiServiceHelper.class)
                .chatModel(qwenChatModel) // 会话模型
                .chatMemory(chatMemory) // 会话记忆
                .contentRetriever(contentRetriever) // 自定义RAG
                .tools(interviewQuestionUtil) // 工具调用
                .build();
    }
}
