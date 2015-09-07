package tk.fjnugis.danger.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.ui.fragment.MapFragment;
import tk.fjnugis.danger.util.DangerInfoHelper;

import java.text.DecimalFormat;

/**
 * Created by Xiangyang on 2014/11/25.
 */
public class DangerInfoActivity extends Activity {
    Danger danger;
    @InjectView(R.id.danger_info_window_back)
    Button backButton;
    @InjectView(R.id.danger_info_activity_title)
    TextView titileTextView;/**标题*/
    @InjectView(R.id.danger_info_activity_tagsll)
    LinearLayout tagsLinearLayout;
//    @InjectView(R.id.tagsLayout)
//    FixGridLayout tagsLayout;
    @InjectView(R.id.danger_info_activity_rank)
    RatingBar rankRatingBar;//等级
    @InjectView(R.id.danger_info_activity_rank_text)
    TextView rankText;//等级数字
    @InjectView(R.id.danger_info_activity_category)
    TextView categoryTextView;//类别
    @InjectView(R.id.danger_info_activity_descritpion)
    TextView descriptionTextView;//描述
    @InjectView(R.id.danger_info_activity_location_latlng)
    TextView locationLatlngTextView;//坐标
    @InjectView(R.id.danger_info_activity_location_address)
    TextView loactionAddressTextView;//地址
    @InjectView(R.id.danger_info_activity_location_addressDesc)
    TextView locationAddressDescTextView;//详细地址
    @InjectView(R.id.danger_info_activity_lable_pictures)
    TextView picturesTextView;  //图片label
    @InjectView(R.id.gridView)
    NoScrollGridView gradView;  //图片

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danger_info);
        ButterKnife.inject(this);
        //点击返回按钮
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                NavUtils.navigateUpFromSameTask(DangerInfoActivity.this);
                DangerInfoActivity.this.finish();
            }
        });
        //获取传过来的实体类
        Intent intent=getIntent();
        if(intent!=null){
            danger= (Danger) intent.getBundleExtra(MapFragment.KEY_OF_DANGER_INFO).get(MapFragment.KEY_OF_DANGER_INFO);
            Log.d("intent中提取的安全隐患信息",danger.toString());
            initView(danger);
        }
    }

    /**
     * 初始化界面
     * @param danger 封装安全隐患信息的实体类
     */
    private void initView(Danger danger) {
        //标题
        String title=danger.getName();
        if(title==null||title.equals(""))
            title="无标题";
        if(title!=null&&title.length()>12)
            title=title.substring(0,12)+"…";
        this.titileTextView.setText(title);
        //标签
        String[] tagArr=DangerInfoHelper.getTags(danger);
        boolean flag=true;
        for(int i=0;tagArr!=null&&i<tagArr.length;i++){
            Button tagView = new Button(getApplicationContext());
            tagView.setBackgroundResource(R.drawable.tag);
            tagView.setText(tagArr[i]);
            tagView.setTextColor(getResources().getColor(R.color.white));
            //测量标签的宽高
//            int tagWidth=View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//            int tagheight=View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//            tagView.measure(tagWidth,tagheight);
//            tagheight=tagView.getMeasuredHeight();
//            tagWidth=tagView.getMeasuredWidth();
//            tagsLayout.setmCellWidth(tagWidth);
//            tagsLayout.setmCellHeight(tagheight);
//            Log.d("tagsLayout子元素的宽高：",tagheight+"  "+tagWidth);
//            tagsLayout.addView(tagView);
            tagsLinearLayout.addView(tagView);
        }

        //等级
        String rank=new DecimalFormat("#.#").format(danger.rank);
        rankRatingBar.setRating(Float.parseFloat(rank));
        rankText.setText(rank);
        //类别
        String category=danger.getCategory()+">"+danger.getSubCategory();
        categoryTextView.setText(category);
        //描述
        String description = danger.description;
        descriptionTextView.setText(description);
        //坐标
        StringBuilder sb = new StringBuilder();
        double lon=danger.getLongitude();
        double lat=danger.getLatitude();
        if (lon > 0) {
            sb.append("东经：").append(lon).append("度\n");
        } else {
            sb.append("西经：").append(-lon).append("度\n");
        }
        if (lat > 0) {
            sb.append("北纬：").append(lat).append("度\n");
        } else {
            sb.append("南纬：").append(-lat).append("度\n");
        }
        if(danger.getAccuracy()!=null)
            sb.append("误差范围：").append(danger.getAccuracy()).append("米");
        locationLatlngTextView.setText(sb.toString());
        //地址
        loactionAddressTextView.setText(DangerInfoHelper.getAddress(danger));
        locationAddressDescTextView.setText(danger.getAddressDescritpion());
        //图片
        if(danger.getPictures()!=null&&danger.getPictures().length()>0){
            picturesTextView.setVisibility(View.VISIBLE);
            gradView.setVisibility(View.VISIBLE);
            final String[] files=DangerInfoHelper.getPictures(danger);
            gradView.setAdapter(new MyGridAdapter(files,getApplicationContext()));
            gradView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    imageBrower(position,files);
                }
            });
        }
    }

    //跳转到图片浏览窗口
    private void imageBrower(int position, String[] urls) {
        Intent intent = new Intent(this, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
        startActivity(intent);
    }
}