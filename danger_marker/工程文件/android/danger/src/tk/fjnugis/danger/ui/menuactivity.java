package tk.fjnugis.danger.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.special.ResideMenu.ResideMenu;
import com.special.ResideMenu.ResideMenuItem;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.ui.fragment.*;

import java.util.HashMap;
import java.util.Map;

public class MenuActivity extends FragmentActivity implements OnClickListener {

    private ResideMenu resideMenu;
    private MenuActivity mContext;
    //导航菜单
    private ResideMenuItem itemMap;
    private ResideMenuItem itemList;
    private ResideMenuItem itemUpload;
    private ResideMenuItem itemHotPoint;
    private ResideMenuItem itemTagCloud;
    private ResideMenuItem itemOfflineMap;
//    private ResideMenuItem itemUserCenter;
    private ResideMenuItem itemSetting;
    public View currentItem;
    //进度窗口
    public ProgressDialog progressDialog;

    private Map<String, Fragment> fragments = new HashMap();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MenuActivity","onCreate();");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        mContext = this;
        setUpMenu();
        if (savedInstanceState == null) {
            Log.d("MenuActivity","切换到地图");
            changeFragment(new MapFragment());
            this.currentItem = itemMap;
        }else {
            Log.d("MenuActivity","已有Fragment");
        }

    }

    private void setUpMenu() {
        resideMenu = new ResideMenu(this);
        resideMenu.setBackground(R.drawable.menu_background_1);
        resideMenu.attachToActivity(this);
        resideMenu.setScaleValue(0.6f);

        itemMap = new ResideMenuItem(this, R.drawable.icon_home, "地图");
        itemList=new ResideMenuItem(this, R.drawable.ic_action_storage, "列表");
        itemUpload = new ResideMenuItem(this, R.drawable.ic_action_place, "上传");
        itemHotPoint = new ResideMenuItem(this, R.drawable.ic_action_map, "热力图");
        itemTagCloud = new ResideMenuItem(this, R.drawable.ic_action_cloud, "标签云");
        itemOfflineMap = new ResideMenuItem(this, R.drawable.ic_item_download, "离线地图");
//        itemUserCenter = new ResideMenuItem(this, R.drawable.ic_action_person, "个人中心");
        itemSetting = new ResideMenuItem(this, R.drawable.ic_action_settings, "设置");

        itemMap.setOnClickListener(this);
        itemList.setOnClickListener(this);
        itemUpload.setOnClickListener(this);
        itemHotPoint.setOnClickListener(this);
        itemTagCloud.setOnClickListener(this);
        itemOfflineMap.setOnClickListener(this);
//        itemUserCenter.setOnClickListener(this);
        itemSetting.setOnClickListener(this);

        resideMenu.addMenuItem(itemMap, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemList, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemUpload, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemHotPoint, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemTagCloud, ResideMenu.DIRECTION_LEFT);

        resideMenu.addMenuItem(itemOfflineMap,ResideMenu.DIRECTION_RIGHT);
//        resideMenu.addMenuItem(itemUserCenter, ResideMenu.DIRECTION_RIGHT);
        resideMenu.addMenuItem(itemSetting, ResideMenu.DIRECTION_RIGHT);

        findViewById(R.id.title_bar_left_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
            }
        });
        findViewById(R.id.title_bar_right_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == currentItem)
            return;
        if (view == itemMap) {
            changeFragment(MapFragment.class);
            setTitle("危险地图");
        } else if(view==itemList){
            changeFragment(ListFragment.class);
            setTitle("列表");
        }else if (view == itemUpload) {
            changeFragment(UploadFragment.class);
            setTitle("上传");
        } else if (view == itemHotPoint) {
            changeFragment(HotMapFragment.class);
            setTitle("热力图");
        } else if (view == itemTagCloud) {
            changeFragment(TagsCloudFragment.class);
            setTitle("标签云");
        } else if(view==itemOfflineMap){
            changeFragment(OfflineMapFragment.class);
            setTitle("离线地图");
//        } else if (view == itemUserCenter) {

        } else if (view == itemSetting) {
            changeFragment(SettingFragment.class);
            setTitle("设置");
        }
        this.currentItem = view;
        resideMenu.closeMenu();
    }

    private void changeFragment(Fragment targetFragment) {
        resideMenu.clearIgnoredViewList();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public void changeFragment(Class clazz) {
        String key=clazz.getName();
        Fragment fragment = this.fragments.get(key);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (fragment == null) {
            try {
                fragment = (Fragment)clazz.newInstance();
                fragments.put(key, fragment);
                transaction.add(R.id.main_fragment,fragment,fragment+"");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        for(Fragment f:fragments.values()){
            transaction.hide(f);
        }
        transaction.show(fragment);
        transaction.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    public Fragment getFragment(Class clazz){
        String key=clazz.getName();
        Fragment fragment = this.fragments.get(key);
        return fragment;
    }

    public void setTitle(String title) {
        TextView view_title = (TextView) findViewById(R.id.menu_title);
        view_title.setText(title);
    }

    public ResideMenu getResideMenu() {
        return this.resideMenu;
    }

}
