package com.CMPE272.restservice;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

/**
 * Created by Sarinder Virk
 */
@SpringBootTest
@AutoConfigureMockMvc
public class TweetControllerTest {

	@Autowired
	private MockMvc mvc;

	String bad_id = "1110458664540659720";
	String good_id = "1440458664540659720";

	@Test
	public String postTweet() throws Exception {
		String request = "unit test " + new Date();
		String response = mvc.perform(MockMvcRequestBuilders
			.post("/tweet?text=" + request)
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		System.out.println("response="+ response);
		// response should contain json with substring like ..."id":"1440857187002171399"...
		Matcher m = Pattern.compile("\"id\":\"(\\d+)\"").matcher(response);
		String createdId = m.find() ? m.group(1) : null;
		assertNotNull(createdId);
		return createdId;
	}

	@Test
	public void readTweetMissingId() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tweet").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void readTweetIdNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tweet?id=" + bad_id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void readTweet() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/tweet?id=" + good_id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString()
				.contains("{\"id\":\"" + good_id + "\"}");
	}

	@Test
	public void deleteTweet() throws Exception {
		String createdId = postTweet();
		System.out.println("createdId="+ createdId);
		mvc.perform(MockMvcRequestBuilders.delete("/tweet?id=" + createdId).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

	@Test
	public void deleteTweetNotFound() throws Exception {
		mvc.perform(MockMvcRequestBuilders.delete("/tweet?id="+ bad_id).accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	public void listTimelineTest() throws Exception {
		String good_id = "1440458664540659720";
		mvc.perform(MockMvcRequestBuilders.get("/timeline").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString()
				.contains("{\"id\":\"" + good_id + "\"}");
	}

}