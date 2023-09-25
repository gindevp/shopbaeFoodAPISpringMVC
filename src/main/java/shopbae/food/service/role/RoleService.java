package shopbae.food.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shopbae.food.model.AppRoles;
import shopbae.food.repository.role.IRoleRepository;

@Service
public class RoleService implements IRoleService {
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public AppRoles findByName(String name) {
		return roleRepository.findByName(name);
	}

	@Override
	public void setDefaultRole(Long accountId, Long roleId) {
		roleRepository.setDefaultRole(accountId, roleId);
	}

}
