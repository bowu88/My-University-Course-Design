package tk.tingwode.twd4and.activity;

import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.fragment.NearbyMapFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;

public class MainActivity extends BaseActivity {
	
	private Fragment mContent;

	public MainActivity() {
		super(R.string.app_name);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(savedInstanceState, "mContent");
		if (mContent == null)
			mContent = new NearbyMapFragment();
		
		setContentView(R.layout.content_frame);
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, mContent)
		.commit();
		
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSupportFragmentManager().putFragment(outState, "mContent", mContent);
	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager()
		.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.addToBackStack(null)
		.commit();
		getSlidingMenu().showContent();
	}

}
