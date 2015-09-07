package tk.tingwode.client.domain;

import java.util.Date;
import java.util.Set;

public class AlbumJO {
	public String id;		//id
	
	public String name;	//专辑名
	public String description;
	public Date date;
	
	public UserJO userjo;//所属用户id
	public int voices_count;//声音list集合
	public Set<UserJO> users_like;//赞的用户
	public Set<UserJO> users_favorite;//收藏的用户
	
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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
	public int getVoices_count() {
		return voices_count;
	}
	public void setVoices_count(int voices_count) {
		this.voices_count = voices_count;
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
	
}
