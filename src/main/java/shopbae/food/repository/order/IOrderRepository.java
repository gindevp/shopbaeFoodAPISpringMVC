package shopbae.food.repository.order;

import java.io.Serializable;
import java.util.List;

import shopbae.food.model.Order;
import shopbae.food.service.IGeneral;

public interface IOrderRepository extends IGeneral<Order> {
	/**
	 * This method use find by id of user and id of merchant
	 * 
	 * @param userId
	 * @param merId
	 * @return List order
	 */
	List<Order> findByAppUserAndMer(Long userId, Long merId);

	/**
	 * This method use save order
	 * 
	 * @param t Order
	 * @return id Serializable
	 */
	Serializable savee(Order t);

	/**
	 * This method use find by true flag and status
	 * 
	 * @param status
	 * @return List order
	 */
	List<Order> findByFlagAndStatus(String status);
}
