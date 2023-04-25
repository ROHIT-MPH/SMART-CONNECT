package com.example.SmartConnect.DAO;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.SmartConnect.Entities.User;

public interface UserRepository extends JpaRepository<User, Integer>{

}
