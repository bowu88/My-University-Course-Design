package tk.tingwode.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;

public class VoiceDao {
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}

	public void add(Voice voice) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(voice);
			voice.getUser().getVoices().add(voice);
			session.update(voice.getUser());
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public Voice find(String id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			Voice voice = (Voice) session.get(Voice.class, id);
			tx.commit();
			return voice;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public List<Voice> findAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Voice";
			Query query = session.createQuery(hql);
			List<Voice> voices = query.list();
			tx.commit();
			return voices;
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	/** 获取用户的所有声音 */
	public List<Voice> findAll(String user_id) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			String hql = "from Voice where user_id=?";
			Query query = session.createQuery(hql).setParameter(0, user_id);
			List<Voice> voices = query.list();
			tx.commit();
			return voices;
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}

	public void update(Voice voice) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		try {
			User user = voice.getUser();
			session.update(voice);
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
			Voice voice = (Voice) session.get(Voice.class, id);
			User user = voice.getUser();
			user.getVoices().remove(voice);
			session.update(user);
			session.delete(voice);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
}
