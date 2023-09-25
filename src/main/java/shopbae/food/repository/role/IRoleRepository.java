package shopbae.food.repository.role;

import shopbae.food.model.AppRoles;

public interface IRoleRepository {
	/**
	 * This method use find by name
	 * 
	 * @param name
	 * @return AppRole
	 */
	AppRoles findByName(String name);

	/**
	 * This method use set default role for account
	 * 
	 * @param accountId
	 * @param roleId
	 */
	void setDefaultRole(Long accountId, Long roleId);
}
