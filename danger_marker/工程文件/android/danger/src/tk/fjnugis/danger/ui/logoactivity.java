package tk.fjnugis.danger.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;
import tk.fjnugis.danger.R;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//隐藏标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_logo);
		//取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		ImageView iv = (ImageView) findViewById(R.id.logo);

		AlphaAnimation aa = new AlphaAnimation(0.1f, 1.0f);
		aa.setDuration(3000);
		iv.startAnimation(aa);

		aa.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				 Intent it = new Intent(LogoActivity.this,MenuActivity.class);
				 startActivity(it);
				 finish();
			}
		});
	}
	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * 
	 * // Inflate the menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.logo, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

}
