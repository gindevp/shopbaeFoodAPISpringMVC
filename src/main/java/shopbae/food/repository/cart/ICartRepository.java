package shopbae.food.repository.cart;

import java.util.List;

import shopbae.food.model.Cart;
import shopbae.food.service.IGeneral;

public interface ICartRepository extends IGeneral<Cart> {
	/**
	 * This method use find all merchant by id of user
	 * 
	 * @param id AppUser
	 * @return List Cart
	 */
	List<Cart> findAllByUser(Long id);

	/**
	 * This method use find merchant by product
	 * 
	 * @param id Product
	 * @return Cart
	 */
	Cart findByProduct(Long id);

	/**
	 * This method use find by id of product and id of user
	 * 
	 * @param product_id
	 * @param user_id
	 * @return Cart
	 */
	Cart findByProductIdAndUserId(Long product_id, Long user_id);

	/**
	 * This method use set id of product and id of cart in table productCart
	 * 
	 * @param cart_id
	 * @param product_id
	 */
	void setProductCart(Long cart_id, Long product_id);

	/**
	 * This method use update quantity
	 * 
	 * @param quantity
	 * @param cart_id
	 */
	void updateQuantity(int quantity, Long cart_id);

	/**
	 * This method use delete by id of user
	 * 
	 * @param id
	 */
	void deletesByUser(Long id);

	/**
	 * This method use find by id of product and true Flag
	 * 
	 * @param id
	 * @return Cart
	 */
	Cart findByProductAndFlag(Long id);
}
