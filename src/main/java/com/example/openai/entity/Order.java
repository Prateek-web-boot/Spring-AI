//package com.example.openai.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//@Entity
//@Table(name="orders")
//@Data
//public class Order {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String productName;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="userId", nullable = false)
//    private User user;
//}
