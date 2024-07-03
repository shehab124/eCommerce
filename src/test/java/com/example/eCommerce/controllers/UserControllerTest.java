package com.example.eCommerce.controllers;

import com.example.eCommerce.TestUtils;
import com.example.eCommerce.model.Cart;
import com.example.eCommerce.model.User;
import com.example.eCommerce.model.requests.CreateUserRequest;
import com.example.eCommerce.repositories.CartRepository;
import com.example.eCommerce.repositories.UserRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);

    @Before
    public void setUp() {
        userController = new UserController();
        TestUtils.injectObject(userController, "userRepository", userRepository);
        TestUtils.injectObject(userController, "cartRepository", cartRepository);
        TestUtils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);

        User user = new User();
        Cart cart = new Cart();
        user.setId(0L);
        user.setUsername("test");
        user.setPassword("testPassword");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.of(user));
        when(userRepository.findByUsername("someone")).thenReturn(null);

    }

    @Test
    public void create_user_happy_path() {
        when(bCryptPasswordEncoder.encode("testPassword")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("testPassword");
        request.setConfirmPassword("testPassword");
        final ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        User u = response.getBody();
        assertNotNull(u);
        Assert.assertEquals("test", u.getUsername());
        Assert.assertEquals("thisIsHashed", u.getPassword());

    }

    @Test
    public void create_user_weak_pass() {
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("weak");
        request.setConfirmPassword("weak");

        ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }

    @Test
    public void create_user_password_confirm_not_equal() {
        CreateUserRequest request= new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("password1");
        request.setConfirmPassword("password2");
        final ResponseEntity<User> response = userController.createUser(request);
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(400), response.getStatusCode());
    }

    @Test
    public void find_user_by_name() {
        final ResponseEntity<User> response = userController.findByUserName("test");
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        User u = response.getBody();
        assertNotNull(u);
        Assert.assertEquals("test", u.getUsername());
    }

    @Test
    public void find_user_by_name_not_found() {
        final ResponseEntity<User> response = userController.findByUserName("notfound");
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

    @Test
    public void find_user_by_id_happy_path() {
        final ResponseEntity<User> response = userController.findById(0L);
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        User u = response.getBody();
        assertNotNull(u);
        Assert.assertEquals(Optional.of(0L).get(), u.getId());;
    }

    @Test
    public void find_user_by_id_not_found() {
        final ResponseEntity<User> response = userController.findById(1L);
        assertNotNull(response);
        Assert.assertEquals(HttpStatusCode.valueOf(404), response.getStatusCode());
    }

}










//import com.example.eCommerce.TestUtils;
//import com.example.eCommerce.model.User;
//import com.example.eCommerce.model.requests.CreateUserRequest;
//import com.example.eCommerce.repositories.CartRepository;
//import com.example.eCommerce.repositories.UserRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.http.HttpStatusCode;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import java.util.Optional;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//import static org.mockito.Mockito.mock;
//
//public class UserControllerTest {
//
//    private UserController userController;
//
//    private UserRepository userRepository = mock(UserRepository.class);
//
//    private CartRepository cartRepository = mock(CartRepository.class);
//
//    private BCryptPasswordEncoder bCryptPasswordEncoder = mock(BCryptPasswordEncoder.class);
//
//    @Before
//    public void setUp()
//    {
//        userController = new UserController();
//        TestUtils.injectObject(userController, "userRepository", userRepository);
//        TestUtils.injectObject(userController, "cartRepository", cartRepository);
//        TestUtils.injectObject(userController, "bCryptPasswordEncoder", bCryptPasswordEncoder);
//    }
//
//    @Test
//    public void create_user_happy_path()
//    {
//
//        CreateUserRequest createUserRequest = new CreateUserRequest();
//        createUserRequest.setUsername("chehab");
//        createUserRequest.setPassword("12345678");
//        createUserRequest.setConfirmPassword("12345678");
//
//        ResponseEntity<User> response = userController.createUser(createUserRequest);
//        assertNotNull(response);
//        assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
//
//        User user = response.getBody();
//        assertNotNull(user);
//        assertEquals(Optional.of(0), user.getId());
//        assertEquals("chehab", user.getUsername());
//        assertEquals("12345678", user.getPassword());
//    }
//
//}
