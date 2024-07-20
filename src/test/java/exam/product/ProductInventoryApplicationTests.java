package exam.product;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.util.Objects;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import exam.product.dto.ProductDto;
import exam.product.dto.ProductType;
import exam.product.entity.Product;
import exam.product.repository.ProductInventoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProductInventoryApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductInventoryApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private ProductInventoryRepository productInventoryRepository;

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenCreateProduct_thenStatus200() throws Exception {
		mvc.perform(
				post("/v1/product")
						.content(createTestProductJSON("Product Name", "Product 1 Description", 1,
								BigDecimal.valueOf(100), ProductType.APPLIANCE))
						.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.data.productName", is("Product Name")))
				.andExpect(jsonPath("$.data.productType", is("Appliance")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void givenProductItemIs1_whenGetProduct_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(get("/v1/product/{productId}", savedProduct.getProductId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.productName", is("Product 1")))
				.andExpect(jsonPath("$.data.productType", is("Appliance")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void givenProductItems_whenGetProducts_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		Product anotherSavedProduct = createTestProduct("Product 2", "Product 2 Description", 2,
				BigDecimal.valueOf(200), ProductType.ELECTRONIC);

		mvc.perform(get("/v1/product").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data[0].productName", is("Product 1")))
				.andExpect(jsonPath("$.data[0].productType", is("Appliance")))
				.andExpect(jsonPath("$.data[1].productName", is("Product 2")))
				.andExpect(jsonPath("$.data[1].productType", is("Electronic")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void givenProductItemIs1_whenGetProductIsEmpty_thenStatus404() throws Exception {
		mvc.perform(get("/v1/product/100").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.apiStatus", is("NOT_FOUND"))).andExpect(jsonPath("$.success", is(false)));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductProduct_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON("New Product Name", null, 2, null, null))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.productName", is("New Product Name")))
				.andExpect(jsonPath("$.data.productQuantity", is(2)));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductProductName_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON("New Product Name", null, null, null, null))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.productName", is("New Product Name")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductDescription_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON(null, "New Product Description", null, null, null))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.productDescription", is("New Product Description")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductQuantity_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON(null, null, 3, null, null)).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data.productQuantity", is(3)));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductUnitPrice_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON(null, null, null, BigDecimal.valueOf(200.99), null))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.unitPrice", is(200.99)));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenUpdateProductType_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(put("/v1/product/{productId}", savedProduct.getProductId())
				.content(createTestProductJSON(null, null, null, null, ProductType.FOOD))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.data.productType", is("Food")));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenDeleteProduct_thenStatus200() throws Exception {
		Product savedProduct = createTestProduct("Product 1", "Product 1 Description", 1, BigDecimal.valueOf(100),
				ProductType.APPLIANCE);
		mvc.perform(
				delete("/v1/product/{productId}", savedProduct.getProductId()).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.data", is(true)))
				.andExpect(jsonPath("$.apiStatus", is("DELETED"))).andExpect(jsonPath("$.success", is(true)));
	}

	@Test
	@WithMockUser(username = "user", password = "password", roles = "USER")
	public void whenNoProductItems_thenStatus404() throws Exception {
		mvc.perform(get("/v1/product").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.apiStatus", is("NOT_FOUND"))).andExpect(jsonPath("$.success", is(false)));
	}

	private Product createTestProduct(String name, String description, Integer quantity, BigDecimal unitPrice,
			ProductType productType) {
		Product product = new Product();

		product.setProductName(name);
		product.setProductDescription(description);
		product.setProductQuantity(quantity);
		product.setUnitPrice(unitPrice);
		product.setProductType(Objects.nonNull(productType) ? productType.getValue() : null);

		Product createdProduct = this.productInventoryRepository.save(product);

		return createdProduct;
	}

	private String createTestProductJSON(String name, String description, Integer quantity, BigDecimal unitPrice,
			ProductType productType) {
		ObjectMapper objectMapper = new ObjectMapper();
		ProductDto productDto = new ProductDto();
		productDto.setProductName(name);
		productDto.setProductDescription(description);
		productDto.setProductQuantity(quantity);
		productDto.setUnitPrice(unitPrice);
		productDto.setProductType(Objects.nonNull(productType) ? productType.getValue() : null);

		try {
			return objectMapper.writeValueAsString(productDto);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return "";
	}

	@AfterEach
	private void teardown() {
		this.productInventoryRepository.deleteAll();
	}
}
