package exam.product.dto;

/**
 * 
 * <p>
 * Enumeration of values representing the type of Product <br>
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
public enum ProductType {

	FOOD("Food"), SPORTS("Sports"), HOUSEHOLD("Household"), MUSIC("Music"), ELECTRONIC("Electronic"),
	APPLIANCE("Appliance"), NONE("None");

	private String name;

	ProductType(String type) {
		this.name = type;
	}

	public String getValue() {
		return this.name;
	}

	ProductType fromString(String prettyName) {
		for (ProductType productType : values()) {
			if (productType.name.equals(prettyName)) {
				return productType;
			}
		}
		return null;
	}
}
