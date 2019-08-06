package com.abevilacqua.techstore;

import com.abevilacqua.techstore.model.Product;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

final class TestUtils {

    static MockMvc createMockMvc(Object controller) {
        return standaloneSetup(controller).build();
    }

    static Product createProduct() {
        return Product.builder()
                .name("Notebook ABevialcqua")
                .description("My new notebook")
                .price(666.0)
                .build();
    }

    static String mapToJson(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
