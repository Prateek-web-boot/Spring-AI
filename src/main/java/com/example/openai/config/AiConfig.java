package com.example.openai.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.ai.openai.OpenAiEmbeddingModel;
import org.springframework.ai.openai.api.OpenAiApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Configuration
public class AiConfig {

    private Logger logger = LoggerFactory.getLogger(AiConfig.class);
    public ChatClient chatClient;

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory memory, ChatMemory chatMemory) {


        logger.info("ChatMemory Impl class: " + chatMemory.getClass().getName());
        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return builder
                .defaultAdvisors(messageChatMemoryAdvisor,  new SafeGuardAdvisor(List.of("games")))
                .defaultSystem("You are an expert")
                .defaultOptions(OpenAiChatOptions.builder()
                .model("gpt-4o-mini")
                .temperature(0.3)
                .maxTokens(200)
                .build())
        .build();
    }


    @Bean
    public EmbeddingModel embeddingModel(OpenAiApi openAiApi) {
        return new OpenAiEmbeddingModel(openAiApi);
    }


}
