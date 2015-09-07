package tk.tingwode.twd4and.activity;

import java.util.HashMap;
import java.util.Map;

import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.domain.UserJO;
import tk.tingwode.twd4and.service.BusinessService;
import tk.tingwode.twd4and.utils.Global;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	EditText txtUser;
	EditText txtPwd;
	Button btnLogin;
	TextView registNow;
	
	public ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		//取消状态栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		txtUser = (EditText)findViewById(R.id.txtUser);
		txtPwd = (EditText)findViewById(R.id.txtPwd);
//		txtUser.setText("test@test.com");
//		txtPwd.setText("123");
		
		btnLogin = (Button)findViewById(R.id.loginButton);
		
		//登录按钮监听器
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String name = txtUser.getText().toString();
				String pass = txtPwd.getText().toString();
				//判断登录邮箱和密码是否为空
				if(name==null||pass==null||name.trim().equals("")||pass.trim().equals("")){
					Toast.makeText(LoginActivity.this, "登录邮箱和密码不能为空!", 3000).show();
					return;
				}
				new LoginTask().execute(name,pass);
			}
		});
		registNow=(TextView) findViewById(R.id.regist_now);
		registNow.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it=new Intent(LoginActivity.this,RegistActivity.class);
				startActivity(it);
			}
		});
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	private class LoginTask extends AsyncTask<String, Void, UserJO>{

		@Override
		protected void onPreExecute() {
			//显示“正在登录”
			pd = new ProgressDialog(LoginActivity.this);
			pd.setMessage("正在登录...");
			pd.show();
		}
		
		@Override
		protected UserJO doInBackground(String... params) {
			BusinessService service=new BusinessService();
			UserJO user=null;
			try {
				user=service.login(params[0], params[1]);
			} catch (Exception e) {
				e.printStackTrace();
				//TODO 提示登录失败
			}
			return user;
		}
		
		@Override
		protected void onPostExecute(UserJO result) {
			//获取到用户数据后跳转
			pd.dismiss();
			if(result==null){
				Toast.makeText(LoginActivity.this, "登录失败", 3000).show();
				return;
			}
			Global.userjo=result;
			Toast.makeText(LoginActivity.this, "登录成功", 3000).show();
			Intent it=new Intent(LoginActivity.this,MainActivity.class);
			LoginActivity.this.startActivity(it);
		}
		
	}

}
