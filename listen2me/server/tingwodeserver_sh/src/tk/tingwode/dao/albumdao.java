package tk.tingwode.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tk.tingwode.domain.Album;

public class AlbumDao {
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public void add(Album album) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(album);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public Album find(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Album album = (Album) session.get(Album.class, id);
			tx.commit();
			return album;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
}
