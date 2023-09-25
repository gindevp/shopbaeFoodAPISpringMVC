package shopbae.food.repository.favorite;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import shopbae.food.model.Favorite;
@Repository
@Transactional
@EnableTransactionManagement
public class FavoriteRepository implements IFavoriteRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Session getSession() {
		Session session = sessionFactory.getCurrentSession();
		return session;
	}
	@Override
	public Favorite findById(Long id) {
		// TODO Auto-generated method stub
		return getSession().get(Favorite.class,id);
	}

	@Override
	public void save(Favorite t) {
		// TODO Auto-generated method stub
		getSession().save(t);
	}

	@Override
	public void update(Favorite t) {
		// TODO Auto-generated method stub
		getSession().update(t);
	}

	@Override
	public void delete(Favorite t) {
		// TODO Auto-generated method stub
		getSession().delete(t);
	}

	@Override
	public List<Favorite> findAll() {
		// TODO Auto-generated method stub
		return getSession().createQuery("FROM favorite", Favorite.class).getResultList();
	}

}
