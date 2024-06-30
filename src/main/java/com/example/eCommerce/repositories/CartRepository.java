package com.example.eCommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eCommerce.model.Cart;
import com.example.eCommerce.model.User;

import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUser(User user);
}
