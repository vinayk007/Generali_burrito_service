package com.generali.burritoorderingservice.IntegrationTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.generali.burritoorderingservice.controller.OrderController;
import com.generali.burritoorderingservice.model.Extras;
import com.generali.burritoorderingservice.model.Order;
import com.generali.burritoorderingservice.model.Protein;
import com.generali.burritoorderingservice.model.Salsa;
import com.generali.burritoorderingservice.model.Tortilla;
import com.generali.burritoorderingservice.model.Vegetable;
import com.generali.burritoorderingservice.repo.OrderRepository;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
public class IntegrationTest {

	@Autowired
	MockMvc mvc;
	
	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	OrderRepository orderRepo;

	@Autowired
	OrderController orderController;
	
	@Test
	void validTortilla_Created() throws Exception {
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
		String inputJson = objectMapper.writeValueAsString(order);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/orders")
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		assertEquals(201, mvcResult.getResponse().getStatus());
	}

	@Test
	void getOrder_Success() throws Exception {
		Long orderId = 1L;
		Order order = new Order();
		order.setId(orderId);
		order.setTortilla(Tortilla.corn);
		Optional<Order> resOrder = Optional.of(order);
		
		when(orderRepo.findById(orderId)).thenReturn(resOrder);
		String inputJson = objectMapper.writeValueAsString(order);
		
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/orders/"+orderId)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(inputJson)).andReturn();

		assertEquals(200, mvcResult.getResponse().getStatus());
	}

}
