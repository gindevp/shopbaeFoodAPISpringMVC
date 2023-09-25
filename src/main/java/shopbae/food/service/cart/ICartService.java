package shopbae.food.service.cart;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import shopbae.food.model.Cart;
import shopbae.food.model.Product;
import shopbae.food.service.IGeneral;

public interface ICartService extends IGeneral<Cart> {
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
//	    @Query(value = "select * from cart where product_id = ?1 and user_id= ?2", nativeQuery = true)
	Cart findByProductIdAndUserId(Long product_id, Long user_id);

	/**
	 * This method use set id of product and id of cart in table productCart
	 * 
	 * @param cart_id
	 * @param product_id
	 */
//	    @Query(value = "insert into product_cart(cart_id,product_id) values (?1,?2)", nativeQuery = true)
	void setProductCart(Long cart_id, Long product_id);

	/**
	 * This method use update quantity
	 * 
	 * @param quantity
	 * @param cart_id
	 */
//	    @Query(value = "update cart set quantity = ?1 where id=?2", nativeQuery = true)
	void updateQuantity(int quantity, Long cart_id);

	/**
	 * This method use delete by id of user
	 * 
	 * @param id
	 */
	void deletesByUser(Long id);

	/**
	 * this method add to cart
	 * 
	 * @param cart
	 */
	void addToCart(Cart cart);

	/**
	 * This method use find by id of product and true Flag
	 * 
	 * @param id
	 * @return Cart
	 */
	Cart findByProductAndFlag(Long id);

	/**
	 * Dùng để thêm 1 rỏ hàng chứa 1 loại sản phẩm với số lượng tùy biến
	 * 
	 * @param productid là chỉ số của sản phẩm cần thêm
	 * @param userid    Long là id của người mua
	 * 
	 */
	void addCart(Long productid, Long userid);

	/**
	 * 
	 * Dùng để chuyển sang trang cart detail vào load các order và danh sách cart
	 * theo userId và merchanntId theo session
	 * 
	 * @param userId : id của user
	 * 
	 */
	void cartDetail(Model model, Long userId, HttpSession session);

	/**
	 * Dùng để đặt hàng và nạp vào orderDetail các cart rồi xóa cart cũ. Set trạng
	 * thái cho order là pending
	 * 
	 * @param userId     chỉ số người mua
	 * @param merchantId chỉ số người bán
	 * @param note       ghi chú của người mua
	 * @param address    địa chỉ của người mua
	 * @param sum        tổng tiền của order
	 */
	List<Product> ordeing(Long userId, Long merchantId, String note, String address, double sum);
	List<Cart> findAllByProduct(Long id);
}
