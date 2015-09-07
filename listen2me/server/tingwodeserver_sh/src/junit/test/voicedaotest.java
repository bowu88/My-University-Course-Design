package junit.test;

import java.util.HashSet;
import java.util.List;

import org.junit.Test;

import tk.tingwode.dao.AlbumDao;
import tk.tingwode.dao.UserDao;
import tk.tingwode.dao.VoiceDao;
import tk.tingwode.domain.Album;
import tk.tingwode.domain.Voice;
import tk.tingwode.utils.Global;

import com.google.gson.Gson;

public class VoiceDaoTest {
	@Test
	public void addVoice(){
		UserDao udao=new UserDao();
		VoiceDao dao=new VoiceDao();
		Voice voice=new Voice();
		voice.setTitle("测试声音");
		voice.setUser(udao.find("test2@test.com","123"));
		voice.setTag("测试");
		voice.setAudio_url("xxxxx");
		dao.add(voice);
	}
	
	@Test
	public void addVoiceToNewAlbum(){
		UserDao udao=new UserDao();
		VoiceDao vdao=new VoiceDao();
		Voice voice=new Voice();
		voice.setTitle("测试声音");
		voice.setUser(udao.find("test2@test.com","123"));
		voice.setTag("测试");
		voice.setAudio_url("xxxxx");
		HashSet<Album> set=new HashSet<Album>();
		Album a=new Album();
		a.setName("新专辑");
		a.setDescription("用于测试专辑的增删改查");
		set.add(a);
		voice.setAlbums(set);
		vdao.add(voice);
	}
	
	@Test 
	public void findVoice(){
		VoiceDao dao=new VoiceDao();
		Voice voice=dao.find("2c91b905466d12f301466d1c0b760001");
//		System.out.println(voice.getTitle()+voice.getDate());
		Gson gson=Global.gson;
		System.out.println(gson.toJson(voice));
	}
	
	@Test
	public void findAllVoice(){
		VoiceDao vdao=new VoiceDao();
		List<Voice> voices=vdao.findAll("2c91b905466d122101466d1223d00000");
		for(Voice v:voices){
			System.out.println(v.getTitle()+" "+v.getDate());
		}
	}
	
	@Test
	public void addAlbum(){
		AlbumDao dao=new AlbumDao();
		Album a=new Album();
		a.setName("测试专辑");
		a.setDescription("用于测试专辑的时间戳");
		dao.add(a);
	}
	
	@Test 
	public void findAlbums(){
		AlbumDao dao=new AlbumDao();
		Album a=dao.find("2c91b905466b773601466b773ae70000");
		System.out.println(a.getName());
		for(Voice v:a.getVoices()){
			System.out.println(v.getTitle());
		}
	}
	
	@Test
	public void addNewVoiceToAlbums(){
		UserDao udao=new UserDao();
		AlbumDao dao=new AlbumDao();
		Album a=new Album();
		a.setName("测试专辑中直接加新声音");
		a.setDescription("用于测试专辑的增删改查");
		Voice voice=new Voice();
		voice.setTitle("新声音");
		voice.setUser(udao.find("test2@test.com","123"));
		voice.setTag("测试");
		voice.setAudio_url("xxxxx");
		a.addVoice(voice);
		dao.add(a);
	}
}
