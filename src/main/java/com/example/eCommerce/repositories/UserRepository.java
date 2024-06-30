package com.example.eCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eCommerce.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	User findByUsername(String username);
}
