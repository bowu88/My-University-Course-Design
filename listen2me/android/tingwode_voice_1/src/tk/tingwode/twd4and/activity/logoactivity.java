package tk.tingwode.twd4and.activity;
import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.R.id;
import tk.tingwode.twd4and.R.layout;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LogoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//隐藏标题
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		this.setContentView(R.layout.logo);
		//取消状态栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		ImageView iv = (ImageView)findViewById(R.id.logo);
		
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
				Intent it = new Intent(LogoActivity.this,LoginActivity.class);
				startActivity(it);
				finish();
				
			}
		});
		
		
		
		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("-------onDestroy-------");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("-------onResume-------");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("-------onStart-------");
	}
	
	

}
