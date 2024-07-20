package exam.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

/**
 * 
 * <p>
 * The Data transfer object for Product in REST Controller and Service
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
public class ProductDto {

	private Long productId;

	@NotNull
	@NotEmpty
	private String productName;

	@NotNull
	@NotEmpty
	private String productDescription;

	@NotNull
	@NotEmpty
	private String productType = ProductType.NONE.getValue();

	@NotNull
	@NotEmpty
	private Integer productQuantity;

	@NotNull
	@NotEmpty
	private BigDecimal unitPrice;

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public Integer getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

}
