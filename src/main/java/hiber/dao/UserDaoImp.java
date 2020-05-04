package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;


@Repository
public class UserDaoImp implements UserDao {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public void add(User user) {
        sessionFactory.getCurrentSession().save(user);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<User> listUsers() {
        TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
        return query.getResultList();
    }

    @Override
    public User getUserByCarNameAndSeries(String name, int series) {
        Car car1 = null;
        User user = null;
        Query query = sessionFactory.getCurrentSession().createQuery("from Car where name = :paramName");
        query.setParameter("paramName", name);
        List<Car> cars = query.list();
        for (Car car : cars) {
            if (car.getName().equals(name) && (car.getSeries() == series)) {
                car1 = car;
            }
        }
        if (car1 != null) {
            user = car1.getUser();
        }
        return user;
    }
}
