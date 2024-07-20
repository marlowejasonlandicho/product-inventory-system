package exam.product.repository;

import org.springframework.data.repository.CrudRepository;

import exam.product.entity.Product;

/**
 * 
 * <p>
 * Data Repository interface for interacting with Database
 * </p>
 * 
 * 
 * @author Marlowe Landicho
 *
 * @version
 * 
 */
public interface ProductInventoryRepository extends CrudRepository<Product, Long>{

}
