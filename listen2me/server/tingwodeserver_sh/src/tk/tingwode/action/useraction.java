package tk.tingwode.action;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.struts2.ServletActionContext;

import tk.tingwode.domain.User;
import tk.tingwode.service.BusinessService;
import tk.tingwode.utils.Global;
import tk.tingwode.utils.WebUtils;

import com.google.gson.Gson;

public class UserAction extends GenericAction {
	BusinessService service = new BusinessService();
	private User user;

	// 上传照片相关属性
	private File photo;
	private String photoFileName;
	private String photoContentType;

	private String json;
	private String message = "";

	public String add() {
		try {
			
			if (photo != null) {
				// 上传照片
				String savePath = ServletActionContext.getServletContext()
						.getRealPath("/WEB-INF/upload/photo");
				String saveName = user.getUsername() + "_" + photoFileName;
				File savefile = new File(savePath, saveName);
				if (!savefile.getParentFile().exists())
					savefile.getParentFile().mkdirs();
				FileUtils.copyFile(photo, savefile);
				user.setPhoto_url(saveName);
			}

			// 注册用户
			service.addUser(user);
			message = "注册成功";
			user = null;
		} catch (Exception e) {
			e.printStackTrace();
			message = "注册失败";
		}
		return SUCCESS;
	}

	public String find() {
		user = service.findUser(user.getId());
		Gson gson = Global.gson;
		json = gson.toJson(user);
		user = null;
		return "json";
	}

	/** 登录功能 */
	public String login() {
		user = service.findUser(user.getEmail(), user.getPassword());
		if (user == null) {
			message = "用户名或密码错误";
			return SUCCESS;
		}
		super.session.put("user", user);
		Gson gson = Global.gson;
		json = gson.toJson(WebUtils.User2UserJO(user));
		System.out.println(new Date().toLocaleString()+"：  用户\""+user.getUsername()+"\"已登录");
		user = null;
		return "json";
	}
	
	/**注销功能*/
	public String logout(){
		ServletActionContext.getRequest ().getSession().removeAttribute("user");
		message="注销成功";
		return SUCCESS;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public String getJson() {
		return json;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public File getPhoto() {
		return photo;
	}

	public void setPhoto(File photo) {
		this.photo = photo;
	}

	public String getPhotoFileName() {
		return photoFileName;
	}

	public void setPhotoFileName(String photoFileName) {
		this.photoFileName = photoFileName;
	}

	public String getPhotoContentType() {
		return photoContentType;
	}

	public void setPhotoContentType(String photoContentType) {
		this.photoContentType = photoContentType;
	}

}
