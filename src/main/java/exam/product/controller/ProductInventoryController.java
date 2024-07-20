package exam.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exam.product.dto.ApiResponse;
import exam.product.dto.ApiStatus;
import exam.product.dto.ProductDto;
import exam.product.exception.ProductNotFoundException;
import exam.product.service.ProductInventoryService;

/**
 * 
 * <p>
 * REST API for Product Inventory System
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
@RestController
@RequestMapping(path = { "/v1/product" }, produces = { "application/json" })
public class ProductInventoryController {

	@Autowired
	private ProductInventoryService productService;

	/**
	 *
	 * <p>
	 * Create a Product
	 * </p>
	 *
	 * @param product The Product to be created
	 * 
	 * @return the created Product in database
	 * 
	 */
	@PostMapping
	public ResponseEntity<ApiResponse<ProductDto>> createProduct(@RequestBody ProductDto product) {
		ProductDto createdProduct = this.productService.save(product);
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();

		if (createdProduct != null) {
			apiResponse.setData(createdProduct);
			apiResponse.setApiStatus(ApiStatus.CREATED);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Product Created");
			return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
		} else {
			apiResponse.setApiStatus(ApiStatus.CREATE_FAILED);
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Product Creation Failed");
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 *
	 * <p>
	 * Retrieve a Product based on Product Id
	 * </p>
	 *
	 * @param productId the Product Service Id from database
	 * 
	 * @return the Product in database
	 * 
	 */
	@GetMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> getProduct(@PathVariable Long productId) throws ProductNotFoundException {
		ProductDto product = this.productService.getProduct(productId);
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();

		if (product != null) {
			apiResponse.setData(product);
			apiResponse.setApiStatus(ApiStatus.FOUND);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Product Found");
			return new ResponseEntity<>(apiResponse, HttpStatus.OK);
		} else {

			apiResponse.setApiStatus(ApiStatus.NOT_FOUND);
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Product Not Avaialble");
			return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * <p>
	 * Retrieve all Product
	 * </p>
	 *
	 * 
	 * @return all Product Service items in database
	 * 
	 */
	@GetMapping
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProduct() {
		List<ProductDto> products = this.productService.getAllProduct();
		ApiResponse<List<ProductDto>> apiResponse = new ApiResponse<>();
		if (!products.isEmpty()) {
			apiResponse.setData(products);
			apiResponse.setApiStatus(ApiStatus.FOUND);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Product Found");
			return new ResponseEntity<ApiResponse<List<ProductDto>>>(apiResponse, HttpStatus.OK);
		} else {
			apiResponse.setApiStatus(ApiStatus.NOT_FOUND);
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Product Not Found");
			return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * <p>
	 * Update a Product
	 * </p>
	 *
	 * @param productId the Product Id from database
	 * @param product   the Product request body containing data to be updated in
	 *                  database
	 * 
	 * @return the updated Product in database
	 * 
	 */
	@PutMapping("/{productId}")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable Long productId, @RequestBody ProductDto productDto) {
		ProductDto updatedProduct = this.productService.update(productId, productDto);
		ApiResponse<ProductDto> apiResponse = new ApiResponse<>();

		if (updatedProduct != null) {
			apiResponse.setData(updatedProduct);
			apiResponse.setApiStatus(ApiStatus.UPDATED);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Product Updated");
			return new ResponseEntity<ApiResponse<ProductDto>>(apiResponse, HttpStatus.OK);
		} else {
			apiResponse.setApiStatus(ApiStatus.UPDATE_FAILED);
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Product Update Failed");
			return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}


	/**
	 *
	 * <p>
	 * Deletes a Product based given Product Id
	 * </p>
	 *
	 * @param productId the Product Id from database
	 * 
	 * @return a boolean value indicating a successful deletion of the Product in database
	 * 
	 */
	@DeleteMapping("/{productId}")
	public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId) {
		boolean isProductDeleted = this.productService.delete(productId);
		ApiResponse apiResponse = new ApiResponse<>();

		if (isProductDeleted) {
			apiResponse.setData(isProductDeleted);
			apiResponse.setApiStatus(ApiStatus.DELETED);
			apiResponse.setSuccess(true);
			apiResponse.setMessage("Product Deleted");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
		} else {
			apiResponse.setApiStatus(ApiStatus.DELETE_FAILED);
			apiResponse.setSuccess(false);
			apiResponse.setMessage("Product Delete Failed");
			return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.BAD_REQUEST);
		}
	}

}
