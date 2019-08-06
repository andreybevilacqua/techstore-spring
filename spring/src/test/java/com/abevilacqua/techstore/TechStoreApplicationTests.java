package com.abevilacqua.techstore;

import com.abevilacqua.techstore.controller.StoreController;
import com.abevilacqua.techstore.model.Product;
import com.abevilacqua.techstore.repository.ProductRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static com.abevilacqua.techstore.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TechStoreApplicationTests {

	private MockMvc mockMvc;

	private Product product = createProduct();

	@Mock
	private ProductRepo productRepo;

	@BeforeEach
	void setup() {
		StoreController storeController = new StoreController(productRepo);
		this.mockMvc = createMockMvc(storeController);
	}

	@Test
	@DisplayName("Should get list of printers")
	void shouldGetListOfPrinters() throws Exception {
		Mockito.when(productRepo.findAll()).thenReturn(Arrays.asList(product, product));
		mockMvc.perform(get("/products")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.[*].id").exists())
				.andExpect(jsonPath("$.[*].name").exists())
				.andExpect(jsonPath("$.[*].description").exists())
				.andExpect(jsonPath("$.[*].price").exists());
	}

	@Test
	@DisplayName("Should find a product by ID")
	void shouldFindAProductByID() throws Exception {
		Mockito.when(productRepo.findById(1L)).thenReturn(Optional.of((product)));
		mockMvc.perform(get("/products/1")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andDo(print())
				.andExpect(jsonPath("$.id").exists())
				.andExpect(jsonPath("$.name").exists())
				.andExpect(jsonPath("$.description").exists())
				.andExpect(jsonPath("$.price").exists());
	}

	@Test
	@DisplayName("Should create a product")
	void shouldCreateAProduct() throws Exception {
		mockMvc.perform(post("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(createProduct())))
				.andExpect(status().isCreated());
	}

	@Test
	@DisplayName("Should update product")
	void shouldUpdateProduct() throws Exception {
		mockMvc.perform(put("/products")
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapToJson(createProduct())))
				.andExpect(status().isOk());
	}

	@Test
	@DisplayName("Should delete product")
	void shouldDeleteProduct() throws Exception {
		Mockito.when(productRepo.findById(6L)).thenReturn(Optional.of(product));
		mockMvc.perform(delete("/products/6")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNoContent());
	}

}
