package shopbae.food.repository.cart;

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

import shopbae.food.model.Cart;
import shopbae.food.model.Product;
import shopbae.food.model.ProductCartMap;
import shopbae.food.repository.product.IProductRepository;

@Repository
@Transactional
@EnableTransactionManagement
public class CartRepository implements ICartRepository {
	@PersistenceContext
	private EntityManager entityManager;
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private IProductRepository productRepository;
	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Cart findById(Long id) {
		// Tạo CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Tạo CriteriaQuery
		CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);

		// Tạo Root
		Root<Cart> root = criteriaQuery.from(Cart.class);

		// Thêm điều kiện truy vấn
		Predicate condition = criteriaBuilder.equal(root.get("id"), id);

		// Thêm điều kiện vào truy vấn
		criteriaQuery.where(condition);

		// Tạo truy vấn với điều kiện
		TypedQuery<Cart> query = entityManager.createQuery(criteriaQuery);

		// Thực hiện truy vấn
		Cart result = query.getSingleResult();
		return result;
	}

	@Override
	public void save(Cart t) {
		getSession().save(t);
	}

	@Override
	public void update(Cart t) {
		getSession().update(t);
	}

	@Override
	public void delete(Cart t) {
		getSession().delete(t);
	}

	@Override
	public List<Cart> findAll() {
		return getSession().createQuery("FROM cart ", Cart.class).getResultList();
	}

	@Override
	public List<Cart> findAllByUser(Long id) {
		// Tạo CriteriaBuilder
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

		// Tạo CriteriaQuery
		CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);

		// Tạo Root
		Root<Cart> root = criteriaQuery.from(Cart.class);

		// Thêm điều kiện truy vấn
		Predicate condition = criteriaBuilder.equal(root.get("user"), id);
		Predicate condition2 = criteriaBuilder.equal(root.get("deleteFlag"), true);
		// Thêm điều kiện vào truy vấn
		criteriaQuery.where(criteriaBuilder.and(condition, condition2));

		// Tạo truy vấn với điều kiện
		TypedQuery<Cart> query = entityManager.createQuery(criteriaQuery);

		// Thực hiện truy vấn
		List<Cart> result = query.getResultList();
		return result;
	}

	@Override
	public Cart findByProduct(Long id) {
		TypedQuery<Cart> query = getSession().createQuery("FROM cart a where a.product =:id", Cart.class);
		query.setParameter("id", id);
		try {
			return query.getSingleResult();
		} catch (NoResultException e) {
			// TODO: handle exception
			return null;
		}

	}

	@Override
	public Cart findByProductIdAndUserId(Long product_id, Long user_id) {
		try {
			// Tạo CriteriaBuilder
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

			// Tạo CriteriaQuery
			CriteriaQuery<Cart> criteriaQuery = criteriaBuilder.createQuery(Cart.class);

			// Tạo Root
			Root<Cart> root = criteriaQuery.from(Cart.class);

			// Thêm điều kiện truy vấn
			Predicate condition = criteriaBuilder.equal(root.get("user"), user_id);
			Predicate condition2 = criteriaBuilder.equal(root.get("product"), product_id);
			Predicate condition3 = criteriaBuilder.equal(root.get("deleteFlag"), true);
			// Thêm điều kiện vào truy vấn
			criteriaQuery.where(criteriaBuilder.and(condition, condition2, condition3));

			// Tạo truy vấn với điều kiện
			TypedQuery<Cart> query = entityManager.createQuery(criteriaQuery);

			// Thực hiện truy vấn
			Cart result = query.getSingleResult();
			return result;

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	@Override
	public void setProductCart(Long cart_id, Long product_id) {
		Product product = new Product();
		Cart cart = new Cart();
		cart.setId(cart_id);
		product.setId(product_id);
		getSession().save(new ProductCartMap(product, cart));
	}

	@Override
	public void updateQuantity(int quantity, Long cart_id) {
		Cart cart = findById(cart_id);
		cart.setQuantity(quantity);
		cart.setTotalPrice(cart.getTotalPrice());
		getSession().update(cart);
	}

	@Override
	public void deletesByUser(Long id) {
		try {
			TypedQuery<Cart> query = getSession().createQuery("UPDATE cart a SET a.deleteFlag=false WHERE a.user =: id",
					Cart.class);
			query.setParameter("id", id);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public Cart findByProductAndFlag(Long id) {
		try {
			TypedQuery<Cart> query = getSession().createQuery("FROM cart a where a.deleteFlag=true and a.product =: id",
					Cart.class);
			query.setParameter("id", productRepository.findById(id));
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}
	}
}
