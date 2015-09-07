package tk.tingwode.action;

import java.util.List;

import tk.tingwode.domain.User;
import tk.tingwode.domain.Voice;
import tk.tingwode.service.BusinessService;

public class PageAction extends GenericAction{
	private BusinessService service=new BusinessService();
	
	public String myVoice(){
		User user=(User) session.get("user");
		if(user==null){
			message="请登录";
			return SUCCESS;
		}
		List<Voice> voices=service.findAllVoiceOfUser(user.getId());
		request.put("voices", voices);
		return SUCCESS;
	}
}
