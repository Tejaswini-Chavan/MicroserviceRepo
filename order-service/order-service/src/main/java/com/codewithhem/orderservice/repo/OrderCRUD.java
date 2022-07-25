package com.codewithhem.orderservice.repo;

import com.codewithhem.orderservice.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCRUD extends CrudRepository<Order, Integer> {
}
