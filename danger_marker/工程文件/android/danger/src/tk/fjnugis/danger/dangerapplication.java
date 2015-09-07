package tk.fjnugis.danger;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.reflect.TypeToken;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.util.HttpHelper;

import java.util.List;

/**
 * Created by Xiangyang on 2014/11/23.
 */
public class DangerApplication extends Application implements BDLocationListener {
    public LocationClient locClient;
    public LatLng location =new LatLng(26,119);//默认位置
    public List<Danger> dangerList;

    public static final String SETTING_KEY="tk.fjnugis.danger.setting";

    @Override
    public void onCreate() {
        super.onCreate();
        // 在使用Baidu SDK 各组间之前初始化 context 信息，传入 ApplicationContext
        SDKInitializer.initialize(this);
        //初始化定位模块
        initLocClient();
        //加载配置文件
        loadPreferences();
    }

    /**加载配置文件*/
    private void loadPreferences(){
        SharedPreferences settings=getSharedPreferences(SETTING_KEY,0);
        //获取服务端根路径
        String root=settings.getString(HttpHelper.ROOT_PREFERENCE_KEY,HttpHelper.ROOT);
        HttpHelper.ROOT=root;
    }

    /**初始化定位模块*/
    private void initLocClient(){
        locClient = new LocationClient(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度定位模式
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//低功耗
//        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);//仅设备
        option.setIsNeedAddress(true);//设置包含地址信息
        option.setScanSpan(LocationClientOption.MIN_SCAN_SPAN);
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        locClient.setLocOption(option);
        locClient.registerLocationListener(this);
        locClient.start();
    }

    public void updateDangerList(final DangerListUpdateListener listener){
        //从服务端获取安全隐患信息
        RequestQueue mQueue = Volley.newRequestQueue(this.getApplicationContext());
        StringRequest request=new StringRequest(HttpHelper.ROOT + "/server/Danger-getAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("调试", "MapFragent 获取数据：" + s);
                try {
                    List<Danger> list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {
                    }.getType());
                    if (list.size() == 0 || list.equals(getDangerList()))
                        return;
                    listener.onGetDangerList(list);
                    setDangerList(list);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(DangerApplication.this, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(DangerApplication.this, "无法连接服务器", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    public LocationClient getLocClient() {
        return locClient;
    }

    public void setLocClient(LocationClient locClient) {
        this.locClient = locClient;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public List<Danger> getDangerList() {
        return dangerList;
    }

    public void setDangerList(List<Danger> dangerList) {
        this.dangerList = dangerList;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation == null)
            return;
        LatLng location=new LatLng( bdLocation.getLatitude(), bdLocation.getLongitude());
        if(location.latitude!=this.location.latitude) {
            Log.d("DangerApplication", "获取到位置:" + bdLocation.getLatitude() + "," + bdLocation.getLongitude());
            this.setLocation(location);
        }
//        locClient.stop();
//        locClient.unRegisterLocationListener(this);
    }

    public interface DangerListUpdateListener{
        //刷新信息列表时的回调方法
        void onGetDangerList(List<Danger> dangerList);
    }
}