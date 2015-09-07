package tk.fjnugis.danger.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.util.DangerInfoHelper;

import java.util.List;

/**
 * Created by Xiangyang on 2015/1/19.
 */
public class DangerListAdapter extends BaseAdapter {

    private LayoutInflater mInflater;

    LatLng location;
    List<Danger> dangerList;

    public DangerListAdapter(Context context,List<Danger> dangerList) {
        super();
        this.dangerList = dangerList;
        this.mInflater=LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d("List适配器", "getView");
        ViewHolder holder=null;
        final Danger danger = dangerList.get(position);
        if(convertView==null){
            convertView=mInflater.inflate(R.layout.item_listview,null);
            holder=new ViewHolder();
            ButterKnife.inject(holder,convertView);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        holder.title.setText(DangerInfoHelper.getTitle(danger, 8));
        Float rankFloat=DangerInfoHelper.getRank(danger);
        holder.rank.setText(rankFloat.toString());
        holder.ratingBar.setRating(rankFloat);
        //category.setText(DangerInfoHelper.getCategory(danger));
        //计算距离
        if(location!=null){
            double lat=danger.getLatitude();
            double lng=danger.getLongitude();
            double distance = DistanceUtil.getDistance(location,new LatLng(lat,lng));
            Log.d("DangerListAdapter","当前位置:"+location.latitude+","+location.longitude+"---目标位置："+lat+","+lng+" ---计算出的距离:"+danger.getName()+danger.getId()+"---"+distance);
            String distanceStr=DangerInfoHelper.getDistance(distance);
            holder.distanceTextView.setText(distanceStr);
        }

        //图标
        //todo 根据危险等级设置不同图标
        //icon.setImageResource();

        return convertView;
    }

    @Override
    public int getCount() {
//        Log.d("List适配器|生命周期", "getCount");
        return dangerList.size();
    }

    @Override
    public Object getItem(int position) {
        return dangerList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }
    public void setDangerList(List<Danger>  dangerList){
        this.dangerList=dangerList;
    }

    class ViewHolder{
        @InjectView(R.id.item_icon)
        ImageView icon;
        @InjectView(R.id.item_title)
        TextView title;
        @InjectView(R.id.item_rank)
        TextView rank;
        @InjectView(R.id.item_distance)
        TextView distanceTextView;
        @InjectView(R.id.item_ratingbar)
        RatingBar ratingBar;
    }
}
