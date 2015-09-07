package tk.fjnugis.dangerserver.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * Created by Xiangyang on 2015/1/11.
 */
@Component
@Scope("prototype")
public class ImageAction extends ActionSupport{
    //downloaPath属性用于封装被下载文件的路径
    private String imagePath;

    //初始化要保存的文件名
    private String filename="未命名";
    private String fileType="image/jpg";

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    // 隐含属性 targetFile ,用于封装下载文件
    public InputStream getInputStream() throws FileNotFoundException, UnsupportedEncodingException {
        String path = new String(this.imagePath.getBytes("ISO-8859-1"),"UTF-8");
        path="/WEB-INF/upload/picture/"+path;
        this.filename=path.substring(path.lastIndexOf("_")+1);
        this.fileType="image/"+path.substring(path.lastIndexOf(".")+1);
        String realpath= ServletActionContext.getServletContext().getRealPath(path);
        return new FileInputStream(realpath);
    }
}
