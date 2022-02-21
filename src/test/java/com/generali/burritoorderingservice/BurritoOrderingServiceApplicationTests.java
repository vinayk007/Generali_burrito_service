package com.generali.burritoorderingservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.generali.burritoorderingservice.controller.OrderController;
import com.generali.burritoorderingservice.model.Extras;
import com.generali.burritoorderingservice.model.Order;
import com.generali.burritoorderingservice.model.Protein;
import com.generali.burritoorderingservice.model.Salsa;
import com.generali.burritoorderingservice.model.Tortilla;
import com.generali.burritoorderingservice.model.Vegetable;
import com.generali.burritoorderingservice.repo.OrderRepository;

@SpringBootTest
class BurritoOrderingServiceApplicationTests {

	@MockBean
	OrderRepository orderRepo;

	@Autowired
	OrderController orderController;

	@Test
	void validTortilla_NoVegetable_Created() {
		Order order = new Order();
		order.setTortilla(Tortilla.corn);
		
		when(orderRepo.save(order)).thenReturn(order);
		ResponseEntity<Order> res = orderController.saveNewOrder(order);
		
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	void validAll_Created() {
		Order order = new Order();
		order.setTortilla(Tortilla.corn);
		List<Vegetable> vegList = new ArrayList<>();
		vegList.add(Vegetable.cabbage);
		vegList.add(Vegetable.corn);
		order.setVegetable(vegList);
		order.setProtein(Protein.bean);
		order.setSalsa(Salsa.hot);
		order.setExtras(Extras.avocado);
		
		when(orderRepo.save(order)).thenReturn(order);
		ResponseEntity<Order> res = orderController.saveNewOrder(order);
		
		assertEquals(HttpStatus.CREATED, res.getStatusCode());
	}

	@Test
	void getOrder_Success() {
		Order order = new Order();
		Long orderId = 1L;
		order.setId(orderId);
		order.setTortilla(Tortilla.corn);
		List<Vegetable> vegList = new ArrayList<>();
		vegList.add(Vegetable.cabbage);
		vegList.add(Vegetable.corn);
		order.setVegetable(vegList);
		Optional<Order> resOrder = Optional.of(order);
		
		when(orderRepo.findById(orderId)).thenReturn(resOrder);
		ResponseEntity<Order> res = orderController.getAnOrder(orderId);
		
		assertEquals(HttpStatus.OK, res.getStatusCode());
	}

	@Test
	void getOrder_NotFound() {
		Order order = null;
		Long orderId = 1L;
		Optional<Order> resOrder = Optional.ofNullable(order);
		when(orderRepo.findById(orderId)).thenReturn(resOrder);
		ResponseEntity<Order> res = orderController.getAnOrder(orderId);
		
		assertEquals(HttpStatus.NOT_FOUND, res.getStatusCode());
	}

}
