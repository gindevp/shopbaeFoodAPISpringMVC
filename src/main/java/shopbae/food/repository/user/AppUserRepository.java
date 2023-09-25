package shopbae.food.repository.user;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import shopbae.food.model.AppUser;

@Repository
@Transactional
@EnableTransactionManagement
public class AppUserRepository implements IAppUserRepository {
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public AppUser findById(Long id) {
		return getSession().get(AppUser.class, id);
	}

	@Override
	public void save(AppUser t) {
		getSession().save(t);
	}

	@Override
	public void update(AppUser t) {
		getSession().update(t);
	}

	@Override
	public void delete(AppUser t) {
		getSession().delete(t);
	}

	@Override
	public List<AppUser> findAll() {
		return getSession().createQuery("FROM appuser ", AppUser.class).getResultList();
	}

	@Override
	public AppUser findByName(String name) {
		try {
			TypedQuery<AppUser> query = getSession().createQuery("FROM appuser a where a.name =: name", AppUser.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public List<AppUser> getAllByStatus(String status) {
		try {
			TypedQuery<AppUser> query = getSession().createQuery("From appuser a where a.status=: status",
					AppUser.class);
			query.setParameter("status", status);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

}
