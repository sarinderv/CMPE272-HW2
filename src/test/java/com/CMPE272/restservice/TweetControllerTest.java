package com.CMPE272.restservice;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void readTweetMissingId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tweet").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void readTweetIdNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tweet?id=1110458664540659720").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void readTweet() throws Exception {
		String id = "1440458664540659720";
		mvc.perform(MockMvcRequestBuilders.get("/tweet?id="+ id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString()
				.contains("{\"id\":\"" + id + "\"}");
	}
}