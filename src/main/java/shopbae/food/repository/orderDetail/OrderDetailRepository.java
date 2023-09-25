package shopbae.food.repository.orderDetail;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import shopbae.food.model.OrderDetail;
import shopbae.food.repository.order.IOrderRepository;

@Repository
@Transactional
@EnableTransactionManagement
public class OrderDetailRepository implements IOrderDetailRepository {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private IOrderRepository orderRepository;

	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}

	@Override
	public OrderDetail findById(Long id) {
		return getSession().get(OrderDetail.class, id);
	}

	@Override
	public void save(OrderDetail t) {
		getSession().save(t);
	}

	@Override
	public void update(OrderDetail t) {
		getSession().update(t);
	}

	@Override
	public void delete(OrderDetail t) {
		getSession().delete(t);
	}

	@Override
	public List<OrderDetail> findAll() {
		return getSession().createQuery("FROM order_detail ", OrderDetail.class).getResultList();
	}

	@Override
	public List<OrderDetail> findByOrder(Long id) {
		try {
			TypedQuery<OrderDetail> query = getSession().createQuery("FROM order_detail a where a.order=:id",
					OrderDetail.class);
			query.setParameter("id", orderRepository.findById(id));
			return query.getResultList();
		} catch (Exception e) {
			return null;
		}

	}

}
