package com.codewithhem.orderserviceconsumer.repo;


import com.codewithhem.orderserviceconsumer.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderCRUD extends CrudRepository<Order,Integer> {

}
