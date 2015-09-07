package tk.tingwode.twd4and.activity;

import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.R.dimen;
import tk.tingwode.twd4and.R.drawable;
import tk.tingwode.twd4and.R.id;
import tk.tingwode.twd4and.R.layout;
import tk.tingwode.twd4and.R.menu;
import tk.tingwode.twd4and.fragment.MenuFragment;
import tk.tingwode.twd4and.fragment.UserCenterFragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.view.Window;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class BaseActivity extends SlidingFragmentActivity {

	private int mTitleRes;
	protected ListFragment mFrag;

	public BaseActivity(int titleRes) {
		mTitleRes = titleRes;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setTitle(mTitleRes);

		//设置滑动菜单属性
		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		
		//设置左边菜单
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new MenuFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}
		
		//设置右侧个人中心
		getSlidingMenu().setSecondaryMenu(R.layout.menu_frame_two);
		getSlidingMenu().setSecondaryShadowDrawable(R.drawable.shadowright);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.menu_frame_two, new UserCenterFragment())
		.commit();

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			toggle();
			return true;
		case R.id.action_user:
			showSecondaryMenu();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
