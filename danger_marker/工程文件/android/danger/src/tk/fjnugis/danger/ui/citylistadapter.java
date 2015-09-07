package tk.fjnugis.danger.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.baidu.mapapi.map.offline.MKOLSearchRecord;
import tk.fjnugis.danger.R;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiangyang on 2015/4/15.
 */
public class CityListAdapter extends BaseAdapter{
    Map<String, List<MKOLSearchRecord>> map;
    public LayoutInflater mInflater;
    CityClickListener cityClickListener;

    public CityListAdapter(Context context, Map<String, List<MKOLSearchRecord>> map) {
        this.map = map;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        int count = map.size();
        for (Iterator<List<MKOLSearchRecord>> it = map.values().iterator(); it.hasNext(); ) {
            List<MKOLSearchRecord> list = it.next();
            count += list.size();
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        for (String title : map.keySet()) {
            List<MKOLSearchRecord> list = map.get(title);
            int count = 1 + list.size();
            if (position == 0) {
                return title;
            }
            if (position < count) {
                return list.get(position - 1);
            } else {
                position -= count;
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
        Object item = getItem(position);
        if (item instanceof String) {
            convertView = mInflater.inflate(R.layout.item_citylist_tag, null);
            TextView title = (TextView) convertView.findViewById(R.id.title);
            title.setText((String) item);
            return convertView;
        }
        ViewHolder holder=new ViewHolder();
        final MKOLSearchRecord city = (MKOLSearchRecord) item;
        convertView = mInflater.inflate(R.layout.item_citylist, null);
        ButterKnife.inject(holder, convertView);
        holder.cityName.setText(city.cityName);
        holder.size.setText(CityHelper.formatDataSize(city.size));
        holder.downloadIV.setTag(city);
        holder.downloadIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cityClickListener.onDownloadButtonClick(v);
            }
        });
        return convertView;
    }

    class ViewHolder{
        @InjectView(R.id.cityName)
        TextView cityName;
        @InjectView(R.id.size)
        TextView size;
        @InjectView(R.id.downloadIV)
        ImageView downloadIV;

    }

    public interface CityClickListener{
        void onDownloadButtonClick(View view);
    }

    public void setCityClickListener(CityClickListener cityClickListener) {
        this.cityClickListener = cityClickListener;
    }
}
