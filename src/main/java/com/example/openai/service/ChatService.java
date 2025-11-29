package com.example.openai.service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.document.Document;

import java.util.List;

public interface ChatService {

    public String chatTemplate(String q, String conversationId);

    public void addDocs();

    public List<Document> fetchVectors(String similar);

    public String ragQuery(String q);
}
