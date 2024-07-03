package com.example.eCommerce.controllers;

import com.example.eCommerce.TestUtils;
import com.example.eCommerce.model.Item;
import com.example.eCommerce.repositories.ItemRepository;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {

    private static ItemController itemController;

    private static ItemRepository itemRepository = mock(ItemRepository.class);

    @BeforeClass
    public static void init()
    {
        itemController = new ItemController();
        TestUtils.injectObject(itemController, "itemRepository", itemRepository);
    }

    @Before
    public void setup()
    {
        Item item = new Item();
        item.setId(1L);
        item.setName("Square Widget");
        item.setDescription("A widget that is square");

        when(itemRepository.findAll()).thenReturn(List.of(item));
        when(itemRepository.findByName("Square Widget")).thenReturn(List.of(item));
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
    }

    @Test
    public void get_all_items()
    {
        ResponseEntity<List<Item>> response = itemController.getItems();
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void get_item_by_id()
    {
        ResponseEntity<Item> response = itemController.getItemById(1L);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    public void get_item_by_name()
    {
        ResponseEntity<List<Item>> response = itemController.getItemsByName("Square Widget");
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatusCode.valueOf(200), response.getStatusCode());
        Assertions.assertNotNull(response.getBody());
    }

}
