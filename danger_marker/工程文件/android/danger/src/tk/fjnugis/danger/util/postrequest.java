package tk.fjnugis.danger.util;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xiangyang on 2014/12/2.
 */
public class PostRequest extends StringRequest {
    Map<String,String> params;

    public PostRequest(String url, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
    }
    public PostRequest(String url,Map<String,String> params,Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, url, listener, errorListener);
        this.params=params;
    }

    @Override
    public Map<String, String> getParams() {
        return this.params;
    }
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> params = new HashMap<String, String>();
        params.put("Content-Type", "application/x-www-form-urlencoded");
        return params;
    }

    @Override
    public RetryPolicy getRetryPolicy()   {
        //设置请求超时时间
        RetryPolicy retryPolicy = new DefaultRetryPolicy(15000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        return retryPolicy;
    }
}
