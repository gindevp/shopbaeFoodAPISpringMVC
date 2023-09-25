package shopbae.food.service.role;

import shopbae.food.model.AppRoles;

public interface IRoleService {
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
