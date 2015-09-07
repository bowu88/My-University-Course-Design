package junit.test;

import org.junit.Test;

import tk.tingwode.domain.Album;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;
import tk.tingwode.service.BusinessService;

public class BusinessTest {
	BusinessService service =new BusinessService();
	String user1_id="2c91b905466d122101466d1223d00000";
	/**
	 * 测试关注功能
	 */
	@Test
	public void testFollow(){
		String id=user1_id;
		String followed_id="2c91b905466cca0401466cca06ab0000";
		service.followUser(id, followed_id);
	}
	
	/**测试发布声音功能 */
	@Test
	public void testPublishVoice(){
		Voice voice=new Voice();
		voice.setTag("测试session");
		voice.setTitle("新发布的声音");
		voice.setAudio_url("xxxxx");
		service.publishVoice(user1_id, voice);
		
	}
	
	//测试创建专辑
	@Test
	public void testCreateAlbum(){
		Album album=new Album();
		album.setName("测试专辑");
		album.setDescription("无声音");
		User user=new User();
		user.setId("2c91b905466d122101466d1223d00000");
		album.setUser(user);
		service.addAlbum(album);
	}
	
	//测试添加声音
	@Test
	public void testAddVoice2Album(){
		String[] voice_ids={"2c91b905468a073b01468a12601b0000","2c91b905468a073b01468a19c3610001"};
		service.addVoices2Album("2c91b905468b3b6801468b3b74b40000",voice_ids);
	}
	
	@Test
	public void testCreateAlbumWithVoices(){
		Album album=new Album();
		album.setName("测试专辑");
		album.setDescription("有声音");
		User user=new User();
		user.setId("2c91b905466d122101466d1223d00000");
		album.setUser(user);
		String[] voice_ids={"2c91b905468a073b01468a12601b0000","2c91b905468a073b01468a19c3610001"};
		service.addAlbumWithVoices(album, voice_ids);
	}
}
