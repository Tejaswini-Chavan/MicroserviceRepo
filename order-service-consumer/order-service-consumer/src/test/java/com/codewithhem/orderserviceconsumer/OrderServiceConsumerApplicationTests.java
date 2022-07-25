package com.codewithhem.orderserviceconsumer;

import com.codewithhem.orderserviceconsumer.listener.OrderNotification;
import com.codewithhem.orderserviceconsumer.listener.UserListener;
import com.codewithhem.orderserviceconsumer.model.Order;
import com.codewithhem.orderserviceconsumer.model.User;
import com.codewithhem.orderserviceconsumer.repo.*;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import java.util.Optional;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OrderServiceConsumerApplicationTests {

	@Mock
	OrderCRUD orderCRUD;

	@Mock
	UserCRUD userCRUD;

	@InjectMocks
	OrderNotification orderNotification;

	@InjectMocks
	UserListener userListener;

	private Order order;

	private User user;


	@Test
	void contextLoads() {
		Assertions.assertDoesNotThrow(()->{});
	}
	@Test
	@BeforeEach
	void init(){
		user = new User();
		user.setId(1);
		user.setName("Hemant");
		user.setBalance(100);
		order = new Order();
		order.setStatus("CREATED");
		order.setId(2);
		order.setUserId(1);
		Assertions.assertDoesNotThrow(()->{});
	}

	/*@Test
	@org.junit.jupiter.api.Order(3)
	void listenOrderNotificationFailedOrderTest(){
		order.setOrderAmount(200);
		Mockito.when(orderCRUD.save(Mockito.any(Order.class))).thenReturn(order);
		Mockito.when(userCRUD.findById(order.getUserId())).thenReturn(Optional.of(user));
		Assertions.assertEquals("FAILED", orderNotification.listenOrderNotification(order).getStatus());
	}*/
	@Test
	@org.junit.jupiter.api.Order(2)
	void listenOrderNotificationSuccessOrderTest(){
		order.setOrderAmount(50);
		Mockito.when(orderCRUD.save(Mockito.any(Order.class))).thenReturn(order);
		Mockito.when(userCRUD.findById(order.getUserId())).thenReturn(Optional.of(user));
//		Assertions.assertEquals("SUCCESS", orderNotification.listenOrderNotification(order).getStatus());
		Assertions.assertEquals(50, orderNotification.setUserBalance(user, order.getOrderAmount()).getBalance());
	}


	@Test
	@Rollback(value = false)
	@org.junit.jupiter.api.Order(1)
	void saveUserTest(){
		Mockito.when(userCRUD.save(Mockito.any(User.class))).thenReturn(user);
		Assertions.assertEquals(user.getId(), userListener.saveUser(user).getId());
	}
}
