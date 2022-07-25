package com.codewithhem.orderservice.repo;

import com.codewithhem.orderservice.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCRUD extends CrudRepository<User, Integer> {
}
