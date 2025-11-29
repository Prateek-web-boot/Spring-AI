//package com.example.openai.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Set;
//
//@Entity
//@Table(name = "users")
//@Data
//public class User {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    private String name;
//    private String email;
//    private String phone;
//    private String address;
//
//    @OneToMany(mappedBy = "user",
//            cascade = CascadeType.ALL,
//            fetch = FetchType.LAZY)
//    private Set<Order> orders = new HashSet<>();
//    public User() {}
//}
