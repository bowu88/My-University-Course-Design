package tk.tingwode.twd4and.activity;

import java.io.File;

import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.domain.UserJO;
import tk.tingwode.twd4and.service.BusinessService;
import tk.tingwode.twd4and.utils.Tools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class RegistActivity extends Activity {
	//-------------设置头像相关 start------------
	/* 组件 */
	private RelativeLayout switchAvatar;
	private ImageView faceImage;
	private Bitmap photo;

	private String[] items = new String[] { "选择本地图片", "拍照" };
	/* 头像名称 */
	private static final String IMAGE_FILE_NAME = "faceImage.jpg";

	/* 请求码 */
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	//-------------设置头像相关 end--------------
	
	EditText txtEmail;
	EditText txtUsername;
	EditText txtPwd;
	EditText txtPwd2;
	RadioGroup rbtnGender;
	Button btnRegist;
	UserJO registUser;
	
	public ProgressDialog pd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE); // 去掉标题
		setContentView(R.layout.regist);
		//取消状态栏
//		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		//-------------设置头像相关 start------------
		switchAvatar = (RelativeLayout) findViewById(R.id.switch_face_rl);
		faceImage = (ImageView) findViewById(R.id.face);
		switchAvatar.setOnClickListener(listener);// 设置事件监听
		//-------------设置头像相关 end------------
		
		txtEmail=(EditText) findViewById(R.id.txtEmail);
		txtUsername=(EditText) findViewById(R.id.txtUsername);
		txtPwd=(EditText) findViewById(R.id.txtPwd);
		txtPwd2=(EditText) findViewById(R.id.txtPwd2);
		rbtnGender=(RadioGroup) findViewById(R.id.rbtnGender);
		btnRegist=(Button) findViewById(R.id.registButton);
		//登录按钮监听器
		btnRegist.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				UserJO user=RegistActivity.this.getRegistUser();
				if(user==null){
					return;
				}
				//开启注册线程
				new RegistTask().execute(RegistActivity.this.photo,user);
			}
		});
		
	}
	
	private UserJO getRegistUser(){
		registUser=new UserJO();
		registUser.setEmail(txtEmail.getText().toString());
		registUser.setPassword(txtPwd.getText().toString());
		RadioButton checkedRbtn=(RadioButton)findViewById(rbtnGender.getCheckedRadioButtonId());
		if(checkedRbtn!=null){
			registUser.setGender(checkedRbtn.getText().toString());
		}
		registUser.setUsername(txtUsername.getText().toString());
		//校验表单
		boolean flag=true;
		if(isEmpty(registUser.email)){
			Toast.makeText(this, "邮箱不能为空", Toast.LENGTH_LONG).show();
			flag=false;
		}
		if(isEmpty(registUser.username)){
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
			flag=false;
		}
		if(isEmpty(registUser.password)){
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			flag=false;
		}else if(!registUser.password.equals(txtPwd2.getText().toString())){
			Toast.makeText(this, "两次密码输入不一致", Toast.LENGTH_LONG).show();
			flag=false;
		}
		if(isEmpty(registUser.gender)){
			Toast.makeText(this, "请选择您的性别", Toast.LENGTH_LONG).show();
			flag=false;
		}
		return flag?this.registUser:null;
	}
	
	//判断值是否为空
	private boolean isEmpty(String value){
		return value==null||value.trim().equals("");
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	//-------------设置头像相关 start------------
	private View.OnClickListener listener = new View.OnClickListener() {

		@Override
		public void onClick(View v) {
			showDialog();
		}
	};

	/**显示选择对话框 */
	private void showDialog() {

		new AlertDialog.Builder(this)
				.setTitle("设置头像")
				.setItems(items, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						switch (which) {
						case 0:
							Intent intentFromGallery = new Intent();
							intentFromGallery.setType("image/*"); // 设置文件类型
							intentFromGallery
									.setAction(Intent.ACTION_GET_CONTENT);
							startActivityForResult(intentFromGallery,
									IMAGE_REQUEST_CODE);
							break;
						case 1:

							Intent intentFromCapture = new Intent(
									MediaStore.ACTION_IMAGE_CAPTURE);
							// 判断存储卡是否可以用，可用进行存储
							if (Tools.hasSdcard()) {

								intentFromCapture.putExtra(
										MediaStore.EXTRA_OUTPUT,
										Uri.fromFile(new File(Environment
												.getExternalStorageDirectory(),
												IMAGE_FILE_NAME)));
							}

							startActivityForResult(intentFromCapture,
									CAMERA_REQUEST_CODE);
							break;
						}
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//结果码不等于取消时候
		if (resultCode != RESULT_CANCELED) {

			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				startPhotoZoom(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (Tools.hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory()
									+ IMAGE_FILE_NAME);
					startPhotoZoom(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(RegistActivity.this, "未找到存储卡，无法存储照片！",
							Toast.LENGTH_LONG).show();
				}

				break;
			case RESULT_REQUEST_CODE:
				if (data != null) {
					getImageToView(data);
				}
				break;
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**裁剪图片方法实现*/
	public void startPhotoZoom(Uri uri) {

		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置裁剪
		intent.putExtra("crop", "true");
		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX outputY 是裁剪图片宽高
		intent.putExtra("outputX", 320);
		intent.putExtra("outputY", 320);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, 2);
	}

	/** 保存裁剪之后的图片数据*/
	private void getImageToView(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			photo = extras.getParcelable("data");
			Drawable drawable = new BitmapDrawable(photo);
			faceImage.setImageDrawable(drawable);
		}
	}
	//-------------设置头像相关 end------------

	private class RegistTask extends AsyncTask<Object, Void, String>{

		@Override
		protected void onPreExecute() {
			//显示“正在登录”
			pd = new ProgressDialog(RegistActivity.this);
			pd.setMessage("正在注册...");
			pd.show();
		}
		
		@Override
		protected String doInBackground(Object... params) {
			BusinessService service=new BusinessService();
			String result=null;
			try {
				Bitmap photo=(Bitmap) params[0];
				UserJO user=(UserJO) params[1];
				service.regist(photo,user);
				result="注册成功";
			} catch (Exception e) {
				e.printStackTrace();
				result="注册失败";
			}
			return result;
		}
		
		@Override
		protected void onPostExecute(String result) {
			//获取到用户数据后跳转
			pd.dismiss();
			if(result.equals("注册失败")){
				Toast.makeText(RegistActivity.this, "注册失败", Toast.LENGTH_LONG).show();
				return;
			}else if (result.equals("注册成功"))
			Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
			Intent it=new Intent(RegistActivity.this,LoginActivity.class);
			RegistActivity.this.startActivity(it);
		}
		
	}
}
