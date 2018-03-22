package techit.rest.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Test(groups = "LoginTest")
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class LoginTest extends AbstractTransactionalTestNGSpringContextTests {

	@Autowired
	private WebApplicationContext wac;

	private MockMvc mockMvc;

	private ObjectMapper objectMapper;

	@Configuration
	@EnableWebMvc
	static class ContextConfiguration {

	}

	@BeforeClass
	private void setup() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		objectMapper = new ObjectMapper();
	}

	@Test
	public void testSuccess() throws Exception {

		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", "techit");
		credentials.put("password", "abcd");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(credentials));

		String jwt = mockMvc.perform(builder)
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn()
				.getResponse()
				.getContentAsString();

		Claims claims = Jwts.parser()
				.setSigningKey("secret")
				.parseClaimsJws(jwt.substring(7))
				.getBody();
		
		assert jwt.startsWith("Bearer ") && claims.get("username").equals(credentials.get("username"));
	}
	
	@Test
	public void testFailure() throws Exception {

		Map<String, String> credentials = new HashMap<>();
		credentials.put("username", "techit");
		credentials.put("password", "wrong password");

		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders
				.post("/login")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(credentials));

		mockMvc.perform(builder).andExpect(MockMvcResultMatchers.status().isUnauthorized());
	
	}

}