package exam.product.mapper;

import org.mapstruct.Mapper;

import exam.product.dto.ProductDto;
import exam.product.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductDto toDto(Product product);

	Product toEntity(ProductDto productDto);
}
