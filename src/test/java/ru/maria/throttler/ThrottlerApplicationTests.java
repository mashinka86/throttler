package ru.maria.throttler;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import ru.maria.throttler.config.ThrottlerProperties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class ThrottlerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ThrottlerProperties throttlerProperties;

	@ParameterizedTest
	@ValueSource(strings = {"192.168.0.1", "192.168.0.2", "192.168.0.3", "192.168.0.4", "192.168.0.5", "192.168.0.6"})
	void testThrottle(String address) throws Exception {
		RequestPostProcessor postProcessor1 = request -> {
			request.setRemoteAddr(address);
			return request;
		};

		for (int i = 0; i < throttlerProperties.getLimit(); i++) {
			mockMvc.perform(get("/test")
							.with(postProcessor1))
					.andExpect(status().isOk());
		}
		mockMvc.perform(get("/test")
						.with(postProcessor1))
				.andExpect(status().is5xxServerError());

		Thread.sleep(throttlerProperties.getTime() - 1000);
		mockMvc.perform(get("/test")
						.with(postProcessor1))
				.andExpect(status().is5xxServerError());
		Thread.sleep(throttlerProperties.getTime());
		mockMvc.perform(get("/test")
						.with(postProcessor1))
				.andExpect(status().isOk());

	}

}