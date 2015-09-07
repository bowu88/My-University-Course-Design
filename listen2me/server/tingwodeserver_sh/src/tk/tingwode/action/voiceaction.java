package tk.tingwode.action;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import tk.tingwode.client.domain.VoiceJO;
import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;
import tk.tingwode.service.BusinessService;
import tk.tingwode.utils.FileUtil;
import tk.tingwode.utils.Global;
import tk.tingwode.utils.WebUtils;

import com.google.gson.Gson;

public class VoiceAction extends GenericAction {

	BusinessService service = new BusinessService();
	private Voice voice;

	private File[] upload;
	private String[] uploadFileName;
	private String[] uploadContentType;
	
	/** 发布声音 */
	public String publish() {
		User user = (User) super.session.get("user");
		if (user == null) {
			message = "请登录";
			return SUCCESS;
		}
		try{
			for(int i=0;upload==null||i<upload.length;i++){
				if(uploadContentType[i].startsWith("image")){
					//文件为图片
					String savePath = ServletActionContext.getServletContext()
							.getRealPath("/WEB-INF/upload/picture");
					String saveName = user.getUsername() + "_" +voice.getTitle()+"_"+ uploadFileName[i];
					File savefile = new File(savePath, saveName);
					if (!savefile.getParentFile().exists())
						savefile.getParentFile().mkdirs();
					FileUtils.copyFile(upload[i], savefile);
					voice.setPicture_url("/WEB-INF/upload/picture/"+saveName);
				}else if(uploadContentType[i].startsWith("audio")){
					//文件为音频
					String saveName = user.getUsername() + "_" + voice.getTitle()+"_"+uploadFileName[i];
					String savePath = ServletActionContext.getServletContext()
							.getRealPath("/WEB-INF/upload/audio");
					File savefile = new File(savePath, saveName);
					if (!savefile.getParentFile().exists())
						savefile.getParentFile().mkdirs();
					FileUtils.copyFile(upload[i], savefile);
//					String savePath = ServletActionContext.getServletContext()
//							.getResource("/WEB-INF/upload/audio/").getFile();
//					File savefile = new File(savePath, saveName);
//					if (!savefile.getParentFile().exists())
//						savefile.getParentFile().mkdirs();
//					FileUtils.copyFile(upload[i], savefile);
					voice.setAudio_url("/WEB-INF/upload/audio/"+saveName);
				}
			}
			service.publishVoice(user.getId(), voice);
			message = "发布成功！";
			voice = null;
		}catch(Exception e){
			e.printStackTrace();
			super.message="发布失败";
			throw new RuntimeException(e);
		}
		return SUCCESS;
	}

	/** 获取所有声音 */
	public String getAll() {
		List<Voice> voices = service.findAllVoice();
		List<VoiceJO> voicejos = new ArrayList();
		for (Voice voice : voices) {
			voicejos.add(WebUtils.Voice2VoiceJO(voice));
		}
		Gson gson = Global.gson;
		json = gson.toJson(voicejos);
		return "json";
	}
	
	/**获取用户所有的声音*/
	public String getMyVoice(){
		User user=(User) session.get("user");
		if(user==null){
			message="请登录";
			return SUCCESS;
		}
		List<Voice> voices=service.findAllVoiceOfUser(user.getId());
		List<VoiceJO> voicejos=new ArrayList();
		for(Voice v:voices){
			voicejos.add(WebUtils.Voice2VoiceJO(v));
		}
		json=Global.gson.toJson(voicejos);
		return "json";
	}
	
	/**删除声音*/
	public String delete(){
		if(voice.getId()==null){
			message="无法获取声音id";
			return SUCCESS;
		}
		try{
			Voice v=service.findVoice(voice.getId());
			service.deleteVoice(v);
			//删除声音的音频文件和图片文件
			FileUtil.delete(ServletActionContext.getServletContext().getRealPath(v.getAudio_url()));
			FileUtil.delete(ServletActionContext.getServletContext().getRealPath(v.getPicture_url()));
			message="删除成功";
		}catch(Exception e){
			e.printStackTrace();
			message="删除失败";
		}
		return SUCCESS;
	}

	public Voice getVoice() {
		return voice;
	}

	public void setVoice(Voice voice) {
		this.voice = voice;
	}

	public File[] getUpload() {
		return upload;
	}

	public void setUpload(File[] upload) {
		this.upload = upload;
	}

	public String[] getUploadFileName() {
		return uploadFileName;
	}

	public void setUploadFileName(String[] uploadFileName) {
		this.uploadFileName = uploadFileName;
	}

	public String[] getUploadContentType() {
		return uploadContentType;
	}

	public void setUploadContentType(String[] uploadContentType) {
		this.uploadContentType = uploadContentType;
	}


}
