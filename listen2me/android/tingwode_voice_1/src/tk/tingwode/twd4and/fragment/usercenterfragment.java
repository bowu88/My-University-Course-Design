package tk.tingwode.twd4and.fragment;

import tk.tingwode.twd4and.R;
import tk.tingwode.twd4and.domain.UserJO;
import tk.tingwode.twd4and.utils.Global;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class UserCenterFragment extends Fragment{
	
	TextView user_username;
	TextView user_voice_count;
	TextView user_following_count;
	TextView user_followers_count;
	TextView user_album_count;
	Button user_voice_favorites;
	Button user_album_favorites;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.user_center, null);
		LinearLayout voice_layout=(LinearLayout) view.findViewById(R.id.user_voice_layout);
		voice_layout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(UserCenterFragment.this.getActivity(), "跳转到我的声音", 3000).show();
			}
		});
		UserJO user=Global.userjo;
		user_username=(TextView) view.findViewById(R.id.user_username);
		user_voice_count=(TextView) view.findViewById(R.id.user_voice_count);
		user_following_count=(TextView) view.findViewById(R.id.user_following_count);
		user_followers_count=(TextView) view.findViewById(R.id.user_followers_count);
		user_album_count=(TextView) view.findViewById(R.id.user_album_count);
		user_voice_favorites=(Button) view.findViewById(R.id.user_voice_favorites);
		user_album_favorites=(Button) view.findViewById(R.id.user_album_favorites);
		
		user_username.setText(user.username);
		user_voice_count.setText(user.voices_count+"");
		user_album_count.setText(user.albums_count+"");
		user_following_count.setText(user.following_count+"");
		user_followers_count.setText(user.followers_count+"");
		user_voice_favorites.setText(user_voice_favorites.getText().toString()+"("+user.voices_favorite_count+")");
		user_album_favorites.setText(user_album_favorites.getText().toString()+"("+user.albums_favorite_count+")");
		return view;
	}

	
}
