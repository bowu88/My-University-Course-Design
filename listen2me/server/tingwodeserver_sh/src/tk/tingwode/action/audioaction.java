package tk.tingwode.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

public class AudioAction extends ActionSupport{
	//downloaPath属性用于封装被下载文件的路径  
    private String audioPath;  

    //初始化要保存的文件名  
    private String filename="未命名";  
    private String fileType="audio/mp3";
   
    public String getFilename() {  
           return filename;  
    }  

    public void setFilename(String filename) {  
           this.filename = filename;  
    }  

	public String getAudioPath() {
		return audioPath;
	}

	public void setAudioPath(String audioPath) {
		this.audioPath = audioPath;
	}

	// 隐含属性 targetFile ,用于封装下载文件
	public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException {
		String path = new String(this.audioPath.getBytes("ISO-8859-1"),"UTF-8");
		this.filename=path.substring(path.lastIndexOf("_")+1);
		this.fileType="audio/"+path.substring(path.lastIndexOf(".")+1);
		String realpath=ServletActionContext.getServletContext().getRealPath(path);
		return new FileInputStream(realpath);
	}

}
