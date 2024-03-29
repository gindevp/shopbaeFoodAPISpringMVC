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
public class FavoriteRepository implements IFavoriteRepository {
    @Autowired
    private SessionFactory sessionFactory;

    private Session getSession() {
        Session session = sessionFactory.getCurrentSession();
        return session;
    }

    @Override
    public Favorite findById(Long id) {

        return getSession().get(Favorite.class, id);
    }

    @Override
    public void save(Favorite t) {
        getSession().save(t);
    }

    @Override
    public void update(Favorite t) {
        getSession().update(t);
    }

    @Override
    public void delete(Favorite t) {
        getSession().delete(t);
    }

    @Override
    public List<Favorite> findAll() {
        return getSession().createQuery("FROM favorite", Favorite.class).getResultList();
    }

}
