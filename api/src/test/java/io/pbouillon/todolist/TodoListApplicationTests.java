package io.pbouillon.todolist;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoListApplicationTests {

	/**
	 * The HTTP test client calling the controller's endpoints
	 */
	@Autowired
	private MockMvc mockMvc;

	@Test
	void contextLoads() {
	}

	@Test
	void metricsAreResolved() throws Exception {
		mockMvc.perform(get("/actuator"))
				.andExpectAll(
						status().isOk(),
						jsonPath("$._links.health").exists(),
						jsonPath("$._links.info").exists()
				);
	}

}
