package com.codewithhem.orderservice;

import com.codewithhem.orderservice.dto.OrderDTO;
import com.codewithhem.orderservice.dto.UserDTO;
import com.codewithhem.orderservice.model.Order;
import com.codewithhem.orderservice.model.User;
import com.codewithhem.orderservice.repo.OrderCRUD;
import com.codewithhem.orderservice.repo.UserCRUD;
import com.codewithhem.orderservice.service.OrderService;
import com.codewithhem.orderservice.service.UserService;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceApplicationTests {

	private Order order;

	private User user;

	private OrderDTO orderDTO;

	private UserDTO userDTO;

	@Mock
	OrderCRUD orderCRUD;

	@Mock
	UserCRUD userCRUD;

	@InjectMocks
	OrderService orderService = new OrderService();

	@InjectMocks
	UserService userService = new UserService();

	@Test
	void contextLoads() {
		Assertions.assertDoesNotThrow(()->{});
	}

	@Test
	@BeforeEach()
	void inIt(){
		userDTO = new UserDTO();
		userDTO.setId(1);
		userDTO.setName("Hemant");
		userDTO.setBalance(100);
		orderDTO = new OrderDTO();
		orderDTO.setId(2);
		orderDTO.setStatus("CREATED");
		orderDTO.setUserId(1);
		orderDTO.setOrderAmount(100);
		user = new User();
		user.setName(userDTO.getName());
		user.setId(userDTO.getId());
		user.setBalance(user.getBalance());
		order = new Order();
		order.setId(orderDTO.getId());
		order.setStatus(orderDTO.getStatus());
		order.setOrderAmount(orderDTO.getOrderAmount());
		order.setUserId(orderDTO.getUserId());
		Assertions.assertDoesNotThrow(()->{});
	}

	@Test
	@Rollback(value = false)
	@org.junit.jupiter.api.Order(1)
	void saveUserTest(){
		Mockito.when(userCRUD.save(Mockito.any(User.class))).thenReturn(user);
		Assertions.assertEquals(user.getId(),userService.saveUser(user).getId());
	}


	@Test
	@org.junit.jupiter.api.Order(3)
	void getAllUserTest(){
		List<User> users = new ArrayList<>();
		users.add(user);
		Mockito.when(userCRUD.findAll()).thenReturn(users);
		Assertions.assertEquals(user.getId(), userService.getAllUser().get(0).getId());
	}

	@Test
	@Rollback(value = false)
	@org.junit.jupiter.api.Order(2)
	void createOrderTest(){
		Mockito.when(orderCRUD.save(Mockito.any(Order.class))).thenReturn(order);
		Assertions.assertEquals(order.getId(), orderService.createOrder(order).getId());
	}

	@Test
	@org.junit.jupiter.api.Order(4)
	void getAllOrdersTest(){
		List<Order> orders = new ArrayList<>();
		orders.add(order);
		Mockito.when(orderCRUD.findAll()).thenReturn(orders);
		Assertions.assertEquals(order.getId(), orderService.getAllOrders().get(0).getId());
	}






}
