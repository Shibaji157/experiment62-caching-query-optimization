package com.example.experiment62.repository;

import com.example.experiment62.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    // Normal query
    @Query("SELECT u FROM User u")
    List<User> findAllUsers();

    // Optimized query using JOIN FETCH
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.address")
    List<User> findAllWithAddress();

    // Native SQL query
    @Query(
        value = """
                SELECT u.id, u.uid, u.name, a.city, a.country
                FROM users u
                LEFT JOIN addresses a ON u.id = a.user_id
                """,
        nativeQuery = true
    )
    List<Object[]> findUsersNative();

    // Sort by ID
    List<User> findAllByOrderByIdAsc();

    // Sort by Name
    List<User> findAllByOrderByNameAsc();
}