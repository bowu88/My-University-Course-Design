package tk.fjnugis.danger.util;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
/**
 * Created by Xiangyang on 2015/1/13.
 */
public class MultiPartStringRequest extends Request<String> implements MultiPartRequest{

    private final Listener<String> mListener;
    /* To hold the parameter name and the File to upload */
    private Map<String,File> fileUploads = new HashMap<String,File>();

    /* To hold the parameter name and the string content to upload */
    private Map<String,String> stringUploads = new HashMap<String,String>();

    /**
     * Creates a new request with the given method.
     *
     * @param method the request {@link Method} to use
     * @param url URL to fetch the string at
     * @param listener Listener to receive the String response
     * @param errorListener Error listener, or null to ignore errors
     */
    public MultiPartStringRequest(int method, String url, Listener<String> listener,
                                  ErrorListener errorListener) {
        super(method, url, errorListener);
        mListener = listener;
    }



    public void addFileUpload(String param,File file) {
        fileUploads.put(param,file);
    }

    public void addStringUpload(String param,String content) {
        stringUploads.put(param,content);
    }

    /**
     * 要上传的文件
     */
    public Map<String,File> getFileUploads() {
        return fileUploads;
    }

    /**
     * 要上传的参数
     */
    public Map<String,String> getStringUploads() {
        return stringUploads;
    }


    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(String response) {
        if(mListener != null) {
            mListener.onResponse(response);
        }
    }

    /**
     * 空表示不上传
     */
    public String getBodyContentType() {
        return null;
    }
}