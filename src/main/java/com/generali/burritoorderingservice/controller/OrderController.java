package com.generali.burritoorderingservice.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.generali.burritoorderingservice.model.Order;
import com.generali.burritoorderingservice.repo.OrderRepository;

@RestController
public class OrderController {

	@Autowired
	OrderRepository orderRepository;

	@PostMapping("/orders")
	public ResponseEntity<Order> saveNewOrder(@RequestBody Order order){
		try {
			Order savedOrder = orderRepository.save(order);

			return new ResponseEntity<Order>(savedOrder, HttpStatus.CREATED);
		}catch (Exception exc) {
			throw new ResponseStatusException(
					HttpStatus.BAD_REQUEST, "Order could Not be saved" + exc.getLocalizedMessage());
		}
	}

	@GetMapping("/orders/{orderId}")
	public ResponseEntity<Order> getAnOrder(@PathVariable Long orderId){
		try {
			Optional<Order> result = orderRepository.findById(orderId);

			return ResponseEntity.of(result);
		}catch (Exception exc) {
			throw new ResponseStatusException(
					HttpStatus.NOT_FOUND, "Order Not Found" + exc.getLocalizedMessage());
		}
	}

}
