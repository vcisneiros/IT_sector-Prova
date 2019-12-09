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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(SpringExtension.class)
@WebMvcTest
class UserControllerTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean
	private UserService utilizadorService;
	
	@Test
	void listarTodosUtilizadores() throws Exception {
		List<User> userList = new ArrayList<User>();
		userList.add(new User("vcisneiros","123","Victor"));
		when(utilizadorService.findAll()).thenReturn(userList);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/utilizadores")
				.contentType(MediaType.APPLICATION_JSON)
			).andExpect(jsonPath("$", hasSize(1))).andDo(print());
		
	}

}
