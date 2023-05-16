package be.pxl.paj.testing.rest;

import be.pxl.paj.testing.service.BeerExpert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BeerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private WebApplicationContext context;

	@MockBean
	private BeerExpert beerExpert;

	@BeforeEach
	void setup() {
		mockMvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(SecurityMockMvcConfigurers.springSecurity())
				.build();
	}

	@Test
	@WithMockUser(username = "test") //@WithMockUser creates a user which is authenticated already.
	void testByColor() throws Exception {

		when(beerExpert.getBrands("brown")).thenReturn(Arrays.asList("Duvel", "Rodenbach"));

		mockMvc.perform(MockMvcRequestBuilders.get("/beers?color=brown")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string("[\"Duvel\",\"Rodenbach\"]"));

		verify(beerExpert).getBrands("brown");
	}

	@Test
	@WithMockUser(username = "test")
	void testByInvalidColor() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.get("/beers?color=green")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotAcceptable());

		verifyNoInteractions(beerExpert);
	}

	@Test
	void testUnknownUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/beers?color=green")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isForbidden());
	}


}
