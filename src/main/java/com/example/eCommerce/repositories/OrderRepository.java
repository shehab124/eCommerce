package com.example.eCommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eCommerce.model.User;
import com.example.eCommerce.model.UserOrder;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<UserOrder, Long> {
	List<UserOrder> findByUser(User user);
}
