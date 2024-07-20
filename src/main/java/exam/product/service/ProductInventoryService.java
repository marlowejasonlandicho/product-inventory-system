package exam.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exam.product.dto.ProductDto;
import exam.product.entity.Product;
import exam.product.exception.ProductNotFoundException;
import exam.product.mapper.ProductMapper;
import exam.product.repository.ProductInventoryRepository;

/**
 * 
 * <p>
 * Service representing the Business logic for the Product Inventory Service
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
@Service
public class ProductInventoryService {

	@Autowired
	private ProductInventoryRepository productRepository;

	
	@Autowired
	private ProductMapper productMapper;
	
	public ProductDto save(ProductDto productDto) {
		if (Objects.nonNull(productDto)) {
			Product product = productMapper.toEntity(productDto);
			Product savedProductItem = this.productRepository.save(product);
			if (savedProductItem != null) {
			    return productMapper.toDto(savedProductItem);
			}
		}
		return null;
	}

	public ProductDto getProduct(Long productId) throws ProductNotFoundException {
		Optional<Product> productOptional = productRepository.findById(productId);
		Product product = productOptional.orElse(null);
		if (product != null) {
			return productMapper.toDto(product);
		} else {
			throw new ProductNotFoundException();
		}
	}

	public List<ProductDto> getAllProduct() {
		Iterable<Product> productIterator = productRepository.findAll();
		List<ProductDto> productList = new ArrayList<>();
		productIterator.forEach(product -> {
			ProductDto productDto = productMapper.toDto(product);
			productList.add(productDto);
		});

		return productList;
	}

	public ProductDto update(Long productId, ProductDto productDto) {
		Optional<Product> productOptional = productRepository.findById(productId);
		Product product = productOptional.orElse(null);
		
		if (product != null) {
			if (productDto.getProductName() != null) {
				product.setProductName(productDto.getProductName());
			}
			if (productDto.getProductDescription() != null) {
				product.setProductDescription(productDto.getProductDescription());
			}
			if (productDto.getProductType() != null) {
				product.setProductType(productDto.getProductType());
			}
			if (productDto.getProductQuantity() != null) {
				product.setProductQuantity(productDto.getProductQuantity());
			}
			if (productDto.getUnitPrice() != null) {
				product.setUnitPrice(productDto.getUnitPrice());
			}

			Product updatedProduct = this.productRepository.save(product);
			if (updatedProduct != null) {
				return productMapper.toDto(updatedProduct);
			}
		}

		return null;
	}

	public boolean delete(Long productId) {
		Optional<Product> productOptional = productRepository.findById(productId);
		Product product = productOptional.orElse(null);
		if (product != null) {
			this.productRepository.deleteById(productId);
			return true;
		}

		return false;
	}
}
