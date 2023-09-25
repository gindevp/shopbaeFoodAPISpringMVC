package shopbae.food.repository.merchant;

import java.util.List;

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

import shopbae.food.model.Merchant;

@Repository
@Transactional
@EnableTransactionManagement
public class MerchantRepository implements IMerchantRepository {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Merchant findById(Long id) {
		// Tạo CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Tạo CriteriaQuery
		CriteriaQuery<Merchant> criteriaQuery = criteriaBuilder.createQuery(Merchant.class);

		// Tạo Root
		Root<Merchant> root = criteriaQuery.from(Merchant.class);

		// Thêm điều kiện truy vấn
		Predicate condition = criteriaBuilder.equal(root.get("id"), id);

		// Thêm điều kiện vào truy vấn
		criteriaQuery.where(condition);

		// Tạo truy vấn với điều kiện
		TypedQuery<Merchant> query = entityManager.createQuery(criteriaQuery);

		// Thực hiện truy vấn
		Merchant result = query.getSingleResult();
		return result;
	}

	@Override
	public void save(Merchant t) {
		getSession().save(t);
	}

	@Override
	public void update(Merchant t) {
		getSession().update(t);
	}

	@Override
	public void delete(Merchant t) {
		getSession().delete(t);
	}

	@Override
	public List<Merchant> findAll() {
		return getSession().createQuery("FROM merchant ", Merchant.class).getResultList();
	}

	@Override
	public Merchant findByName(String name) {
		// Tạo CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Tạo CriteriaQuery
		CriteriaQuery<Merchant> criteriaQuery = criteriaBuilder.createQuery(Merchant.class);

		// Tạo Root
		Root<Merchant> root = criteriaQuery.from(Merchant.class);

		// Thêm điều kiện truy vấn
		Predicate condition = criteriaBuilder.equal(root.get("name"), name);
		// Thêm điều kiện vào truy vấn
		criteriaQuery.where(condition);

		// Tạo truy vấn với điều kiện
		TypedQuery<Merchant> query = entityManager.createQuery(criteriaQuery);

		// Thực hiện truy vấn
		Merchant result = query.getSingleResult();
		return result;
	}

	@Override
	public List<Merchant> getAllByMerchantStatus(String status) {
		try {
			TypedQuery<Merchant> query = getSession().createQuery("From merchant a where a.status=: status",
					Merchant.class);
			query.setParameter("status", status);
			return query.getResultList();
		} catch (NoResultException e) {
			return null;
		}

	}

	@Override
	public List<Merchant> findAllMerchantAndNameContainer(String name) {
		try {
			TypedQuery<Merchant> query = getSession().createQuery(
					"From merchant a where a.status='ACTIVE' and a.name like concat('%', :name  ,'%')", Merchant.class);
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public Merchant findByAccount(Long id) {
		try {
			TypedQuery<Merchant> query = getSession().createQuery("FROM merchant a where a.account =:id",
					Merchant.class);
			query.setParameter("id", id);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

}
