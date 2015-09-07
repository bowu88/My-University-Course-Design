package tk.tingwode.dao;

import javax.management.RuntimeErrorException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tk.tingwode.domain.User;

public class UserDao {
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public void add(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void delete(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			User user = (User) session.get(User.class, id);
			session.save(user);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void update(User user) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.update(user);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public User find(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			User user = (User) session.get(User.class, id);
			tx.commit();
			return user;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public User find(String email, String password) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			User user = (User) session
					.createQuery("FROM User WHERE email=? and password=?")
					.setParameter(0, email).setParameter(1, password)
					.uniqueResult();
			tx.commit();
			return user;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

}
