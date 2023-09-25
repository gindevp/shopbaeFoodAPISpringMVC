package shopbae.food.repository.role;

import javax.persistence.NoResultException;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import shopbae.food.model.Account;
import shopbae.food.model.AccountRoleMap;
import shopbae.food.model.AppRoles;

@Repository
@Transactional(rollbackFor = Exception.class)
public class RoleRepository implements IRoleRepository {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public AppRoles findByName(String name) {
		TypedQuery<AppRoles> query = getSession().createQuery("FROM roles a WHERE a.name = :name", AppRoles.class);
		query.setParameter("name", name);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public void setDefaultRole(Long accountId, Long roleId) {
		Account account = new Account();
		account.setId(accountId);
		AppRoles roles = new AppRoles();
		roles.setId(roleId);
		getSession().save(new AccountRoleMap(account, roles));
	}

}
