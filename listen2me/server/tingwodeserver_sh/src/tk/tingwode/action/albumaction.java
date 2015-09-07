package tk.tingwode.action;

import java.util.ArrayList;
import java.util.List;

import tk.tingwode.client.domain.AlbumJO;
import tk.tingwode.client.domain.VoiceJO;
import tk.tingwode.domain.Album;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;
import tk.tingwode.service.BusinessService;
import tk.tingwode.utils.Global;
import tk.tingwode.utils.WebUtils;

public class AlbumAction extends GenericAction{
	BusinessService service=new BusinessService();
	private Album album;
	private String voiceIdStr;
	
	/**创建专辑*/
	public String create(){
		User user = (User) super.session.get("user");
		if(user==null){
			message = "请登录";
			return SUCCESS;
		}
		if(album==null){
			super.message="专辑不能为空";
			return SUCCESS;
		}
		album.setUser(user);
		if(voiceIdStr==null||voiceIdStr.trim().equals("")){
			service.addAlbum(album);
			return SUCCESS;
		}
		String[] Voice_ids=voiceIdStr.split(" ");
		service.addAlbumWithVoices(album, Voice_ids);
		message="创建成功！";
		return SUCCESS;
	}
	/**获取我的专辑*/
	public String getMyAlbum(){
		User user = (User) super.session.get("user");
		if(user==null){
			message = "请登录";
			return SUCCESS;
		}
		List<Album> albums=service.findAllAlbumOfUser(user.getId());
		List<AlbumJO> albumjos=new ArrayList();
		for(Album a:albums){
			albumjos.add(WebUtils.Album2AlbumJO(a));
		}
		json=Global.gson.toJson(albumjos);
		return "json";
	}
	/**获取专辑的所有声音*/
	public String getAlbumVoice(){
		if(album==null||album.getId()==null){
			message="专辑id不能为空";
			return SUCCESS;
		}
		List<Voice> voices=service.findVoiceOfAlbum(album.getId());
		List<VoiceJO> voicejos=new ArrayList();
		for(Voice v:voices){
			voicejos.add(WebUtils.Voice2VoiceJO(v));
		}
		json=Global.gson.toJson(voicejos);
		return "json";
	}

	public Album getAlbum() {return album;}
	public void setAlbum(Album album) {this.album = album;}
	public String getVoiceIdStr() {return voiceIdStr;}
	public void setVoiceIdStr(String voiceIdStr) {this.voiceIdStr = voiceIdStr;}
	
}
