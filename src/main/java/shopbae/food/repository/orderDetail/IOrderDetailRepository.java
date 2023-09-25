package shopbae.food.repository.orderDetail;

import java.util.List;

import shopbae.food.model.OrderDetail;
import shopbae.food.service.IGeneral;

public interface IOrderDetailRepository extends IGeneral<OrderDetail> {
	/**
	 * This method use find by id of order
	 * 
	 * @param id
	 * @return List OrderDetail
	 */
	List<OrderDetail> findByOrder(Long id);
}
