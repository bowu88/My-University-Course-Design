package tk.fjnugis.danger.util;

import android.content.Context;
import android.util.Log;
import tk.fjnugis.danger.DangerApplication;

import java.io.*;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 缓存工具
 * Created by Xiangyang on 2015/3/26.
 */
public class CacheHelper {
    /**
     * 保存
     * @param app
     * @param filename
     * @param object
     */
    public static void saveDataToCache(DangerApplication app, String filename,
                                        Object object) {
        if (app != null && object != null) {
            // filename += app.getLoginUserInfo().getUid();
            filename = md5(filename);//加密文件名
            File file = new File(filename);
            try {
                if (file.exists()) {
                    file.delete();
                }
                FileOutputStream ops = app.openFileOutput(filename,
                        Context.MODE_PRIVATE);
                ObjectOutputStream outputStream = null;
                outputStream = new ObjectOutputStream(ops);
                outputStream.writeObject(object);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                Log.w("saveDataToCache file is :" + filename, e.toString());
            }
        }
    }

    /**
     * 读取缓存 honaf
     *
     * @param app
     * @param filename
     * @return
     */
    public static Object getDataFromCache(DangerApplication app, String filename) {
        Object obj = null;
        if (app != null) {
            // filename += app.getLoginUserInfo().getUid();
            filename = md5(filename);//加密文件名
            try {
                InputStream ies = app.openFileInput(filename);
                ObjectInputStream obi = new ObjectInputStream(ies);
                obj = obi.readObject();
                obi.close();
                ies.close();
            } catch (Exception e) {
                Log.w("getDataFromCache file is :" + filename, e.toString());
            }
        }
        return obj;
    }

    public static  String md5(String fileName){
        String reStr = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = md5.digest(fileName.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : bytes){
                int bt = b&0xff;
                if (bt < 16){
                    stringBuffer.append(0);
                }
                stringBuffer.append(Integer.toHexString(bt));
            }
            reStr = stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return reStr;
    }
}
