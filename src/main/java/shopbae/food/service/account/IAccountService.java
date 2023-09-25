package shopbae.food.service.account;

import org.springframework.security.core.userdetails.UserDetails;

import shopbae.food.model.Account;
import shopbae.food.service.IGeneral;

public interface IAccountService extends IGeneral<Account> {
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
	 * 
	 * @return If it matches return true, opposite
	 */
	boolean existsAccountByUserName(String username);

	/**
	 * this method use load user by user name
	 * 
	 * @param username
	 * @return Userdetail
	 */
	UserDetails loadUserByUsername(String username);

	void resetFailedAttempts(Account account);

	void increaseFailedAttempts(Account account);

	void lock(Account account);

	boolean unlockWhenTimeExpired(Account account);
}
