package com.example.experiment62.service;

import com.example.experiment62.dto.UserRequest;
import com.example.experiment62.model.Address;
import com.example.experiment62.model.User;
import com.example.experiment62.repository.UserRepository;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @CacheEvict(value = "users", key = "'allUsers'")
    @Transactional
    public User createUser(UserRequest request) {

        User user = new User();
        user.setUid(request.getUid());
        user.setName(request.getName());

        Address address = new Address();
        address.setCity(request.getCity());
        address.setCountry(request.getCountry());

        address.setUser(user);
        user.setAddress(address);

        return userRepository.save(user);
    }

    // Normal Query
    @Transactional(readOnly = true)
    public List<User> getUsersNormal() {

        List<User> users = userRepository.findAll();

        // Access lazy address to demonstrate N+1 query behaviour
        for (User user : users) {
            if (user.getAddress() != null) {
                user.getAddress().getCity();
            }
        }

        return users;
    }

    // Optimized Query
    @Transactional(readOnly = true)
    public List<User> getUsersOptimized() {
        return userRepository.findAllWithAddress();
    }

    // Cached Query
    @Cacheable(value = "users", key = "'allUsers'")
    @Transactional(readOnly = true)
    public List<User> getUsersCached() {

        System.out.println("CACHE MISS - Loading users from database");

        return userRepository.findAllWithAddress();
    }

    // Native SQL Query
    @Transactional(readOnly = true)
    public List<Object[]> getUsersNative() {
        return userRepository.findUsersNative();
    }

    // Sort by ID
    @Transactional(readOnly = true)
    public List<User> getUsersSortedById() {
        return userRepository.findAllByOrderByIdAsc();
    }

    // Sort by Name
    @Transactional(readOnly = true)
    public List<User> getUsersSortedByName() {
        return userRepository.findAllByOrderByNameAsc();
    }

    // Delete User
    @CacheEvict(value = "users", key = "'allUsers'")
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}