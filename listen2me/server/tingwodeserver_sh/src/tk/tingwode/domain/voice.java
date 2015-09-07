package tk.tingwode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
/**
 * 声音
 * @author Xiangyang
 *
 */
public class Voice {
	private String id;			//id
	
	private String tag;			//标签
	private String title;		//标题
	private String description;//描述
	private String picture_url;//图片url
	private String audio_url;	//音频url
	private double latitude;	//纬度
	private double longitude;	//经度
	private int play_count;	//播放次数
	private Date date;
	
	private User user;		//所属用户
	private Set<Album> albums=new HashSet();	//所属专辑
	private Set<User> users_like=new HashSet();//赞的用户
	private Set<User> users_favorite=new HashSet();//收藏的用户
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tar) {
		this.tag = tar;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getPicture_url() {
		return picture_url;
	}
	public void setPicture_url(String picture_url) {
		this.picture_url = picture_url;
	}
	public String getAudio_url() {
		return audio_url;
	}
	public void setAudio_url(String audio_url) {
		this.audio_url = audio_url;
	}
	public Set<Album> getAlbums() {
		return albums;
	}
	public void setAlbums(Set<Album> albums) {
		this.albums = albums;
	}
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public int getPlay_count() {
		return play_count;
	}
	public void setPlay_count(int play_count) {
		this.play_count = play_count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
