package tk.fjnugis.danger.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import tk.fjnugis.danger.R;

import java.util.*;

/**
 * ListView 适配器 用于显示带小标题党多组信息
 * Created by Xiangyang on 2015/4/14.
 */
public class SectionListAdapter<T> extends BaseAdapter {
    Map<String,List<T>> map;
    public LayoutInflater mInflater;

    public SectionListAdapter(Context context, Map<String, List<T>> map) {
        this.map=map;
        this.mInflater= LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count=map.size();
        for(Iterator<List<T>> it=map.values().iterator();it.hasNext();){
            List<T> list=it.next();
            count+=list.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        for(String title:map.keySet()){
            List<T> list=map.get(title);
            int count=1+list.size();
            if(position==0){
                return title;
            }
            if(position<count){
                return list.get(position-1);
            }else{
                position-=count;
            }
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("CityList适配器", "getView");
        Object item=getItem(position);
        if(item instanceof String){
            convertView=mInflater.inflate(R.layout.item_citylist_tag,null);
            TextView title= (TextView) convertView.findViewById(R.id.title);
            title.setText((String)item);
            return convertView;
        }
        convertView=mInflater.inflate(R.layout.item_citylist,null);
        return setItemView(convertView,(T)item);
    }
    
    public View setItemView(View convertView,T item){
        return null;
    }

    public int getItemLayout(){
        return 0;
    }

}