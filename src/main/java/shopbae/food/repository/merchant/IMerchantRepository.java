package shopbae.food.repository.merchant;

import java.util.List;
import shopbae.food.model.Merchant;
import shopbae.food.service.IGeneral;

public interface IMerchantRepository extends IGeneral<Merchant> {
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
}
