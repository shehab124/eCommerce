package com.example.eCommerce.controllers;

import com.example.eCommerce.TestUtils;
import com.example.eCommerce.model.Item;
import com.example.eCommerce.model.UserOrder;
import com.example.eCommerce.model.*;
import com.example.eCommerce.repositories.OrderRepository;
import com.example.eCommerce.repositories.UserRepository;
import org.junit.*;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class OrderControllerTest {

    private static OrderController orderController;

    private static UserRepository userRepository = mock(UserRepository.class);
    private static OrderRepository orderRepository = mock(OrderRepository.class);


    @BeforeClass
    public static void init()
    {
        orderController = new OrderController();
        TestUtils.injectObject(orderController, "userRepository", userRepository);
        TestUtils.injectObject(orderController, "orderRepository", orderRepository);
    }

    @Before
    public void setup()
    {
        Item item = new Item();
        item.setId(1L);
        item.setName("Round Widget");
        item.setPrice(BigDecimal.valueOf(2.99));
        item.setDescription("A widget that is round");
        List<Item> items = Collections.singletonList(item);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0L);
        user.setUsername("chehab");
        user.setPassword("testPassword");

        cart.setId(0L);
        cart.setUser(user);
        cart.setItems(items);
        cart.setTotal(BigDecimal.valueOf(2.99));

        user.setCart(cart);

        when(userRepository.findByUsername("chehab")).thenReturn(user);
        when(userRepository.findByUsername("wrong")).thenReturn(null);
    }

    @Test
    public void submit_order()
    {
        ResponseEntity<UserOrder> response = orderController.submit("chehab");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void submit_invalid_order()
    {
        ResponseEntity<UserOrder> response = orderController.submit("wrong");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    public void get_orders()
    {
        ResponseEntity<List<UserOrder>> response = orderController.getOrdersForUser("chehab");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

}