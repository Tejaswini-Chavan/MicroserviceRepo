package com.codewithhem.orderserviceconsumer.repo;

import com.codewithhem.orderserviceconsumer.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserCRUD extends CrudRepository<User,Integer> {
}