package tk.fjnugis.danger.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.util.HttpHelper;
import uk.co.senab.photoview.PhotoViewAttacher;
import uk.co.senab.photoview.PhotoViewAttacher.OnPhotoTapListener;

public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;

	private ImageLoader mImageLoader;

	public static ImageDetailFragment newInstance(String imageUrl) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;
		RequestQueue mQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
		mImageLoader = new ImageLoader(mQueue, HttpHelper.imageCache);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);
		
		mAttacher.setOnPhotoTapListener(new OnPhotoTapListener() {
			
			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});
		
		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		//1.根据mImageUrljiazai、mImageView图片
		//2.监听开始加载图片事件，调用progressBar.setVisibility(View.VISIBLE);
		//3.监听加载成功事件，调用progressBar.setVisibility(View.GONE);mAttacher.update();
		//4.监听加载失败事件，调用Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();progressBar.setVisibility(View.GONE);
		progressBar.setVisibility(View.VISIBLE);
		ImageLoader.ImageListener listener =new ImageLoader.ImageListener() {
			public void onErrorResponse(VolleyError error) {
				progressBar.setVisibility(View.GONE);
				Toast.makeText(getActivity(), "图片加载失败", Toast.LENGTH_SHORT).show();
				mImageView.setImageResource(android.R.drawable.ic_delete);
			}

			public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
				progressBar.setVisibility(View.GONE);
				if(response.getBitmap() != null) {
					mAttacher.update();
					mImageView.setImageBitmap(response.getBitmap());
				} else {
					mImageView.setImageResource( R.drawable.empty_photo);
				}

			}
		};
		mImageLoader.get(mImageUrl, listener);
	}

}
