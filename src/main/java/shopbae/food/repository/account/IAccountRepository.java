package shopbae.food.repository.account;

import shopbae.food.model.Account;
import shopbae.food.service.IGeneral;

public interface IAccountRepository extends IGeneral<Account> {
	/**
	 * this method use find by name
	 * 
	 * @param name
	 * @return Account
	 */
	Account findByName(String name);

	/**
	 * this method use find by username
	 * 
	 * @param userName
	 * @return Long idUser
	 */
	Long findIdUserByUserName(String userName);

	/**
	 * this method check if the account has the same name
	 * @return If it matches return true, opposite
	 */
	boolean existsAccountByUserName(String username);

}
