package tk.tingwode.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户
 * @author Xiangyang
 *
 */
public class User {
	private String id;			//id
	private String username;	//用户名
	private String email;		//邮箱
	private String password;	//密码
	private String gender;		//性别
	private String photo_url;	//头像url
	
	private Set<Voice> voices=new HashSet();//用户发布的声音
	private Set<Album> albums=new HashSet();//用户发布的专辑
	
	private Set<Voice> voices_like=new HashSet();
	private Set<Voice> voices_favorite=new HashSet();
	private Set<Album> albums_like=new HashSet();
	private Set<Album> albums_favorite=new HashSet();
	
	private Set<User> following=new HashSet();
	private Set<User> followers=new HashSet();
	
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPhoto_url() {
		return photo_url;
	}
	public void setPhoto_url(String photo_url) {
		this.photo_url = photo_url;
	}
	public Set<Voice> getVoices() {
		return voices;
	}
	public void setVoices(Set<Voice> voices) {
		this.voices = voices;
	}
	public Set<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	public Set<Voice> getVoices_like() {
		return voices_like;
	}
	public void setVoices_like(Set<Voice> voices_like) {
		this.voices_like = voices_like;
	}
	public Set<Voice> getVoices_favorite() {
		return voices_favorite;
	}
	public void setVoices_favorite(Set<Voice> voices_favorite) {
		this.voices_favorite = voices_favorite;
	}
	public Set<Album> getAlbums_like() {
		return albums_like;
	}
	public void setAlbums_like(Set<Album> albums_like) {
		this.albums_like = albums_like;
	}
	public Set<Album> getAlbums_favorite() {
		return albums_favorite;
	}
	public void setAlbums_favorite(Set<Album> albums_favorite) {
		this.albums_favorite = albums_favorite;
	}
	public Set<User> getFollowing() {
		return following;
	}
	public void setFollowing(Set<User> following) {
		this.following = following;
	}
	public Set<User> getFollowers() {
		return followers;
	}
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	
}
