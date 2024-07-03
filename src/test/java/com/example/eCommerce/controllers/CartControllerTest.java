package com.example.eCommerce.controllers;

import com.example.eCommerce.TestUtils;
import com.example.eCommerce.model.*;
import com.example.eCommerce.model.requests.ModifyCartRequest;
import com.example.eCommerce.repositories.*;
import com.example.eCommerce.repositories.ItemRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {

    private static CartController cartController;

    private static UserRepository userRepository = mock(UserRepository.class);

    private static CartRepository cartRepository = mock(CartRepository.class);

    private static ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeClass
    public static void init()
    {
        cartController = new CartController();
        TestUtils.injectObject(cartController, "userRepository", userRepository);
        TestUtils.injectObject(cartController, "cartRepository", cartRepository);
        TestUtils.injectObject(cartController, "itemRepository", itemRepository);
    }

    @Before
    public void setup()
    {
        // item setup
        Item item = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        item.setDescription("A widget that is square");
        item.setPrice(BigDecimal.valueOf(1.99));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        // cart setup
        Cart cart = new Cart();

        // User setup
        User user = new User();
        user.setId(1L);
        user.setUsername("chehab");
        user.setPassword("testPassword");
        user.setCart(cart);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        cart.setItems(List.of(item));
        cart.setId(1L);
        cart.setUser(user);
        cart.setTotal(BigDecimal.valueOf(1.99));
        when(cartRepository.save(cart)).thenReturn(cart);

    }

    @Test
    public void add_to_cart()
    {
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("chehab");
        modifyCartRequest.setQuantity(1);

        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    public void remove_from_cart()
    {
        // add item
        ModifyCartRequest addToCartRequest = new ModifyCartRequest();
        addToCartRequest.setItemId(1L);
        addToCartRequest.setUsername("chehab");
        addToCartRequest.setQuantity(1);
        cartController.addTocart(addToCartRequest);

        // remove item
        ModifyCartRequest removeFromCartRequest = new ModifyCartRequest();
        removeFromCartRequest.setUsername("chehab");
        removeFromCartRequest.setItemId(1L);
        removeFromCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(removeFromCartRequest);

        // assert
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }


}
