package tk.fjnugis.danger.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.*;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.*;
import com.google.gson.reflect.TypeToken;
import com.special.ResideMenu.ResideMenu;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.ui.DangerInfoActivity;
import tk.fjnugis.danger.ui.MenuActivity;
import tk.fjnugis.danger.util.HttpHelper;
import tk.fjnugis.danger.util.PostRequest;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapFragment extends Fragment {
    DangerApplication application;
    MenuActivity menuActivity;
    private ResideMenu resideMenu;
    private String className="MapFragment";
    BaiduMap mBaiduMap;
    RequestQueue mQueue;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;
    BitmapDescriptor mCurrentMarker;
    boolean isFirstLoc = true;// 是否首次定位
    Bitmap marker_bm;
    BitmapDescriptor marker_ic = BitmapDescriptorFactory.fromResource(R.drawable.marker);
   // BitmapDescriptor marker_ic =BitmapDescriptorFactory.fromBitmap(marker_bm);
    ViewHolder viewHolder=new ViewHolder();
    //已加载Marker的id
    Map<Integer,Danger> dangerMap=new HashMap();

    /**传递给intent的实体类的key*/
    public static final String KEY_OF_DANGER_INFO="tk.fjnugis.danger.keyofdangerinfo";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("MapFragment","onCreate()");
        super.onCreate(savedInstanceState);
        //获取resideMenu
        MenuActivity parentActivity = (MenuActivity) getActivity();
        resideMenu = parentActivity.getResideMenu();
        application= (DangerApplication) parentActivity.getApplication();
        marker_bm= BitmapFactory.decodeResource(parentActivity.getApplicationContext().getResources(),R.drawable.marker);
//        viewHolder.mapView.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null) {
            Log.d("MapFragment","从保存的状态中恢复");
            return super.onCreateView(inflater, container, savedInstanceState);
        }
        Log.d("MapFragment","初始化");
        View parentView = inflater.inflate(R.layout.fragment_map, container, false);
        ButterKnife.inject(this.viewHolder, parentView);//注入View
        // 地图初始化
        mBaiduMap = viewHolder.mapView.getMap();
        // 设置底图中心和缩放级别
        LatLng centerll=new LatLng(26,119);
        float v=14f;//显示级别
        MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.newLatLngZoom(centerll,v);
        mBaiduMap.animateMapStatus(mapStatusUpdate);
        //获取resideMenu
        menuActivity = (MenuActivity) getActivity();
        resideMenu = menuActivity.getResideMenu();
        resideMenu.addIgnoredView(viewHolder.mapView);//防止移动地图时打开导航
        //地图各种监听器的初始化
        initMapListener();
        //初始化定位功能
        initLocationModule();
        //添加图标
