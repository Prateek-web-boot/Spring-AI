//package com.example.openai.repository;
//
//import com.example.openai.entity.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.Optional;
//
//@Repository
//public interface UserRepository extends JpaRepository<User, Long> {
//
//    Optional<User> findByName(String name);
//
//    @Query("select u from User u left join fetch u.orders where u.id = :id")
//    Optional<User> findIdWithOrders(@Param("id") Long id);
//
//    @Query("select distinct u from User u left join fetch u.orders")
//    List<User> findAllWithOrders();
//}
