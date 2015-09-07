package tk.tingwode.utils;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import tk.tingwode.client.domain.AlbumJO;
import tk.tingwode.client.domain.UserJO;
import tk.tingwode.client.domain.VoiceJO;
import tk.tingwode.domain.Album;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;

public class WebUtils {
	/**
	 * 生成唯一的id
	 * @return
	 */
	public static String makeId(){
		return UUID.randomUUID().toString();
	}
	
	/**将User转为UserJO*/
	public static UserJO User2UserJO(User user){
		UserJO u=new UserJO();
		u.setId(user.getId());
		u.setUsername(user.getUsername());
		u.setEmail(user.getEmail());
		u.setPassword(user.getPassword());
		u.setGender(user.getGender());
		u.setPhoto_url(user.getPhoto_url());
		u.setVoices_count(user.getVoices().size());
		u.setAlbums_count(user.getAlbums().size());
		u.setVoices_favorite_count(user.getVoices_favorite().size());
		u.setAlbums_favorite_count(user.getAlbums_favorite().size());
		u.setFollowing_count(user.getFollowing().size());
		u.setFollowers_count(user.getFollowers().size());
		return u;
	}
	
	/**将Album转为AlbumJO*/
	public static AlbumJO Album2AlbumJO(Album album){
		AlbumJO ajo=new AlbumJO();
		ajo.setId(album.getId());
		ajo.setName(album.getName());
		ajo.setDescription(album.getDescription());
		ajo.setDate(album.getDate());
		ajo.setUserjo(User2UserJO(album.getUser()));
		ajo.setVoices_count(album.getVoices().size());
		Set<UserJO> users_like=new HashSet();
		for(User u:album.getUsers_like()){
			users_like.add(User2UserJO(u));
		}
		ajo.setUsers_like(users_like);
		Set<UserJO> users_favorite=new HashSet();
		for(User u:album.getUsers_favorite()){
			users_favorite.add(User2UserJO(u));
		}
		ajo.setUsers_favorite(users_favorite);
		return ajo;
	}
	
	/**将Voice转为VoiceJO*/
	public static VoiceJO Voice2VoiceJO(Voice v){
		VoiceJO vjo=new VoiceJO();
		vjo.setId(v.getId());
		vjo.setTag(v.getTag());
		vjo.setTitle(v.getTitle());
		vjo.setDescription(v.getDescription());
		vjo.setPicture_url(v.getPicture_url());
		vjo.setAudio_url(v.getAudio_url());
		vjo.setLatitude(v.getLatitude());
		vjo.setLongitude(v.getLongitude());
		vjo.setPlay_count(v.getPlay_count());
		vjo.setDate(v.getDate());
		vjo.setUserjo(User2UserJO(v.getUser()));
		Set<String> albums=new HashSet();
		for(Album a:v.getAlbums()){
			albums.add(a.getId());
		}
		vjo.setAlbums(albums);
		Set<UserJO> users_like=new HashSet();
		for(User u:v.getUsers_like()){
			users_like.add(User2UserJO(u));
		}
		vjo.setUsers_like(users_like);
		Set<UserJO> users_favorite=new HashSet();
		for(User u:v.getUsers_favorite()){
			users_favorite.add(User2UserJO(u));
		}
		vjo.setUsers_favorite(users_favorite);
		return vjo;
	}
}