//        addMarkers();//添加所有Marker
        MapStatus currentMapStatus=mBaiduMap.getMapStatus();
        addMarkers(currentMapStatus.bound.northeast,currentMapStatus.bound.southwest);//添加指定范围内的Marker
        //初始化跳转到上传页面功能
        initUploadWindow();
        return parentView;
    }
    /**
     * 地图各种监听器的初始化
     */
    private void initMapListener(){
        //Marker点击事件
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(final Marker marker) {
                //地图中心移动到Marker
                MapStatusUpdate mapStatus=MapStatusUpdateFactory.newLatLng(marker.getPosition());
                mBaiduMap.animateMapStatus(mapStatus);
                if(marker.getExtraInfo()==null){
                    return false;
                }
                Danger danger= (Danger) marker.getExtraInfo().get("danger");
                //创建信息窗口
                final InfoWindow infoWindow;
                Button textView=new Button(getActivity().getApplicationContext());
                textView.setBackgroundResource(R.drawable.popup);
                textView.setText(marker.getTitle());
                textView.setTextColor(Color.rgb(15, 15, 15));
                infoWindow=new InfoWindow(BitmapDescriptorFactory.fromView(textView), marker.getPosition(), -(marker_bm.getHeight()), new InfoWindow.OnInfoWindowClickListener() {
                    @Override
                    public void onInfoWindowClick() {
                        mBaiduMap.hideInfoWindow();
                        viewHolder.dangerInfoWindow.setVisibility(View.GONE);
                    }
                });
                mBaiduMap.showInfoWindow(infoWindow);
                //地图中心移动到Marker （平滑地移动）
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(marker.getPosition());
                mBaiduMap.animateMapStatus(u);
                showDangerInfo(danger);
                return false;
            }
        });
        //点击地图
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                mBaiduMap.hideInfoWindow();
                viewHolder.dangerInfoWindow.setVisibility(View.GONE);
                //隐藏上传窗口
                if(uploadMarker!=null){
                    cleanUploadMarker();
                }
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
        //地图状态改变
        mBaiduMap.setOnMapStatusChangeListener(new BaiduMap.OnMapStatusChangeListener() {
            @Override
            public void onMapStatusChangeFinish(MapStatus mapStatus) {
                addMarkers(mapStatus.bound.northeast,mapStatus.bound.southwest);
            }
            @Override
            public void onMapStatusChangeStart(MapStatus mapStatus) {}
            @Override
            public void onMapStatusChange(MapStatus mapStatus) {}
        });
    }
    //---------------------------上传位置--- Start -----------------------

    private Marker uploadMarker;
    /**
     * 初始化跳转到上传页面功能
     */
    private void initUploadWindow(){
        //长按地图跳转功能
        mBaiduMap.setOnMapLongClickListener(new BaiduMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                Log.d("MapFragment","长按地图点："+ latLng.latitude+" "+ latLng.longitude);
                addUploadMarker(latLng);
            }
        });
        //"添加"按钮点击事件
        viewHolder.uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuActivity.changeFragment(UploadFragment.class);
                UploadFragment uploadFragment= (UploadFragment) menuActivity.getFragment(UploadFragment.class);
                uploadFragment.setLocation(uploadMarker.getPosition());
                cleanUploadMarker();
            }
        });
        //Marker拖曳事件监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragEnd(Marker marker) {
                MapStatusUpdate mapStatusUpdate=MapStatusUpdateFactory.newLatLng(marker.getPosition());
                mBaiduMap.animateMapStatus(mapStatusUpdate);
            }

            @Override
            public void onMarkerDrag(Marker marker) {}
            @Override
            public void onMarkerDragStart(Marker marker) {}
        });
    }

    private void cleanUploadMarker(){
        viewHolder.uploadWindow.setVisibility(View.GONE);
        uploadMarker.remove();
        uploadMarker=null;
        Log.d("MapFragment","清除uploadMarker");
    }

    public void addUploadMarker(LatLng latLng){
        if(uploadMarker!=null){
            uploadMarker.setPosition(latLng);
        }else {
            viewHolder.uploadWindow.setVisibility(View.VISIBLE);
            OverlayOptions option = new MarkerOptions().position(latLng).icon(marker_ic).draggable(true);
            uploadMarker = (Marker) mBaiduMap.addOverlay(option);
        }
        MapStatusUpdate mapStatusUpdate = MapStatusUpdateFactory.newLatLng(uploadMarker.getPosition());
        mBaiduMap.animateMapStatus(mapStatusUpdate);
        updateUploadAddress(latLng);
    }

    public void updateUploadAddress(LatLng latLng){
        GeoCoder geoCoder = GeoCoder.newInstance();
        ReverseGeoCodeOption option = new ReverseGeoCodeOption().location(latLng);
        if (geoCoder.reverseGeoCode(option)) {
            //反地理编码发起成功
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    viewHolder.uploadAddress.setText(reverseGeoCodeResult.getAddress());
                }
                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                    viewHolder.uploadAddress.setText("无地址信息");
                }
            });
        } else {
            //反地理编码发起失败
            viewHolder.uploadAddress.setText("无地址信息");
        }
    }
    //---------------------------上传位置--- END -----------------------
    //---------------------------定位功能---START-----------------------
    /**
     * 初始化定位功能
     */
    private void initLocationModule() {
        Log.d(className,"初始化定位功能");
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
       //requestLocButton.setText("普通");
        viewHolder.requestLocButton.setBackgroundResource(R.drawable.ic_normal);
        View.OnClickListener btnClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                switch (mCurrentMode) {
                    case NORMAL:
                        //viewHolder.requestLocButton.setText("跟随");
                        Toast.makeText(getActivity(),"跟随",Toast.LENGTH_SHORT).show();
                        viewHolder.requestLocButton.setBackgroundResource(R.drawable.ic_following);
                        mCurrentMode = MyLocationConfiguration.LocationMode.FOLLOWING;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;
                    case COMPASS:
                        // viewHolder.requestLocButton.setText("普通");
                        Toast.makeText(getActivity(),"普通",Toast.LENGTH_SHORT).show();
                        viewHolder.requestLocButton.setBackgroundResource(R.drawable.ic_normal);
                        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;
                        //将地图摆正
                        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(
                                new MapStatus.Builder().rotate(0f).overlook(0f).build()
                        ));
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;
                    case FOLLOWING:
                        //viewHolder.requestLocButton.setText("罗盘");
                        Toast.makeText(getActivity(),"罗盘",Toast.LENGTH_SHORT).show();
                        viewHolder.requestLocButton.setBackgroundResource(R.drawable.ic_compass);
                        mCurrentMode = MyLocationConfiguration.LocationMode.COMPASS;
                        mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(mCurrentMode, true, mCurrentMarker));
                        break;
                }
            }
        };
        viewHolder.requestLocButton.setOnClickListener(btnClickListener);

        // 开启定位图层
        mBaiduMap.setMyLocationEnabled(true);
        // 定位初始化
        mLocClient = ((DangerApplication)getActivity().getApplication()).locClient;
        mLocClient.registerLocationListener(myListener);
        mLocClient.start();
    }

    /**
     * 定位SDK监听函数
     */
    public class MyLocationListenner implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || viewHolder.mapView == null)
                return;
           // Log.d(className,"获取到位置");
            ((DangerApplication)getActivity().getApplicationContext()).setLocation(new LatLng(location.getLatitude(),location.getLongitude()));
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                            // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            mBaiduMap.setMyLocationData(locData);
            if (isFirstLoc) {
                isFirstLoc = false;
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
                mBaiduMap.animateMapStatus(u);
            }
        }

    }
    //---------------------------------定位功能-----END-----------------------------
    //---------------------------------添加Maerker-----START------------------------
    private void addMarkers(){
        Log.d(className,"添加Marker");
        //从服务端获取安全隐患信息
        mQueue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        StringRequest request=new StringRequest(HttpHelper.ROOT + "/server/Danger-getAll", new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("调试","MapFragent 获取数据："+s);
                try {
                    List<Danger> list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {
                    }.getType());
                    if (list.size() == 0 || list.equals(application.dangerList))
                        return;
                    addDangerMarker(list);
                    application.setDangerList(list);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(getActivity()==null)
                    return;
                Toast.makeText(getActivity(), "无法连接服务器", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }
    private void addMarkers(LatLng northEast,LatLng southWest){
        Map<String,String> params=new HashMap<String, String>();
        params.put("lt_longitude", northEast.longitude + "");
        params.put("lt_latitude", southWest.latitude + "");
        params.put("rb_longitude", southWest.longitude + "");
        params.put("rb_latitude", northEast.latitude + "");
        Log.d(className,"添加Marker 范围："+params);
        //从服务端获取安全隐患信息
        mQueue = Volley.newRequestQueue(this.getActivity().getApplicationContext());
        StringRequest request=new PostRequest(HttpHelper.ROOT + "/server/Danger-getByBoundary",params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("调试","MapFragent 获取数据："+s);
                try {
                    List<Danger> list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {
                    }.getType());
                    if (list.size() == 0 || list.equals(application.dangerList))
                        return;
                    addDangerMarker(list);
                    application.setDangerList(list);
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if(getActivity()==null)
                    return;
                Toast.makeText(getActivity(), "无法连接服务器", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }
    private void addDangerMarker(List<Danger> dangerList){
        for(Danger danger:dangerList){
            if(danger!=null) {
                addDangerMarker(danger);
            }
        }
    }
    private void addDangerMarker(Danger danger){
        if(danger==null||danger.latitude==null||danger.longitude==null||danger.id==null) {
            return;
        }else if(dangerMap.keySet().contains(danger.id)&&dangerMap.get(danger.id).equals(danger)){
            return;
        }
        dangerMap.put(danger.id,danger);
        LatLng ll=new LatLng(danger.getLatitude(), danger.getLongitude());
        String title=danger.name;
        //标题太长的话截短
        if(title!=null&&title.length()>12)
            title=title.substring(0,12)+"…";
        OverlayOptions option=new MarkerOptions().position(ll).perspective(true)
                .icon(marker_ic)
                .title(title);
        Marker marker=(Marker)mBaiduMap.addOverlay(option);
        Bundle bundle=new Bundle();
        bundle.putSerializable("danger",danger);
        marker.setExtraInfo(bundle);
    }
    //显示危险信息窗口
    private void showDangerInfo(final Danger danger){
        String title=danger.name;
        //标题太长的话截短
        if(title!=null&&title.length()>12)
            title=title.substring(0,12)+"…";
        viewHolder.infoTitle.setText(title);
        //等级保留一位数
        String rank=new DecimalFormat("#.#").format(danger.rank);
        viewHolder.infoRatingBar.setRating(Float.parseFloat(rank));
        viewHolder.infoRank.setText(rank);
        //设置类别信息
        viewHolder.infoCategory.setText(danger.category+">"+danger.subCategory);
        //描述太长的话截短
        String description = danger.description;
        if(description!=null&&description.length()>70)
            description=description.substring(0,70)+"……";
        viewHolder.infoDescription.setText(description);
        viewHolder.infoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到详情
                Activity parentActivity=MapFragment.this.getActivity();
                Intent intent=new Intent(parentActivity, DangerInfoActivity.class);
                Bundle bundle=new Bundle();
                bundle.putSerializable(KEY_OF_DANGER_INFO,danger);
                intent.putExtra(KEY_OF_DANGER_INFO, bundle);
                startActivity(intent);
//                menuActivity.finish();
            }
        });
        viewHolder.dangerInfoWindow.setVisibility(View.VISIBLE);
    }
    //---------------------------------添加Maerker-----END------------------------
    @Override
    public void onDestroy() {
        Log.d("MapFragment","onDestroy");
        super.onDestroy();
        // 退出时销毁定位
        if(mLocClient!=null) {
            mLocClient.unRegisterLocationListener(this.myListener);
            mLocClient.stop();
        }
        // 关闭定位图层
        mBaiduMap.setMyLocationEnabled(false);
        viewHolder.mapView.onDestroy();
        viewHolder.mapView = null;
        //取消发送http请求
        if(mQueue!=null)
            mQueue.stop();
    }

    @Override
    public void onResume() {
        Log.d("MapFragment", "MapFragment  onResume()");
        super.onResume();
//        if(viewHolder.mapView!=null)
        viewHolder.mapView.onResume();
    }

    @Override
    public void onPause() {
        Log.d("生命周期","MapFragment  onPause()");
        super.onPause();
//        if(viewHolder.mapView!=null)
        viewHolder.mapView.onPause();
    }

    class ViewHolder{
        @InjectView(R.id.mapView)
        MapView mapView;
        @InjectView(R.id.request_location_button)
        Button requestLocButton;

        //定位信息窗口
        @InjectView(R.id.danger_info_window)
        RelativeLayout dangerInfoWindow;
        @InjectView(R.id.danger_info_window_title)
        TextView infoTitle;
        @InjectView(R.id.danger_info_window_rankbar)
        RatingBar infoRatingBar;
        @InjectView(R.id.danger_info_window_rank)
        TextView infoRank;
        @InjectView(R.id.danger_info_window_category)
        TextView infoCategory;
        @InjectView(R.id.danger_info_window_description)
        TextView infoDescription;
        @InjectView(R.id.danger_info_window_button)
        Button infoButton;
        //上传信息窗口
        @InjectView(R.id.danger_info_uploadwindow)
        RelativeLayout uploadWindow;
        @InjectView(R.id.danger_info_uploadwindow_address)
        TextView uploadAddress;
        @InjectView(R.id.danger_info_uploadwindow_button)
        Button uploadButton;
    }

}