package shopbae.food.service.order;

import java.io.Serializable;
import java.util.List;

import shopbae.food.model.Order;
import shopbae.food.service.IGeneral;

public interface IOrderService extends IGeneral<Order> {
    /**
     * This method use find by id of user and id of merchant
     * 
     * @param userId
     * @param merId
     * @return List order
     */
    List<Order> findByAppUserAndMer(Long userId);

    /**
     * This method use save order
     * 
     * @param t Order
     * @return id Serializable
     */
    List<Order> findByFlagAndStatus(String status);

    /**
     * This method use find by true flag and status
     * 
     * @param status
     * @return List order
     */
    Serializable savee(Order t);

    /**
     * This metohd send mes to user when have merchant received order
     * 
     * @param t
     * @param userId
     */
    void send(Order t, Long userId);
}
