package shopbae.food.repository.account;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import shopbae.food.model.Account;

@Repository
@Transactional
@EnableTransactionManagement
public class AccountRepository implements IAccountRepository {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Account findById(Long id) {
		// Tạo CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Tạo CriteriaQuery
		CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);

		// Tạo Root
		Root<Account> root = criteriaQuery.from(Account.class);

		// Thêm điều kiện truy vấn
		Predicate condition = criteriaBuilder.equal(root.get("id"), id);

		// Thêm điều kiện vào truy vấn
		criteriaQuery.where(condition);

		// Tạo truy vấn với điều kiện
		TypedQuery<Account> query = entityManager.createQuery(criteriaQuery);

		// Thực hiện truy vấn
		Account result = query.getSingleResult();
		return result;
	}

	@Override
	public void save(Account t) {
		getSession().save(t);
	}

	@Override
	public void update(Account t) {
		getSession().update(t);
	}

	@Override
	public void delete(Account t) {
		getSession().delete(t);
	}

	@Override
	public List<Account> findAll() {
		return getSession().createQuery("FROM account ", Account.class).getResultList();
	}

	@Override
	public Account findByName(String name) {
		try {
			TypedQuery<Account> query = getSession().createQuery("FROM account a where a.userName =: name", Account.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}

	@Override
	public Long findIdUserByUserName(String userName) {
		try { // Tạo CriteriaBuilder
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			// Tạo CriteriaQuery
			CriteriaQuery<Account> criteriaQuery = criteriaBuilder.createQuery(Account.class);

			// Tạo Root
			Root<Account> root = criteriaQuery.from(Account.class);

			// Thêm điều kiện truy vấn
			Predicate condition = criteriaBuilder.equal(root.get("userName"), userName);

			// Thêm điều kiện vào truy vấn
			criteriaQuery.where(condition);

			// Tạo truy vấn với điều kiện
			TypedQuery<Account> query = entityManager.createQuery(criteriaQuery);

			// Thực hiện truy vấn
			Account result = query.getSingleResult();

			return result.getId();
		} catch (NoResultException e) {
			return null;
		}
	}

	@Override
	public boolean existsAccountByUserName(String username) {
		Optional<Account> optional = Optional.ofNullable(findByName(username));
		try {
			if (optional.isPresent()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
}
