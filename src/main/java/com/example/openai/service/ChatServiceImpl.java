package com.example.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.memory.repository.jdbc.JdbcChatMemoryRepository;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatServiceImpl implements ChatService {


    private String systemMessage = "You are an Expert";
    private String userMessage = "Tell me about India";

    @Autowired
    private ChatClient chatClient;

    @Autowired
    private VectorStore vectorStore;

    public ChatServiceImpl(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    @Override
    public String chatTemplate(String query, String conversationId) {
        return this.chatClient
                .prompt()
                .advisors(advisorSpec -> advisorSpec.param(ChatMemory.CONVERSATION_ID, conversationId))
                .system(system -> system.text(this.systemMessage))
                .user(user -> user.text(query))
                .call()
                .content();
    }


    @Override
    public void addDocs() {
        List<Document> documents = List.of(
                new Document("Spring AI rocks!!"),
                new Document("The World is big and salvation lurks."),
                new Document("You walk forward facing the past."),
                new Document("India is country in southeast ASia"),
                new Document("India is 7th largets nation in the world")

        );

        vectorStore.add(documents);
    }


    @Override
    public List<Document> fetchVectors(String query) {

        //load data from vectro DB
        SearchRequest request = SearchRequest.builder()
                .query(query)
                .topK(3)
                .similarityThreshold(0.8)
                .build();

        List<Document> docs = vectorStore.similaritySearch(request);

        return docs;
    }


    // Rag Pipeline
    //Step1: fetch similar Data from VectroDB
    //step2: Clean and format the data accordingly to form a context
    //step3: Add the user query wuth the context generated in step 2
    //step4: pass on the final prompt to LLM to answer accordingly
    @Override
    public String ragQuery(String query) {

        //fetching similar results from vactor DB
        var result = vectorStore.similaritySearch(
                SearchRequest.builder().query(query).topK(3).similarityThreshold(0.5).build());

        //converting the data from vectorDB to a single String
        List<String> context = result.stream().map(Document::getText).toList();
        var contextString = String.join(", ", context);

        //Use OpenAI LLM to answer as per the data fetched from vector DB
        return chatClient.prompt()
                .system(s -> s.text("Use the following context to answer, if no answer found say I Dont Know"))
                .user(u -> u.text("Context:\n" + context + "\n\nQuestion: " + query))
                .call()
                .content();
    }


}
