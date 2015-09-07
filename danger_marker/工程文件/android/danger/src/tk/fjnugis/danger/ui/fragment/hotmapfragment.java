package tk.fjnugis.danger.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.HeatMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import org.json.JSONArray;
import org.json.JSONObject;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.ui.DangerInfoActivity;
import tk.fjnugis.danger.ui.HeatMapActivity;
import tk.fjnugis.danger.util.Category;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Created by Xiangyang on 2015/3/19.
 */
public class HotMapFragment extends Fragment {
    @InjectView(R.id.heatmap_button)
    Button button;
    @InjectView(R.id.category)
    Spinner categorySpinner;
    @InjectView(R.id.subcategory)
    Spinner subCategorySpinner;
    @InjectView(R.id.tags_input_editText)
    EditText tagsET;
    public static final  String KEY_OF_BUNDLE="hotmapfragment.bundle";
    public static final String KEY_OF_CATEGORY="hotmapfragment.category";
    public static final String KEY_OF_SUB_CATEGORY="hotmapfragment.subcategory";
    public static final String KEY_OF_TAG="hotmapfragment.tag";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView= inflater.inflate(R.layout.fragment_hotmap, container, false);
        ButterKnife.inject(this,parentView);
        //初始化类别
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        Set<String> set= Category.getInstance().getCategorys();
        adapter.add("不限");
        for(String str:set){
            adapter.add(str);
        }
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);
        categorySpinner.setPrompt("请选择一级类别");//设置提示信息
        subCategorySpinner.setAdapter(adapter2);
        subCategorySpinner.setPrompt("请选择二级类别");
        subCategorySpinner.setEnabled(false);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter2.clear();
                adapter2.add("不限");
                String category = categorySpinner.getSelectedItem().toString();
                if(category!="不限") {
                    List<String> subCategorys = Category.getInstance().getSubCategory(category);
                    for (String subCategory : subCategorys) {
                        adapter2.add(subCategory);
                    }
                }
                subCategorySpinner.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //点击查询按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity parentActivity=HotMapFragment.this.getActivity();
                Intent intent=new Intent(parentActivity, HeatMapActivity.class);
                String category=categorySpinner.getSelectedItem().toString();
                String subCategory=subCategorySpinner.getSelectedItem().toString();
                String tag=tagsET.getText().toString();
                Bundle bundle=new Bundle();
                if(category!=null&&!category.trim().equals(""))
                    bundle.putSerializable(HotMapFragment.KEY_OF_CATEGORY,category);
                if(subCategory!=null&&!category.trim().equals(""))
                    bundle.putSerializable(HotMapFragment.KEY_OF_SUB_CATEGORY,subCategory);
                if(tag!=null&&!category.trim().equals(""))
                    bundle.putSerializable(HotMapFragment.KEY_OF_TAG,tag);
                intent.putExtra(HotMapFragment.KEY_OF_BUNDLE, bundle);
                startActivity(intent);
            }
        });
        return parentView;
    }
}