package com.github.yo.chat.config;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentByParagraphSplitter;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class RagConfig {
    // 向量模型
    private final EmbeddingModel qwenEmbeddingModel;
    // 向量数据库：内存存储
    private final EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    @Bean
    public ContentRetriever contentRetriever() throws IOException {
        // 1.1 加载文档
        List<Document> documents = loadDocumentsFromClasspath();

        // 1.2 文档切割：将每个文档按段落切割，最大1000字符，每次重叠最多200字符，重叠目的是为了防止文档切割出现的断层影响
        DocumentByParagraphSplitter paragraphSplitter =
                new DocumentByParagraphSplitter(1000, 200);
        // 2.1 自定义文档加载器
        EmbeddingStoreIngestor ingestor = EmbeddingStoreIngestor.builder()
                .documentSplitter(paragraphSplitter) // 文档切割
                .textSegmentTransformer(
                        textSegment ->
                                TextSegment.from(
                                        String.format("%s%s%s",
                                                textSegment.metadata().getString("file_name"),
                                                System.lineSeparator(),
                                                textSegment.text()),
                                        textSegment.metadata()
                                )
                )// 为了提高搜索质量，为每个TextSegment添加文档名称
                .embeddingModel(qwenEmbeddingModel) // 向量模型
                .embeddingStore(embeddingStore)
                .build();
        // 2.2 加载文档
        ingestor.ingest(documents);
        // 3.1 自定义文档检索器
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore) // 向量数据库
                .embeddingModel(qwenEmbeddingModel) // 向量模型
                .maxResults(5) // 最多返回5个结果
                .minScore(0.75) // 过滤掉分数低于0.75的
                .build();
    }

    private List<Document> loadDocumentsFromClasspath() throws IOException {
        List<Document> documents = new ArrayList<>();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = resolver.getResources("classpath:docs/*");

        DocumentParser parser = new TextDocumentParser();

        for (Resource resource : resources) {
            try {
                // 使用DocumentParser解析文档
                Document document = parser.parse(resource.getInputStream());
                // 添加文件名到元数据
                document.metadata().put("file_name", resource.getFilename());
                documents.add(document);
                log.info("Loaded document: {}", resource.getFilename());
            } catch (IOException e) {
                log.error("Failed to load document: {}", resource.getFilename(), e);
            }
        }

        if (documents.isEmpty()) {
            log.warn("No documents found in classpath:docs/ directory");
        }

        return documents;
    }
}
