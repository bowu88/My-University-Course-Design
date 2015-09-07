package tk.fjnugis.danger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.reflect.TypeToken;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.model.Tag;
import tk.fjnugis.danger.ui.fragment.MapFragment;
import tk.fjnugis.danger.ui.fragment.TagsCloudFragment;
import tk.fjnugis.danger.util.CacheHelper;
import tk.fjnugis.danger.util.HttpHelper;
import tk.fjnugis.danger.util.PostRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/3/27.
 */
public class DangerListActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener,Response.ErrorListener {
    private DangerApplication application;
    private String url= HttpHelper.ROOT+"/server/Danger-getListByTag";
    private String tag;
    private Map<String,String> params=new HashMap<String,String>();
    private List<Danger> dangerList;
    @InjectView(R.id.swipe_container)
    SwipeRefreshLayout swipeLayout;
    @InjectView(R.id.listView)
    ListView listView;
    @InjectView(R.id.activity_title)
    TextView title;
    @InjectView(R.id.back)
    Button backButton; //返回按钮
    DangerListAdapter adapter;
    // ListView底部View
    View moreView;
    Button bt;
    ProgressBar pg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        application= (DangerApplication) getApplication();
        setContentView(R.layout.activity_danger_list);
        ButterKnife.inject(this);
        // 实例化底部"加载更多布局
        moreView = getLayoutInflater().inflate(R.layout.moredata, null);
        bt= (Button) moreView.findViewById(R.id.bt_load);
        pg= (ProgressBar) moreView.findViewById(R.id.pg);
        listView.addFooterView(moreView);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pg.setVisibility(View.VISIBLE);// 将进度条可见
                bt.setVisibility(View.GONE);// 按钮不可见
                getMoreData();
            }
        });

        //获取intent中的数据
        Intent intent=getIntent();
        tag= intent.getStringExtra(TagsCloudFragment.KEY_OF_TAG);
        Log.d("标签列表","标签："+tag);
        title.setText(tag);
        //点击返回按钮
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DangerListActivity.this.finish();
            }
        });
        //获取缓存中的数据
        dangerList= (List<Danger>) CacheHelper.getDataFromCache(application, url+tag);
        if(dangerList==null)
            dangerList=new ArrayList<Danger>();
        // 顶部刷新的样式
        swipeLayout.setColorScheme(R.color.white, R.color.blue,
                R.color.white, R.color.blue);
        swipeLayout.setOnRefreshListener(this);
        adapter=new DangerListAdapter(this,dangerList);
        adapter.setLocation(application.getLocation());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

        params.put("tag", tag);
        params.put("count", 20+"");
        params.put("danger.longitude",application.getLocation().longitude+"");
        params.put("danger.latitude",application.getLocation().latitude+"");
        getDangerListByTag();
    }

    private void getDangerListByTag(){
        swipeLayout.setRefreshing(true);
        params.put("startIndex","0");
        RequestQueue mQueue = Volley.newRequestQueue(this.getApplicationContext());
        PostRequest request=new PostRequest(url,params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("标签云-DangerListActivity", "获取数据：" + s);
                //关闭进度条
                swipeLayout.setRefreshing(false);
                try {
                   dangerList= HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {}.getType());
                    CacheHelper.saveDataToCache(application, url+tag, dangerList);
                    adapter.setDangerList(dangerList);
                    adapter.notifyDataSetChanged();
                    showLoadMoreButton();
                    Toast.makeText(DangerListActivity.this, "数据已更新", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(DangerListActivity.this, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, this);
        mQueue.add(request);
        mQueue.start();
    }

    private void showLoadMoreButton(){
        if(dangerList.size()%20==0){
            bt.setVisibility(View.VISIBLE);// 按钮可见
        }
        pg.setVisibility(View.GONE);// 进度条不可见
    }

    private void getMoreData(){
        RequestQueue mQueue = Volley.newRequestQueue(this.getApplicationContext());
        params.put("startIndex",dangerList.size()+"");
        PostRequest request=new PostRequest(url,params, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("标签云-DangerListActivity", "加载更多：" + s);
                try {
                    List list= HttpHelper.GSON.fromJson(s, new TypeToken<List<Danger>>() {}.getType());
                    dangerList.addAll(list);
                    showLoadMoreButton();
                    if(list.size()==0){
                        bt.setVisibility(View.GONE);
                        Toast.makeText(DangerListActivity.this, "无更多数据", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    adapter.notifyDataSetChanged();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(DangerListActivity.this, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, this);
        mQueue.add(request);
        mQueue.start();
    }



    @Override
    public void onRefresh() {
        getDangerListByTag();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //跳转到详情
        Intent intent=new Intent(this, DangerInfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable(MapFragment.KEY_OF_DANGER_INFO,dangerList.get(position));
        intent.putExtra(MapFragment.KEY_OF_DANGER_INFO, bundle);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        volleyError.printStackTrace();
        Toast.makeText(DangerListActivity.this, "无法连接服务器", Toast.LENGTH_LONG).show();
    }
}