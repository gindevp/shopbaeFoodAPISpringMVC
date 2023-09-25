package shopbae.food.repository.product;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import shopbae.food.model.Merchant;
import shopbae.food.model.Product;
import shopbae.food.repository.merchant.IMerchantRepository;

@Repository
@Transactional
@EnableTransactionManagement
public class ProductRepository implements IProductRepository {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private IMerchantRepository iMerchantRepository;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public Product findById(Long id) {
		return getSession().get(Product.class, id);
	}

	@Override
	public void save(Product t) {
		getSession().save(t);
	}

	@Override
	public void update(Product t) {
		getSession().update(t);
	}

	@Override
	public void delete(Product t) {
		getSession().delete(t);
	}

	@Override
	public List<Product> findAll() {
		return getSession().createQuery("FROM product", Product.class).getResultList();
	}

	@Override
	public Product findByName(String name) {
		try {
			TypedQuery<Product> query = getSession().createQuery("FROM product a where a.name=:name", Product.class);
			query.setParameter("name", name);
			return query.getSingleResult();
		} catch (Exception e) {
			return null;
		}

	}

	@Override
	public List<Product> getAllByDeleteFlagTrueAndMerchant(Long id) {
		Merchant mer= iMerchantRepository.findById(id);
		try {
			TypedQuery<Product> query = getSession().createQuery("From product a where a.deleteFlag = true and a.merchant =:id", Product.class);
			query.setParameter("id", mer);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	@Override
	public List<Product> fAllByDeleFlagTAndMerAndNameContai(Long id, String name) {
		try {
			TypedQuery<Product> query = getSession().createQuery(
					"From product p where p.deleteFlag = true and p.merchant =:id and p.name like concat('%',:name, '%')",
					Product.class);
			query.setParameter("id", iMerchantRepository.findById(id));
			query.setParameter("name", name);
			return query.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

}
