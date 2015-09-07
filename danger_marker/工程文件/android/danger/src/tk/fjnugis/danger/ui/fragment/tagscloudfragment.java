package tk.fjnugis.danger.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.map.HeatMap;
import com.google.gson.reflect.TypeToken;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.model.Tag;
import tk.fjnugis.danger.ui.DangerInfoActivity;
import tk.fjnugis.danger.ui.DangerListActivity;
import tk.fjnugis.danger.ui.KeywordsFlow;
import tk.fjnugis.danger.ui.MenuActivity;
import tk.fjnugis.danger.util.CacheHelper;
import tk.fjnugis.danger.util.HttpHelper;
import tk.fjnugis.danger.util.PostRequest;

import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Xiangyang on 2015/3/20.
 */
public class TagsCloudFragment extends Fragment implements View.OnClickListener {
    public String[] tags;
    @InjectView(R.id.keywordsflow)
    KeywordsFlow keywordsFlow;
    @InjectView(R.id.in)
    Button btnIn;
    @InjectView(R.id.out)
    Button btnOut;
    DangerApplication application;
    MenuActivity menuActivity;
    private String url=HttpHelper.ROOT + "/server/Tag-getList";
    public static final String KEY_OF_TAG="tagscloudfragment.tag";//保存标签的key

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView= inflater.inflate(R.layout.fragment_tagscloud, container, false);
        ButterKnife.inject(this, parentView);
        menuActivity= (MenuActivity) getActivity();
        application= (DangerApplication) getActivity().getApplication();
        btnIn.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        keywordsFlow.setDuration(800l);
        keywordsFlow.setOnItemClickListener(this);
        //从缓存中读取数据
        tags= (String[]) CacheHelper.getDataFromCache(application,url);
        if(tags!=null){
            feedKeywordsFlow(keywordsFlow, tags);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        }
        //从服务端获取数据
        getTagsFromServer();
        return parentView;
    }

    //从服务端获取数据
    private void getTagsFromServer(){
        Log.d("标签云","url:"+url);
        RequestQueue mQueue = Volley.newRequestQueue(menuActivity.getApplicationContext());
        PostRequest request=new PostRequest(url,null, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("标签云", "获取数据：" + s);
                try {
                    List<Tag> list = HttpHelper.GSON.fromJson(s, new TypeToken<List<Tag>>() {}.getType());
                    tags=new String[list.size()];
                    for(int i=0;i<tags.length;i++){
                        tags[i]=list.get(i).getName();
                    }
                    feedKeywordsFlow(keywordsFlow, tags);
                    keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
                    CacheHelper.saveDataToCache(application, url, tags);
                    Toast.makeText(menuActivity, "数据已更新", Toast.LENGTH_SHORT).show();
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(menuActivity, "无法解析数据", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
                Toast.makeText(menuActivity, "无法连接服务器", Toast.LENGTH_LONG).show();
            }
        });
        mQueue.add(request);
        mQueue.start();
    }

    private static void feedKeywordsFlow(KeywordsFlow keywordsFlow, String[] arr) {
        Random random = new Random();
        for (int i = 0; i < KeywordsFlow.MAX; i++) {
            int ran = random.nextInt(arr.length);
            String tmp = arr[ran];
            keywordsFlow.feedKeyword(tmp);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == btnIn) {//点击前进
            keywordsFlow.rubKeywords();
            // keywordsFlow.rubAllViews();
            feedKeywordsFlow(keywordsFlow, tags);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_IN);
        } else if (v == btnOut) {//点击后退
            keywordsFlow.rubKeywords();
            // keywordsFlow.rubAllViews();
            feedKeywordsFlow(keywordsFlow, tags);
            keywordsFlow.go2Show(KeywordsFlow.ANIMATION_OUT);
        } else if (v instanceof TextView) {
            String tag = ((TextView) v).getText().toString();
            Intent intent=new Intent(menuActivity, DangerListActivity.class);
            intent.putExtra(TagsCloudFragment.KEY_OF_TAG, tag);
            startActivity(intent);
        }
    }
}