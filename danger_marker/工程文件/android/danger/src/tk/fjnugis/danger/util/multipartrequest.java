package tk.fjnugis.danger.util;

import java.io.File;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/1/13.
 */
public interface MultiPartRequest {
    public void addFileUpload(String param,File file);

    public void addStringUpload(String param,String content);

    public Map<String,File> getFileUploads();

    public Map<String,String> getStringUploads();
}
