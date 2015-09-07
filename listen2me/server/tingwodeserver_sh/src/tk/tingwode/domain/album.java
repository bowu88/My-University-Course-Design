package tk.tingwode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 专辑
 * @author Xiangyang
 *
 */
public class Album {
	private String id;		//id
	
	private String name;	//专辑名
	private String description;
	private Date date;
	
	private User user;//所属用户id
	private Set<Voice> voices=new HashSet();//声音list集合
	private Set<User> users_like=new HashSet();//赞的用户
	private Set<User> users_favorite=new HashSet();;//收藏的用户
	
	public void addVoice(Voice voice){
		if(voice==null)
			return;
		if(this.voices==null)
			this.voices=new HashSet<Voice>();
		this.getVoices().add(voice);
		if(voice.getAlbums()==null){
			voice.setAlbums(new HashSet<Album>());
		}
		voice.getAlbums().add(this);
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Set<Voice> getVoices() {
		return voices;
	}
	public void setVoices(Set<Voice> voices) {
		this.voices = voices;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Set<User> getUsers_like() {
		return users_like;
	}
	public void setUsers_like(Set<User> users_like) {
		this.users_like = users_like;
	}
	public Set<User> getUsers_favorite() {
		return users_favorite;
	}
	public void setUsers_favorite(Set<User> users_favorite) {
		this.users_favorite = users_favorite;
	}
	
}
