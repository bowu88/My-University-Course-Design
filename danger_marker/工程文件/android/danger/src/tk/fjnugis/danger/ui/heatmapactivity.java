package tk.fjnugis.danger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.reflect.TypeToken;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.ui.fragment.HotMapFragment;
import tk.fjnugis.danger.ui.fragment.MapFragment;
import tk.fjnugis.danger.util.CacheHelper;
import tk.fjnugis.danger.util.HttpHelper;
import tk.fjnugis.danger.util.PostRequest;

import java.io.InputStream;
import java.util.*;

/**
 * Created by Xiangyang on 2015/3/19.
 */
public class HeatMapActivity extends Activity {
    @InjectView(R.id.mapview)
    MapView mMapView;
    BaiduMap mBaiduMap;
    HeatMap heatmap;
    @InjectView(R.id.backbtn)
    Button backButton;
    DangerApplication dangerApplication;
    private String url = HttpHelper.ROOT + "/server/Danger-getLngLatList";
    private Map<String,String> params=new HashMap<String, String>() ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dangerApplication= (DangerApplication) getApplication();
        setContentView(R.layout.activity_heatmap);
        ButterKnife.inject(this);
        //设置地图
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.zoomTo(5));
        //获取传过来的数据
        Intent intent=getIntent();
        if(intent!=null){
            Bundle bundle=  intent.getBundleExtra(HotMapFragment.KEY_OF_BUNDLE);
            String category= (String) bundle.get(HotMapFragment.KEY_OF_CATEGORY);
            String subCategory= (String) bundle.get(HotMapFragment.KEY_OF_SUB_CATEGORY);
            String tag= (String) bundle.get(HotMapFragment.KEY_OF_TAG);
            if(!(category==null||category.trim().equals("")||category.equals("不限")))
                params.put("category", category);
            if(!(subCategory==null||subCategory.trim().equals("")||subCategory.equals("不限")))
                params.put("subCategory",subCategory);
            if(!(tag==null||tag.trim().equals("")||tag.equals("不限")))
                params.put("tag", tag);
            Log.d("intent中提取的信息", category + "," + subCategory + "," + tag);
        }
        //返回按键
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HeatMapActivity.this.finish();
            }
        });
        addHeatMap();
    }

    private void addHeatMap() {
        final Handler h = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                mBaiduMap.addHeatMap(heatmap);
            }
        };
        //从缓存中获取点数据
        List<Map<String,Double>> cacheList= (List<Map<String,Double>>) CacheHelper.getDataFromCache(dangerApplication,getCacheFileName());
        if(cacheList!=null) {
            Log.d("缓存数据大小：", cacheList.size() + "");
            heatmap = new HeatMap.Builder().data(transformList(cacheList)).build();
            h.sendEmptyMessage(0);
        }
        //从服务端获取点数据
        RequestQueue mQueue = Volley.newRequestQueue(this.getApplicationContext());

        PostRequest request=new PostRequest(url,params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("调试", "HeatMapActivity 获取数据：" + s);
                try {
                    List<Map<String,Double>> list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Map<String,Double>>>() {}.getType());
                    //heatmap.removeHeatMap();
                    heatmap = new HeatMap.Builder().data(transformList(list)).build();
                    h.sendEmptyMessage(0);
                    CacheHelper.saveDataToCache(dangerApplication,getCacheFileName(),list);
                    Toast.makeText(HeatMapActivity.this,"数据已更新",Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(HeatMapActivity.this, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(HeatMapActivity.this, "无法连接服务器", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    /**
     * 转换list数据类型
     * @param list
     * @return
     */
    private List<LatLng> transformList(List<Map<String,Double>> list){
        List<LatLng> latLngList=new ArrayList<LatLng>();
        for(Map<String,Double> map:list){
            latLngList.add(new LatLng(map.get("latitude"),map.get("longitude")));
        }
        return latLngList;
    }

    private String getCacheFileName(){
        return url+params.toString();
    }
}