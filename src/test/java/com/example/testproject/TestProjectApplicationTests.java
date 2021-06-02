package com.example.testproject;

import com.example.testproject.controller.ImageController;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

//@RunWith(SpringRunner.class)
@SpringBootTest
class TestProjectApplicationTests {
	@Autowired
	private ImageController controller;

	@Test
	void contextLoads() {
	}

}
