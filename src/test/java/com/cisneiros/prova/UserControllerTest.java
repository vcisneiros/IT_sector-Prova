package com.cisneiros.prova;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@Test
	void listAllUsers() throws Exception {
		List<User> userList = new ArrayList<User>();
		userList.add(new User("vcisneiros","123","Victor"));
		when(userService.findAll()).thenReturn(userList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/utilizadores")
				.contentType(MediaType.APPLICATION_JSON)
			).andExpect(jsonPath("$", hasSize(1))).andDo(print());
		
	}
	
	@Test
	void createUser() throws Exception {
		User user = new User("vcisneiros","123","Victor");
				
		ObjectMapper objectMapper = new ObjectMapper();
		String userJSON = objectMapper.writeValueAsString(user);
		
		ResultActions result = mockMvc.perform(post("/user")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(userJSON));
		
		result.andExpect(status().isCreated())
        .andExpect(jsonPath("$.username").value("vcisneiros"))
        .andExpect(jsonPath("$.password").value("123"))
		.andExpect(jsonPath("$.name").value("Victor"));
		
		
	}

}
