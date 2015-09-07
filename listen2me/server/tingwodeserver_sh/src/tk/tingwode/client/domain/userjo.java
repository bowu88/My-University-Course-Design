package tk.tingwode.client.domain;


public class UserJO {
	public String id;			//id
	public String username;	//用户名
	public String email;		//邮箱
	public String password;	//密码
	public String gender;		//性别
	public String photo_url;	//头像url
	
	public int voices_count;//用户发布的声音
	public int albums_count;//用户发布的专辑
	
	public int voices_favorite_count;
	public int albums_favorite_count;
	
	public int following_count;
	public int followers_count;
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
	public int getVoices_count() {
		return voices_count;
	}
	public void setVoices_count(int voices_count) {
		this.voices_count = voices_count;
	}
	public int getAlbums_count() {
		return albums_count;
	}
	public void setAlbums_count(int albums_count) {
		this.albums_count = albums_count;
	}
	public int getVoices_favorite_count() {
		return voices_favorite_count;
	}
	public void setVoices_favorite_count(int voices_favorite_count) {
		this.voices_favorite_count = voices_favorite_count;
	}
	public int getAlbums_favorite_count() {
		return albums_favorite_count;
	}
	public void setAlbums_favorite_count(int albums_favorite_count) {
		this.albums_favorite_count = albums_favorite_count;
	}
	public int getFollowing_count() {
		return following_count;
	}
	public void setFollowing_count(int following_count) {
		this.following_count = following_count;
	}
	public int getFollowers_count() {
		return followers_count;
	}
	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}
	
}
