package tk.fjnugis.danger.ui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import tk.fjnugis.danger.DangerApplication;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.ui.MenuActivity;
import tk.fjnugis.danger.util.HttpHelper;

/**
 * Created by Xiangyang on 2015/4/16.
 */
public class SettingFragment extends Fragment{
    MenuActivity menuActivity;
    @InjectView(R.id.root)
    EditText root;
    @InjectView(R.id.saveButton)
    Button saveButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuActivity = (MenuActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView= inflater.inflate(R.layout.fragment_setting, container, false);
        ButterKnife.inject(this,parentView);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveSetting();
            }
        });
        //读取配置
        SharedPreferences settings=menuActivity.getSharedPreferences(DangerApplication.SETTING_KEY,0);
        String rootUrl=settings.getString(HttpHelper.ROOT_PREFERENCE_KEY,HttpHelper.ROOT);
        root.setText(rootUrl);
        return parentView;
    }

    /**保存设置*/
    public void saveSetting(){
        if(root.getText()==null||HttpHelper.ROOT.equals(root.getText().toString())) {
            return;
        }
        String rootUrl=root.getText().toString();
        HttpHelper.ROOT=rootUrl;
        SharedPreferences settings=menuActivity.getSharedPreferences(DangerApplication.SETTING_KEY,0);
        SharedPreferences.Editor editor=settings.edit();
        editor.putString(HttpHelper.ROOT_PREFERENCE_KEY,rootUrl);
        editor.commit();
        Toast.makeText(menuActivity,"设置保存成功",Toast.LENGTH_SHORT).show();
    }

}
