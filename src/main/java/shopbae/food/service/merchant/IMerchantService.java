package shopbae.food.service.merchant;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.ui.Model;

import shopbae.food.model.Merchant;
import shopbae.food.service.IGeneral;

public interface IMerchantService extends IGeneral<Merchant> {
	/**
	 * This method use find by name
	 * 
	 * @return a merchant
	 */
	Merchant findByName(String name);
	/**
	 * This method use find all by status
	 * 
	 * @return List merchant
	 */
	List<Merchant> getAllByMerchantStatus(String status);
	/**
	 * This method use find all merchant by name container
	 * 
	 * @return List merchant
	 */
	List<Merchant> findAllMerchantAndNameContainer(String name);
	/**
	 * This method use find by id of account
	 * 
	 * @return merchant
	 */
	Merchant findByAccount(Long id);

	/**
	 * Dùng để load product theo merchant id
	 * 
	 * @param id          chỉ số của thực thể merchant
	 * @param model       modelview
	 * @param httpSession session
	 * @return
	 */
	String detailMer(Long id, Model model, HttpSession httpSession);

	/**
	 * Dùng để load merchant và kiểm tra đăng nhập cho homepage
	 * 
	 * @param model
	 * @param session
	 */
	void homePage(Model model, HttpSession session,int page,int pageSize);

}
