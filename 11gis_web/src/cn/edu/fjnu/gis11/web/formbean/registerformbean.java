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
	/*private String type;	����Ϊ�գ�
	private String name;	����Ϊ�գ����ܰ��������������
	private String email;	����Ϊ�գ�Ҫ��һ���Ϸ�������\\w+@\\w+(\\.\\w+)+
	private String password;����Ϊ6-16���ַ������ܰ����ո�
	private String password2;��������Ҫһ��
	private String gender;	����Ϊ��
	*/
	//У���
	public boolean validata(String checkcode){
		boolean flag=true;
		if(terms==null){
			flag=false;
			errors.put("terms", "����ܷ�������");
		}
		if(type==null){
			flag=false;
			errors.put("type", "��ѡ���½����");
		}
		if(email==null||email.trim().length()==0){
			flag=false;
			errors.put("email", "���䲻��Ϊ��");
		}else if(!email.matches("\\w+@\\w+(\\.\\w+)+")){
			flag=false;
			errors.put("email", "��������䲻�Ϸ�");
		}
		if(password==null||password.trim().equals("")){
			flag=false;
			errors.put("password", "���벻��Ϊ��");
		}else if(!password.matches("[\\S]{6,16}")){
			flag=false;
			errors.put("password", "�������Ϊ6-16λ�ķǿ��ַ�");
		}else{
			if(!password.equals(password2)){
				flag=false;
				errors.put("password2", "������������벻һ��");
			}
		}
		if(name==null||name.trim().equals("")){
			flag=false;
			errors.put("name", "��������Ϊ��");
		}
		if(gender==null){
			flag=false;
			errors.put("gender", "��ѡ���Ա�");
		}
		/*private String hometown;����Ϊ��
		private String address;	����Ϊ��
		private String birthday;����Ϊ��
		private String cellphone;����Ϊ��
		private String home_phone;��Ϊ�գ���Ϊ�ձ���Ϊ������
		private String direction;����Ϊ��
		private String description;����Ϊ��*/
		if(hometown==null||hometown.trim().length()==0){
			flag=false;
			errors.put("hometown", "���᲻��Ϊ��");
		}
		if(birthday==null||birthday.trim().length()==0){
			flag=false;
			errors.put("birthday", "���ղ���Ϊ��");
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
			errors.put("cellphone", "�ֻ����벻��Ϊ��");
		}else if(!cellphone.matches("[\\d]{11}")){
			flag=false;
			errors.put("cellphone", "��������ʵ���ֻ�����");
		}
		if(home_phone!=null&&home_phone.trim().length()!=0){
			if(!home_phone.matches("\\d+")){
				flag=false;
				errors.put("home_phone", "��������");
			}
		}
		if(direction==null){
			flag=false;
			errors.put("direction", "��ѡ�������о�����");
		}
		if(description!=null){
			if(description.length()>100){
				flag=false;
				errors.put("description","��ע���ܳ���100���ַ�");
			}
		}
		if(this.checkcode==null){
			flag=false;
			errors.put("checkcode", "��֤�벻��Ϊ��");
		}else if(!this.checkcode.equals(checkcode)){
			flag=false;
			errors.put("checkcode", "��֤�����");
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
