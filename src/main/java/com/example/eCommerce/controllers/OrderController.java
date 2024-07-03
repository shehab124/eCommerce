package com.example.eCommerce.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.eCommerce.model.User;
import com.example.eCommerce.model.UserOrder;
import com.example.eCommerce.repositories.OrderRepository;
import com.example.eCommerce.repositories.UserRepository;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	private static final Logger log = LoggerFactory.getLogger(OrderController.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OrderRepository orderRepository;
	
	
	@PostMapping("/submit/{username}")
	public ResponseEntity<UserOrder> submit(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.error("Username {} not found", username);
			return ResponseEntity.notFound().build();
		}
		UserOrder order = UserOrder.createFromCart(user.getCart());
		orderRepository.save(order);

		log.info("Order submitted successfully for user {}", username);

		return ResponseEntity.ok(order);
	}
	
	@GetMapping("/history/{username}")
	public ResponseEntity<List<UserOrder>> getOrdersForUser(@PathVariable String username) {
		User user = userRepository.findByUsername(username);
		if(user == null) {
			log.error("Username {} not found!", username);
			return ResponseEntity.notFound().build();
		}
		log.debug("fetching orders for user {}", username);
		return ResponseEntity.ok(orderRepository.findByUser(user));
	}
}
