package shopbae.food.service.product;

import java.util.List;

import shopbae.food.model.Product;
import shopbae.food.service.IGeneral;

public interface IProductService extends IGeneral<Product> {
	/**
	 * This method use find by name
	 * 
	 * @param name
	 * @return product
	 */
	Product findByName(String name);

	/**
	 * This method use find all by true deleflag and id of merchant
	 * 
	 * @param id
	 * @return
	 */
	List<Product> getAllByDeleteFlagTrueAndMerchant(Long id);

	/**
	 * This method use find all by true delefalg and id of mer and name containing
	 * 
	 * @param id
	 * @param name
	 * @return
	 */
	List<Product> fAllByDeleFlagTAndMerAndNameContai(Long id, String name);
}
