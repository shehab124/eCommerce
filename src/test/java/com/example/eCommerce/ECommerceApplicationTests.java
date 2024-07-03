package com.example.eCommerce;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ECommerceApplicationTests {

	@Before
	public void init()
	{
		System.out.println("runs before each method");
	}

	@BeforeClass
	public static void setup()
	{
		System.out.println("runs before each class");
	}

	@Test
	public void testing()
	{

	}

	@After
	public void initEnd()
	{
		System.out.println("runs after each method");
	}

	@AfterClass
	public static void tearDown()
	{
		System.out.println("runs after each class");
	}
	@Test
	void contextLoads() {
	}

}
