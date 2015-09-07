package tk.tingwode.client.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class VoiceJO {
public String id;			//id
	
	public String tag;			//标签
	public String title;		//标题
	public String description;//描述
	public String picture_url;//图片url
	public String audio_url;	//音频url
	public double latitude;	//纬度
	public double longitude;	//经度
	public int play_count;	//播放次数
	public Date date;
	
	public UserJO userjo;		//所属用户
//	public Set<AlbumJO> albums=new HashSet();	//所属专辑
	public Set<String> albums=new HashSet();	//所属专辑
	public Set<UserJO> users_like=new HashSet();//赞的用户
	public Set<UserJO> users_favorite=new HashSet();//收藏的用户
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
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
	public UserJO getUserjo() {
		return userjo;
	}
	public void setUserjo(UserJO userjo) {
		this.userjo = userjo;
	}
	public Set<UserJO> getUsers_like() {
		return users_like;
	}
	public void setUsers_like(Set<UserJO> users_like) {
		this.users_like = users_like;
	}
	public Set<UserJO> getUsers_favorite() {
		return users_favorite;
	}
	public void setUsers_favorite(Set<UserJO> users_favorite) {
		this.users_favorite = users_favorite;
	}

	public Set<String> getAlbums() {
		return albums;
	}

	public void setAlbums(Set<String> albums) {
		this.albums = albums;
	}
	
}
