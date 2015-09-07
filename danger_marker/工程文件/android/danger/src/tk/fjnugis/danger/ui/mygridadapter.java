package tk.fjnugis.danger.ui;

import android.content.Context;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.util.HttpHelper;

/**
 * Created by Xiangyang on 2015/1/17.
 */
public class MyGridAdapter extends BaseAdapter {
    private String[] files;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private ImageLoader mImageLoader;

    public MyGridAdapter(String[] files, Context context) {
        this.files = files;
        mLayoutInflater = LayoutInflater.from(context);
        mContext=context;
        RequestQueue mQueue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(mQueue, HttpHelper.imageCache);
    }

    @Override
    public int getCount() {
        return files == null ? 0 : files.length;
    }

    @Override
    public String getItem(int position) {
        return files[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyGridViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new MyGridViewHolder();
            convertView = mLayoutInflater.inflate(R.layout.item_gridview,
                    parent, false);
            viewHolder.imageView = (ImageView) convertView
                    .findViewById(R.id.album_image);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (MyGridViewHolder) convertView.getTag();
        }
        String url = getItem(position);
        ImageLoader.ImageListener listener = ImageLoader.getImageListener(viewHolder.imageView, R.drawable.empty_photo, android.R.drawable.ic_delete);
        mImageLoader.get(url, listener);
        return convertView;
    }

    private static class MyGridViewHolder {
        ImageView imageView;
    }
}
