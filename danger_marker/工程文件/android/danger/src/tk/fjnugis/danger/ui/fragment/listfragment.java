package tk.fjnugis.danger.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.google.gson.reflect.TypeToken;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.ui.DangerInfoActivity;
import tk.fjnugis.danger.ui.DangerListActivity;
import tk.fjnugis.danger.ui.DangerListAdapter;
import tk.fjnugis.danger.ui.MenuActivity;
import tk.fjnugis.danger.util.CacheHelper;
import tk.fjnugis.danger.util.DangerInfoHelper;
import tk.fjnugis.danger.util.HttpHelper;
import tk.fjnugis.danger.util.PostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/1/4.
 */
public class ListFragment extends Fragment implements AdapterView.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener, Response.ErrorListener {
    private DangerApplication application;
    private MenuActivity menuActivity;
    private List<Danger> dangerList;
    private String url = HttpHelper.ROOT + "/server/Danger-getList";
    private Map<String, String> params=new HashMap<String, String>();
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.listView)
    ListView listView;
    DangerListAdapter adapter;
    // ListView底部View
    View moreView;
    Button bt;
    ProgressBar pg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        menuActivity = (MenuActivity) getActivity();
        application = (DangerApplication) this.getActivity().getApplication();
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.inject(this, parentView);
        // 实例化底部"加载更多布局
        moreView = menuActivity.getLayoutInflater().inflate(R.layout.moredata, null);
        bt = (Button) moreView.findViewById(R.id.bt_load);
        pg = (ProgressBar) moreView.findViewById(R.id.pg);
        listView.addFooterView(moreView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                bt.setVisibility(View.GONE);// 按钮不可见
                getMoreData();
            }
        });
        // 顶部刷新的样式
        swipeLayout.setColorScheme(R.color.white, R.color.blue,
                R.color.white, R.color.blue);
        swipeLayout.setOnRefreshListener(this);
        //获取缓存中的数据
        dangerList= (List<Danger>) CacheHelper.getDataFromCache(application, url);
        if(dangerList==null)
            dangerList=new ArrayList<Danger>();
        adapter = new DangerListAdapter(this.getActivity(), dangerList);
        adapter.setLocation(((DangerApplication) getActivity().getApplicationContext()).location);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        params.put("count", 20 + "");
        params.put("danger.longitude", application.getLocation().longitude + "");
        params.put("danger.latitude",application.getLocation().latitude+"");
        getDate();
        return parentView;
    }

    private void getDate(){
        swipeLayout.setRefreshing(true);
        params.put("startIndex","0");
        RequestQueue mQueue = Volley.newRequestQueue(menuActivity.getApplicationContext());
        PostRequest request=new PostRequest(url,params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("标签云-DangerListActivity", "获取数据：" + s);
                //关闭进度条
                swipeLayout.setRefreshing(false);
                try {
                    dangerList= HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {}.getType());
                    CacheHelper.saveDataToCache(application, url, dangerList);
                    adapter.setDangerList(dangerList);
                    adapter.notifyDataSetChanged();
                    showLoadMoreButton();
                    Toast.makeText(menuActivity, "数据已更新", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(menuActivity, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, this);
        mQueue.add(request);
        mQueue.start();
    }

    private void getMoreData() {
        RequestQueue mQueue = Volley.newRequestQueue(menuActivity.getApplicationContext());
        params.put("startIndex", dangerList.size() + "");
        PostRequest request = new PostRequest(url, params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("标签云-DangerListActivity", "加载更多：" + s);
                try {
                    List list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {
                    }.getType());
                    dangerList.addAll(list);
                    showLoadMoreButton();
                    if (list.size() == 0) {
                        bt.setVisibility(View.GONE);
                        Toast.makeText(menuActivity, "无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(menuActivity, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, this);
        mQueue.add(request);
        mQueue.start();
    }

    @Override
    public void onRefresh() {
        getDate();
    }

    private void showLoadMoreButton() {
        if (dangerList.size() % 20 == 0) {
            bt.setVisibility(View.VISIBLE);// 按钮可见
        }
        pg.setVisibility(View.GONE);// 进度条不可见
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到详情
        Activity parentActivity = ListFragment.this.getActivity();
        Intent intent = new Intent(parentActivity, DangerInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MapFragment.KEY_OF_DANGER_INFO, this.dangerList.get(position));
        intent.putExtra(MapFragment.KEY_OF_DANGER_INFO, bundle);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        volleyError.printStackTrace();
        swipeLayout.setRefreshing(false);
        Toast.makeText(menuActivity, "无法连接服务器", Toast.LENGTH_LONG).show();
    }
}