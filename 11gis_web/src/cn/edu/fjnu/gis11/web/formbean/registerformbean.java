package cn.edu.fjnu.gis11.web.formbean;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class RegisterFormBean {
	private String type	;
	private String name;
	private String email;
	private String password;
	private String password2;
	private String gender;
	private String hometown;
	private String address;
	private String birthday;
	private String cellphone;
	private String home_phone;
	private String direction;
	private String description;
	private String checkcode;
	private String terms;
	private Map<String,String> errors=new HashMap();
	
	public String getPassword2() {
		return password2;
	}
	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	public Map<String, String> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, String> errors) {
		this.errors = errors;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getHometown() {
		return hometown;
	}
	public void setHometown(String hometown) {
		this.hometown = hometown;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getCellphone() {
		return cellphone;
	}
	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
	public String getHome_phone() {
		return home_phone;
	}
	public void setHome_phone(String home_phone) {
		this.home_phone = home_phone;
	}
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	/*private String type;	不能为空；
	private String name;	不能为空；不能包含数字特殊符号
	private String email;	不能为空；要是一个合法的邮箱\\w+@\\w+(\\.\\w+)+
	private String password;长度为6-16个字符；不能包含空格；
	private String password2;两次密码要一致
	private String gender;	不能为空
	*/
	//校验表单
	public boolean validata(String checkcode){
		boolean flag=true;
		if(terms==null){
			flag=false;
			errors.put("terms", "请接受服务条款");
		}
		if(type==null){
			flag=false;
			errors.put("type", "请选择登陆类型");
		}
		if(email==null||email.trim().length()==0){
			flag=false;
			errors.put("email", "邮箱不能为空");
		}else if(!email.matches("\\w+@\\w+(\\.\\w+)+")){
			flag=false;
			errors.put("email", "输入的邮箱不合法");
		}
		if(password==null||password.trim().equals("")){
			flag=false;
			errors.put("password", "密码不能为空");
		}else if(!password.matches("[\\S]{6,16}")){
			flag=false;
			errors.put("password", "密码必须为6-16位的非空字符");
		}else{
			if(!password.equals(password2)){
				flag=false;
				errors.put("password2", "两次输入的密码不一致");
			}
		}
		if(name==null||name.trim().equals("")){
			flag=false;
			errors.put("name", "姓名不能为空");
		}
		if(gender==null){
			flag=false;
			errors.put("gender", "请选择性别");
		}
		/*private String hometown;不能为空
		private String address;	可以为空
		private String birthday;可以为空
		private String cellphone;不能为空
		private String home_phone;可为空，不为空必须为纯数字
		private String direction;不能为空
		private String description;可以为空*/
		if(hometown==null||hometown.trim().length()==0){
			flag=false;
			errors.put("hometown", "籍贯不能为空");
		}
		if(birthday==null||birthday.trim().length()==0){
			flag=false;
			errors.put("birthday", "生日不能为空");
		}else{
			try{
				/*DateLocaleConverter conver=new DateLocaleConverter();
				conver.convert(birthday);*/
				
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				sdf.parse(birthday);
			}catch(Exception e){
				flag=false;
				errors.put("birthday", e.toString());
			}
		}
		if(cellphone==null||cellphone.trim().length()==0){
			flag=false;
			errors.put("cellphone", "手机号码不能为空");
		}else if(!cellphone.matches("[\\d]{11}")){
			flag=false;
			errors.put("cellphone", "请输入真实的手机号码");
		}
		if(home_phone!=null&&home_phone.trim().length()!=0){
			if(!home_phone.matches("\\d+")){
				flag=false;
				errors.put("home_phone", "输入有误");
			}
		}
		if(direction==null){
			flag=false;
			errors.put("direction", "请选择您的研究方向");
		}
		if(description!=null){
			if(description.length()>100){
				flag=false;
				errors.put("description","备注不能超过100个字符");
			}
		}
		if(this.checkcode==null){
			flag=false;
			errors.put("checkcode", "验证码不能为空");
		}else if(!this.checkcode.equals(checkcode)){
			flag=false;
			errors.put("checkcode", "验证码错误");
		}
		return flag;
	}
	public String getCheckcode() {
		return checkcode;
	}
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	public String getTerms() {
		return terms;
	}
	public void setTerms(String terms) {
		this.terms = terms;
	}
}
