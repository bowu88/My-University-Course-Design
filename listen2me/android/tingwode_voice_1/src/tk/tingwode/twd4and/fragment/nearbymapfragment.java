package tk.tingwode.twd4and.fragment;

import tk.tingwode.twd4and.BMapApplication;
import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.utils.BMapUtil;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.MyLocationOverlay.LocationMode;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class NearbyMapFragment extends Fragment {
	View view;
	
	private enum E_BUTTON_TYPE {
		LOC,
		COMPASS,
		FOLLOW
	}
	
	private E_BUTTON_TYPE mCurBtnType;

	LocationClient mLocClient;
	LocationData locData = null;
	public MyLocationListenner myListener = new MyLocationListenner();
	
	locationOverlay myLocationOverlay = null;
	private PopupOverlay   pop  = null;
	private TextView  popupText = null;
	private View viewCache = null;
	
	MyLocationMapView mMapView = null;	
	private MapController mMapController = null;

	Button requestLocButton = null;
	boolean isRequest = false;
	boolean isFirstLoc = true;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_locationoverlay, container, false);
		requestLocButton = (Button)view.findViewById(R.id.button1);
        mCurBtnType = E_BUTTON_TYPE.LOC;
        OnClickListener btnClickListener = new OnClickListener() {
        	public void onClick(View v) {
				switch (mCurBtnType) {
				case LOC:
					//手动定位请求
					requestLocClick();
					break;
				case COMPASS:
					myLocationOverlay.setLocationMode(LocationMode.NORMAL);
					requestLocButton.setText("定位");
					mCurBtnType = E_BUTTON_TYPE.LOC;
					break;
				case FOLLOW:
					myLocationOverlay.setLocationMode(LocationMode.COMPASS);
					requestLocButton.setText("罗盘");
					mCurBtnType = E_BUTTON_TYPE.COMPASS;
					break;
				}
			}
		};
	    requestLocButton.setOnClickListener(btnClickListener);
	    
        
        mMapView = (MyLocationMapView)view.findViewById(R.id.bmapView);
        mMapController = mMapView.getController();
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
        createPaopao();
        
        mLocClient = new LocationClient( this.getActivity() );
        locData = new LocationData();
        mLocClient.registerLocationListener( myListener );
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);
        option.setCoorType("bd09ll"); 
        option.setScanSpan(1000);
        mLocClient.setLocOption(option);
        mLocClient.start();
       
		myLocationOverlay = new locationOverlay(mMapView);
	    myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		mMapView.refresh();
		
		return view;
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BMapApplication app = (BMapApplication)this.getActivity().getApplication();
        if (app.mBMapManager == null) {
            app.mBMapManager = new BMapManager(getActivity().getApplicationContext());
            app.mBMapManager.init(BMapApplication.strKey,new BMapApplication.MyGeneralListener());
        }
        
    }
    public void requestLocClick(){
    	isRequest = true;
        mLocClient.requestLocation();
        Toast.makeText(getActivity(), "正在定位……", Toast.LENGTH_SHORT).show();
    }
    public void modifyLocationOverlayIcon(Drawable marker){
    	myLocationOverlay.setMarker(marker);
    	mMapView.refresh();
    }
	public void createPaopao(){
		viewCache = getActivity().getLayoutInflater().inflate(R.layout.custom_text_view, null);
        popupText =(TextView) viewCache.findViewById(R.id.textcache);
        PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {
				Log.v("click", "clickapoapo");
			}
        };
        pop = new PopupOverlay(mMapView,popListener);
        MyLocationMapView.pop = pop;
	}
    public class MyLocationListenner implements BDLocationListener {
    	
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();
            locData.longitude = location.getLongitude();
            locData.accuracy = location.getRadius();
            locData.direction = location.getDerect();
            myLocationOverlay.setData(locData);
            mMapView.refresh();
            if (isRequest || isFirstLoc){
            	Log.d("LocationOverlay", "receive location, animate to it");
                mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
                isRequest = false;
                myLocationOverlay.setLocationMode(LocationMode.FOLLOWING);
				requestLocButton.setText("跟随");
                mCurBtnType = E_BUTTON_TYPE.FOLLOW;
            }
            isFirstLoc = false;
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
  	public class locationOverlay extends MyLocationOverlay{

  		public locationOverlay(MapView mapView) {
  			super(mapView);
  		}
  		@Override
  		protected boolean dispatchTap() {
  			popupText.setBackgroundResource(R.drawable.popup);
			popupText.setText("我的位置");
			pop.showPopup(BMapUtil.getBitmapFromView(popupText),
					new GeoPoint((int)(locData.latitude*1e6), (int)(locData.longitude*1e6)),
					8);
  			return true;
  		}
  		
  	}
}

class MyLocationMapView extends MapView{
	static PopupOverlay   pop  = null; 
	public MyLocationMapView(Context context) {
		super(context);
	}
	public MyLocationMapView(Context context, AttributeSet attrs){
		super(context,attrs);
	}
	public MyLocationMapView(Context context, AttributeSet attrs, int defStyle){
		super(context, attrs, defStyle);
	}
	@Override
    public boolean onTouchEvent(MotionEvent event){
		if (!super.onTouchEvent(event)){
			if (pop != null && event.getAction() == MotionEvent.ACTION_UP)
				pop.hidePop();
		}
		return true;
	}
}