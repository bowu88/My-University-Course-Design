package tk.fjnugis.danger.ui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import com.baidu.mapapi.map.offline.MKOLUpdateElement;
import com.baidu.mapapi.map.offline.MKOfflineMap;
import com.baidu.mapapi.map.offline.MKOfflineMapListener;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.ui.CityHelper;
import tk.fjnugis.danger.ui.CityListAdapter;
import tk.fjnugis.danger.ui.SectionListAdapter;
import tk.fjnugis.danger.ui.MenuActivity;

import java.util.*;
import java.util.zip.Inflater;

/**
 * Created by Xiangyang on 2015/4/14.
 */
public class OfflineMapFragment extends Fragment implements MKOfflineMapListener, CityListAdapter.CityClickListener {
    MenuActivity menuActivity;
    ViewHolder viewHolder =new ViewHolder();
    public MKOfflineMap mOffline = null;
    //存放城市列表
    LinkedHashMap<String, List<MKOLSearchRecord>> map=new LinkedHashMap<String, List<MKOLSearchRecord>>();
    /**已下载的离线地图信息列表*/
    ArrayList<MKOLUpdateElement> localMapList = null;
    DownloadListAdapter downloadListAdapter=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.menuActivity= (MenuActivity) getActivity();
        mOffline = new MKOfflineMap();
        mOffline.init(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(savedInstanceState!=null)
            return super.onCreateView(inflater,container,savedInstanceState);
        View parentView=inflater.inflate(R.layout.fragment_offline_map, container, false);
        ButterKnife.inject(viewHolder,parentView);
        viewHolder.searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchCity();
            }
        });
        viewHolder.tabHost.setup();
        addTab("tab1", "城市列表", R.id.linearLayout);
        addTab("tab2","下载管理",R.id.linearLayout2);
        initCityList();
        return parentView;
    }

    //添加tab
    private void addTab(String tag,String title, int contextId){
        TabHost.TabSpec tab = viewHolder.tabHost.newTabSpec(tag);
        tab.setIndicator(getTabIndicator(viewHolder.tabHost.getContext(), title));
        tab.setContent(contextId);
        viewHolder.tabHost.addTab(tab);
    }

    //设置tab样式
    private View getTabIndicator(Context context, String title) {
        View view = LayoutInflater.from(context).inflate(R.layout.tab_layout, null);
        TextView tv = (TextView) view.findViewById(R.id.textView);
        tv.setText(title);
        return view;
    }

    /**
     * 初始化城市列表
     */
    private void initCityList(){
        map.put("热门城市",mOffline.getHotCityList());
        map.put("全国各省",mOffline.getOfflineCityList());
        CityListAdapter adapter=new CityListAdapter(menuActivity,map);
        adapter.setCityClickListener(this);
        viewHolder.cityListView.setAdapter(adapter);
        //初始化已下载城市列表
        localMapList=mOffline.getAllUpdateInfo();
        downloadListAdapter=new DownloadListAdapter();
        viewHolder.localMapListView.setAdapter(downloadListAdapter);
    }

    /**搜索城市*/
    public void searchCity(){
        ArrayList<MKOLSearchRecord> records = mOffline.searchCity(viewHolder.cityNameView.getText().toString());
        if(records.size()>0){
            Map<String, List<MKOLSearchRecord>> map =new HashMap<String, List<MKOLSearchRecord>>();
            map.put("搜索结果",records);
            CityListAdapter adapter= new CityListAdapter(menuActivity,map);
            adapter.setCityClickListener(this);
            viewHolder.searchResultListView.setAdapter(adapter);
        }else {
            Toast.makeText(menuActivity,"未搜索到结果",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onDownloadButtonClick(View view) {
        //下载地图
        MKOLSearchRecord city= (MKOLSearchRecord) view.getTag();
        mOffline.start(city.cityID);
        //切换到下载管理
        viewHolder.tabHost.setCurrentTabByTag("tab2");
        Toast.makeText(menuActivity,"开始下载",Toast.LENGTH_SHORT).show();
        updateView();
    }

    /**
     * 更新状态显示
     */
    public void updateView() {
        localMapList = mOffline.getAllUpdateInfo();
        if (localMapList == null) {
            localMapList = new ArrayList<MKOLUpdateElement>();
        }
        downloadListAdapter.notifyDataSetChanged();
    }

    public String formatDataSize(int size) {
        String ret = "";
        if (size < (1024 * 1024)) {
            ret = String.format("%dK", size / 1024);
        } else {
            ret = String.format("%.1fM", size / (1024 * 1024.0));
        }
        return ret;
    }

    @Override
    public void onGetOfflineMapState(int type, int state) {
        switch (type) {
            case MKOfflineMap.TYPE_DOWNLOAD_UPDATE: {
                MKOLUpdateElement update = mOffline.getUpdateInfo(state);
                //处理下载进度更新提示
                if (update != null) {
//                    stateView.setText(String.format("%s : %d%%", update.cityName,update.ratio));
                    updateView();
                }
            }
            break;
            case MKOfflineMap.TYPE_NEW_OFFLINE:
                // 有新离线地图安装
                Log.d("OfflineMapFragment", String.format("add offlinemap num:%d", state));
                break;
            case MKOfflineMap.TYPE_VER_UPDATE:
                // 版本更新提示
                // MKOLUpdateElement e = mOffline.getUpdateInfo(state);

                break;
        }
    }

    @Override
    public void onPause() {
        //暂停下载
        for(MKOLUpdateElement record:localMapList){
            if (record != null && record.status == MKOLUpdateElement.DOWNLOADING) {
                mOffline.pause(record.cityID);
            }
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        //启动下载
        for(int i=0;localMapList!=null&&i<localMapList.size();i++){
            MKOLUpdateElement record=localMapList.get(i);
            if (record != null && record.status == MKOLUpdateElement.SUSPENDED) {
                mOffline.start(record.cityID);
                updateView();
            }
        }
        super.onResume();
    }

    @Override
    public void onDestroy() {
        /**
         * 退出时，销毁离线地图模块
         */
        mOffline.destroy();
        super.onDestroy();
    }

    class ViewHolder{
        @InjectView(R.id.tabHost)
        TabHost tabHost;
        @InjectView(R.id.citylist)
        ListView cityListView;
        @InjectView(R.id.localMapListView)
        ListView localMapListView;
        @InjectView(R.id.cityName)
        EditText cityNameView;
        @InjectView(R.id.searchResultListView)
        ListView searchResultListView;
        @InjectView(R.id.search)
        Button searchButton;
    }

    public class DownloadListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if(localMapList==null)
                return 0;
            return localMapList.size();
        }

        @Override
        public Object getItem(int index) {
            return localMapList.get(index);
        }

        @Override
        public long getItemId(int index) {
            return index;
        }

        @Override
        public View getView(int index, View view, ViewGroup arg2) {
            MKOLUpdateElement e = (MKOLUpdateElement) getItem(index);
            view = LayoutInflater.from(menuActivity).inflate(R.layout.item_downloaded_city, null);
            initViewItem(view, e);
            return view;
        }

        void initViewItem(final View view, final MKOLUpdateElement e) {
            ImageView remove = (ImageView) view.findViewById(R.id.remove);
            TextView title = (TextView) view.findViewById(R.id.title);
            TextView update = (TextView) view.findViewById(R.id.update);
            TextView ratio = (TextView) view.findViewById(R.id.ratio);
            final ImageView pause=(ImageView)view.findViewById(R.id.pause);
            final ImageView start=(ImageView)view.findViewById(R.id.start);
            ratio.setText(e.ratio + "%");
            title.setText(e.cityName);
            if (e.update) {
                update.setText("可更新");
            } else {
                update.setText("最新");
            }
            if (e.ratio != 100 ) {
                if(e.status==e.WAITING){
                    ratio.setText("等待下载");
                    Log.d("离线地图","等待下载");
                }
                Log.d("离线地图","下载状态："+e.status);
                if(e.status==e.DOWNLOADING) {
                    pause.setVisibility(View.VISIBLE);
                }else if(e.status==e.SUSPENDED){
                    start.setVisibility(View.VISIBLE);
                }
                pause.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOffline.pause(e.cityID);
                        pause.setVisibility(View.GONE);
                        start.setVisibility(View.VISIBLE);
                        updateView();
                    }
                });
                start.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mOffline.start(e.cityID);
                        start.setVisibility(View.GONE);
                        pause.setVisibility(View.VISIBLE);
                    }
                });
            }
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    //提示是否删除
                    AlertDialog.Builder builder=new AlertDialog.Builder(menuActivity);
                    builder.setMessage("确认删除"+e.cityName+"离线地图？");
                    builder.setTitle("提示");
                    builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            mOffline.remove(e.cityID);
                            updateView();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.create().show();
                }
            });
        }

    }
}