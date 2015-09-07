package tk.tingwode.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import tk.tingwode.dao.UserDao;
import tk.tingwode.dao.VoiceDao;
import tk.tingwode.domain.Album;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;

public class BusinessService {
	UserDao udao=new UserDao();
	VoiceDao vdao=new VoiceDao();
	private static SessionFactory sessionFactory;
	static {
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	/**
	 * 注册用户
	 * @param user
	 */
	public void addUser(User user){
//		udao.add(user);
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			session.save(user);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}
	
	/**
	 * 根据id获得用户
	 * @param id
	 * @return
	 */
	public User findUser(String id){
		return udao.find(id);
//		Session session = sessionFactory.openSession();
//		Transaction tx=session.beginTransaction();
//		try{
//			User
//			
//			tx.commit();
//		}catch(Exception e){
//			e.printStackTrace();
//			tx.rollback();
//		}finally{
//			session.close();
//		}
	}
	
	/**
	 * 根据邮箱和密码获得用户
	 * @param email
	 * @param password
	 * @return
	 */
	public User findUser(String email,String password){
		return udao.find(email,password);
	}
	
	/**
	 * 关注功能
	 * @param id
	 * @param followed_id
	 */
	public void followUser(String id,String followed_id){
		User user=udao.find(id);
		User followed_user=udao.find(followed_id);
		user.getFollowing().add(followed_user);
		followed_user.getFollowers().add(user);
		udao.update(user);
	}
	
	/**
	 * 发布声音
	 * @param voice
	 */
	public void publishVoice(String user_id, Voice voice){
//		User user=udao.find(user_id);
//		voice.setUser(user);
//		vdao.add(voice);
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			User user=(User) session.get(User.class, user_id);
			user.getVoices().add(voice);
			session.update(user);
			voice.setUser(user);
			session.save(voice);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	/**获取全部声音*/
	public List<Voice> findAllVoice(){
		return vdao.findAll();
	}
	
	/**获取用户的全部声音*/
	public List<Voice> findAllVoiceOfUser(String user_id){
		return vdao.findAll(user_id);
	}
	
	/**根据id获取声音*/
	public Voice findVoice(String id){
		return vdao.find(id);
	}
	
	/**根据id删除声音*/
	public void deleteVoice(String id){
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			Voice voice=(Voice) session.get(Voice.class, id);
			User user=voice.getUser();
			user.getVoices().remove(voice);
			session.update(user);
			for(Album album:voice.getAlbums()){
				album.getVoices().remove(voice);
				session.update(album);
			}
			session.delete(voice);
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e);
		} finally {
			session.close();
		}
	}
	
	/**删除声音*/
	public void deleteVoice(Voice voice){
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			User user=voice.getUser();
			user.getVoices().remove(voice);
			session.update(user);
			for(Album album:voice.getAlbums()){
				album.getVoices().remove(voice);
				session.update(album);
			}
			session.delete(voice);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}

	/**创建专辑（无声音）*/
	public void addAlbum(Album album) {
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			User user=(User) session.get(User.class, album.getUser().getId());
			user.getAlbums().add(album);
			session.update(user);
			album.setUser(user);
			session.save(album);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}
	
	/**为专辑添加声音*/
	public void addVoices2Album(String album_id,String[] voice_ids){
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			Album album=(Album) session.get(Album.class, album_id);
			Set<Voice> voices=album.getVoices();
			for(String voice_id:voice_ids){
				Voice voice = (Voice) session.get(Voice.class,voice_id);
				voices.add(voice);
//				voice.getAlbums().add(album);
//				session.update(voice);
			}
			session.save(album);
			session.update(album.getUser());
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}
	
	/**创建专辑（同时添加声音）*/
	public void addAlbumWithVoices(Album album,String[] voice_ids){
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			User user=(User) session.get(User.class, album.getUser().getId());
			user.getAlbums().add(album);
			album.setUser(user);
			session.update(user);
			Set<Voice> voices=album.getVoices();
			for(String voice_id:voice_ids){
				Voice voice = (Voice) session.get(Voice.class,voice_id);
				voices.add(voice);
			}
			session.save(album);
			tx.commit();
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}
	
	public List<Album> findAllAlbumOfUser(String user_id){
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			String hql="from Album where user_id=?";
			Query query=session.createQuery(hql).setParameter(0, user_id);
			List<Album> albums=query.list();
			tx.commit();
			return albums;
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}

	public List<Voice> findVoiceOfAlbum(String album_id) {
		Session session = sessionFactory.openSession();
		Transaction tx=session.beginTransaction();
		try{
			Album album=(Album) session.get(Album.class, album_id);
			Set<Voice> voiceSet=album.getVoices();
			List<Voice> voices=new ArrayList(voiceSet);
			//对声音进行排序
			Collections.sort(voices, new Comparator<Voice>(){

				public int compare(Voice o1, Voice o2) {
					int flag=o1.getDate().compareTo(o2.getDate());
					if(flag!=0)
						return flag;
					flag=o1.getTitle().compareTo(o2.getTitle());
					
					return flag;
				}
				
			});
			tx.commit();
			return voices;
		}catch(Exception e){
			tx.rollback();
			throw new RuntimeException(e);
		}finally{
			session.close();
		}
	}
}
