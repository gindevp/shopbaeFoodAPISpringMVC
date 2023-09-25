
package shopbae.food.service.user;

import java.util.List;

import shopbae.food.model.AppUser;
import shopbae.food.service.IGeneral;

public interface IAppUserService extends IGeneral<AppUser> {
	/**
	 * THis method use find by name
	 * 
	 * @param name
	 * @return appuser
	 */
	AppUser findByName(String name);

	/**
	 * This method use find all by status
	 * 
	 * @param status
	 * @return list
	 */
	List<AppUser> getAllByStatus(String status);
}
