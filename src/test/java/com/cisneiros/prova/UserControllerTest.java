package com.cisneiros.prova;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import com.fasterxml.jackson.databind.ObjectMapper;

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
		when(userService.save(any(User.class))).thenReturn(user);
				
		ObjectMapper objectMapper = new ObjectMapper();
		String userJSON = objectMapper.writeValueAsString(user);
		
		ResultActions result = mockMvc.perform(post("/utilizadores")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(userJSON));
		
		result.andExpect(status().isCreated())
	        .andExpect(jsonPath("$.username").value("vcisneiros"))
	        .andExpect(jsonPath("$.password").value("123"))
			.andExpect(jsonPath("$.name").value("Victor"));
		
	}
	
	@Test
	void getUser() throws Exception {
		User user = new User("vcisneiros","123","Victor");
		user.setId(1L);
		when(userService.findUserById(any(Long.class))).thenReturn(user);
						
		mockMvc.perform(MockMvcRequestBuilders.get("/utilizadores/1")
			.contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value("1"))
			.andExpect(jsonPath("$.username").value("vcisneiros"))
			.andExpect(jsonPath("$.password").value("123"))
			.andExpect(jsonPath("$.name").value("Victor"));
		
	}
	
	@Test
	void updateUser() throws Exception {
		User user = new User("vcisneiros","123","Victor");
		user.setId(1L);
		when(userService.findUserById(any(Long.class))).thenReturn(user);
		
		User changedUser = new User("vcisneiros","123456","Cisneiros");
		changedUser.setId(1L);
		
		when(userService.update(any(User.class))).thenReturn(changedUser);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String userJSON = objectMapper.writeValueAsString(changedUser);
		
		ResultActions result = mockMvc.perform(put("/utilizadores")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(userJSON));
		
		result.andExpect(status().isOk())
			.andExpect(jsonPath("$.username").value("vcisneiros"))
			.andExpect(jsonPath("$.name").value("Cisneiros"))
			.andExpect(jsonPath("$.password").value("123456"))
			.andExpect(jsonPath("$.id").value("1"));
		
		changedUser.setUsername("bad-user");
		String badUserJSON = objectMapper.writeValueAsString(changedUser);
		
		ResultActions result2 = mockMvc.perform(put("/utilizadores")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(badUserJSON));
		
		result2.andExpect(status().isBadRequest());
		
	}
	
	@Test
	void deleteUser() throws Exception {		
		User user = new User("vcisneiros","123","Victor");
		user.setId(1L);
		
		ObjectMapper objectMapper = new ObjectMapper();
		String userJSON = objectMapper.writeValueAsString(user);
		
		mockMvc.perform(delete("/utilizadores")
		        .contentType(MediaType.APPLICATION_JSON)
		        .content(userJSON))
				.andExpect(status().isOk());
	}

}
