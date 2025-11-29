package com.example.openai.controller;


import com.example.openai.service.ChatServiceImpl;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ChatController {

    @Autowired
    private ChatServiceImpl chatService;


    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam("message") String message,
                                        @RequestHeader("userId") String userId) {

       String result = chatService.chatTemplate(message, userId);
       return ResponseEntity.ok(result);
    }


    @PostMapping("/addVector")
    public void addDocToVectorStore() {

        chatService.addDocs();
    }


    @GetMapping("/searchVector")
    public ResponseEntity<List<Document>> getVectorData(@RequestParam("similar") String similar) {

        List<Document> result = chatService.fetchVectors(similar);
        return ResponseEntity.ok(result);

    }

    @GetMapping("/ragSearch")
    public ResponseEntity<String> ragSearch(@RequestParam("userPrompt") String userPrompt) {

        String result = chatService.ragQuery(userPrompt);
        return ResponseEntity.ok(result);

    }


    @GetMapping("/ragAdvisorSearch")
    public ResponseEntity<String> ragAdvisorSearch(@RequestParam("userPrompt") String userPrompt) {

        String result = chatService.ragQuerywithAdvisor(userPrompt);
        return ResponseEntity.ok(result);

    }
}
