package tk.tingwode.action;

import java.util.Map;

import org.apache.struts2.interceptor.RequestAware;
import org.apache.struts2.interceptor.SessionAware;

import com.opensymphony.xwork2.ActionSupport;

public class GenericAction extends ActionSupport implements SessionAware,RequestAware{
	public Map<String, Object> session;
	public Map<String,Object> request;
	public String json;
	public String message="";
	
	public void setSession(Map<String, Object> session) {
		this.session=session;
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

	public void setRequest(Map<String, Object> arg0) {
		this.request=arg0;
	}
	

}
