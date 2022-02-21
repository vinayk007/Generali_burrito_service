package com.generali.burritoorderingservice.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.generali.burritoorderingservice.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
