package tk.fjnugis.danger.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.geocode.*;
import tk.fjnugis.danger.R;
import tk.fjnugis.danger.model.Danger;
import tk.fjnugis.danger.model.Tag;
import tk.fjnugis.danger.ui.MenuActivity;
import tk.fjnugis.danger.util.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * 上传安全隐患信息
 * Created by Xiangyang on 2014/11/17.
 */
public class UploadFragment extends Fragment implements View.OnClickListener, Response.ErrorListener, Response.Listener<String> {
    private Danger danger = new Danger();
    private MenuActivity menuActivity;
    private ProgressDialog progressDialog;
    private ViewHolder holder=new ViewHolder();
    private LatLng locationFromMap=null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuActivity = (MenuActivity) getActivity();
        progressDialog = menuActivity.progressDialog;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View parentView = inflater.inflate(R.layout.fragment_upload, container, false);
        ButterKnife.inject(holder, parentView);
        holder.uploadButton.setOnClickListener(this);
        //初始化类别
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Category.getInstance().getCategorys().toArray(new String[4]));
        final ArrayAdapter adapter2 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        holder.categorySpinner.setAdapter(adapter);
        holder.categorySpinner.setPrompt("请选择一级类别");//设置提示信息
        holder.subCategorySpinner.setAdapter(adapter2);
        holder.subCategorySpinner.setPrompt("请选择二级类别");
        holder.subCategorySpinner.setEnabled(false);
        holder.categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String category = holder.categorySpinner.getSelectedItem().toString();
                List<String> subCategorys = Category.getInstance().getSubCategory(category);
                adapter2.clear();
                for (String subCategory : subCategorys) {
                    adapter2.add(subCategory);
                }
                holder.subCategorySpinner.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //初始化定位模块
        initLocModule();
        //初始化标签
        initTags();
        //初始化拍照模块
        initTakePicture();
        return parentView;
    }

    //--------------------------------添加标签-----START----------------------

    private List<String> tagsList = new ArrayList<String>();

    /**
     * 初始化标签
     */
    private void initTags() {
        holder.tagInputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取标签字符串并分割
                String tagsString = holder.tagsInputEditText.getText().toString();
                if (tagsString == null || tagsString.trim().equals(""))
                    return;
                String[] tagsArr = tagsString.trim().split(" ");
                for (String tagName : tagsArr) {
                    if (!(tagName.equals("") || tagsList.contains(tagName))) {
                        addTag(tagName);
                        tagsList.add(tagName);
                        holder.tagsInputEditText.setText(null);
                    }
                }
            }
        });
    }

    /**
     * 添加标签方法
     */
    private void addTag(String tagName) {
        Button tagView = new Button(getActivity().getApplicationContext());
        tagView.setBackgroundResource(R.drawable.tag_with_close);
        tagView.setText(tagName);
        tagView.setTextColor(getResources().getColor(R.color.white));
        //删除标签功能
        tagView.setOnClickListener(clickToRemoveView);
        holder.tagsLinearLayout.addView(tagView);
    }

    private String getTagsString() {
        StringBuilder sb = new StringBuilder();
        for (String tag : tagsList) {
            sb.append(tag).append(" ");
        }
        if (sb.length() > 1)
            sb.deleteCharAt(sb.length() - 1);
        return sb.toString();
    }

    /**
     * 监听器，点击删除该标签
     */
    private View.OnClickListener clickToRemoveView = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            holder.tagsLinearLayout.removeView(v);
        }
    };

    //--------------------------------添加标签-----END----------------------
    //--------------------------------上传信息-----START----------------------

    /**
     * 点击上传Button
     */
    @Override
    public void onClick(View v) {
        //构建Danger实体类
        danger.setCategory(holder.categorySpinner.getSelectedItem().toString());
        danger.setSubCategory(holder.subCategorySpinner.getSelectedItem().toString());
        danger.setTags(getTagsString());
        danger.setName(holder.titleET.getText().toString());
        danger.setDescription(holder.descriptionET.getText().toString());
        danger.setAddressDescritpion(holder.locationDescritionET.getText().toString());
        danger.setRank(holder.rankRB.getRating());
        danger.setDate(new Date());
        danger.setHelped(0);
        danger.setNumSolved(0);
        danger.setSolved(false);
        //Todo 校验输入值
        RequestQueue mQueue=Volley.newRequestQueue(this.getActivity().getApplicationContext());
        PostRequest postRequest=new PostRequest(HttpHelper.ROOT+"/server/Danger-add",HttpHelper.makeParamsMap("danger",danger),this,this);
        mQueue.add(postRequest);
        mQueue.start();
        Log.d("uploadFragment",danger.toString());
        progressDialog = new ProgressDialog(menuActivity);
        progressDialog.setMessage("上传中…");
        progressDialog.show();
    }

    @Override
    public void onResponse(String s) {
        //请求成功
        Log.d("上传", s);
        progressDialog.dismiss();
        //上传图片
        try {
            int id = Integer.parseInt(s.trim());
            danger.id = id;
            if(fileMap.isEmpty()){
                Toast.makeText(menuActivity, "信息上传成功！感谢您的分享", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(menuActivity, "信息上传成功！后台上传图片中……", Toast.LENGTH_LONG).show();
                uploadPicture();
            }
        }catch (Exception e){
            Toast.makeText(menuActivity, s, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onErrorResponse(VolleyError volleyError) {
        //请求失败
        progressDialog.dismiss();
        Toast.makeText(menuActivity, "请求失败", Toast.LENGTH_LONG).show();
    }

    //--------------------------------上传信息-----END----------------------

    //------------------------定位模块--------START----------------------------------
    private LocationClient locClient;

    /**
     * 初始化定位模块
     */
    private void initLocModule() {
        locClient = new LocationClient(this.getActivity().getApplicationContext());
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);//高精度定位模式
        option.setIsNeedAddress(true);//设置包含地址信息
        option.setScanSpan(LocationClientOption.MIN_SCAN_SPAN);
        option.setOpenGps(true);// 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        locClient.setLocOption(option);
        holder.getLocButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.locationLatLon.setText("重新定位中……");
                getLocation();
            }
        });
        holder.jumpToMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(menuActivity,"长按地图选点",Toast.LENGTH_LONG).show();
                menuActivity.changeFragment(MapFragment.class);
            }
        });
        if(this.locationFromMap!=null){
            setLocation(locationFromMap);
        }else {
            getLocation();
        }
    }

    private void getLocation() {
        locClient.registerLocationListener(new OnceLocationListener());
        locClient.start();
    }

    /**
     * 设置位置
     * 供外部调用的
     * */
    public void setLocation(LatLng latLng){
        if(holder.locationLatLon==null){
            this.locationFromMap=latLng;
            return;
        }
        double lat = latLng.latitude;
        double lon = latLng.longitude;
        danger.setLatitude(lat);
        danger.setLongitude(lon);
        danger.setAccuracy(0f);
        StringBuilder sb = new StringBuilder();
        if (lon > 0) {
            sb.append("东经：").append(lon).append("度\n");
        } else {
            sb.append("西经：").append(-lon).append("度\n");
        }
        if (lat > 0) {
            sb.append("北纬：").append(lat).append("度\n");
        } else {
            sb.append("南纬：").append(-lat).append("度\n");
        }
        sb.append("误差范围：").append(0f).append("米");
        holder.locationLatLon.setText(sb.toString());
        reverseGeoCode(latLng);
    }

    /**
     * 发起反地理编码
     *
     * @param latLng 经纬度
     */
    private void reverseGeoCode(LatLng latLng) {
        GeoCoder geoCoder = GeoCoder.newInstance();
        ReverseGeoCodeOption option = new ReverseGeoCodeOption().location(latLng);
        if (geoCoder.reverseGeoCode(option)) {
            //反地理编码发起成功
            geoCoder.setOnGetGeoCodeResultListener(new OnGetGeoCoderResultListener() {
                @Override
                public void onGetReverseGeoCodeResult(ReverseGeoCodeResult reverseGeoCodeResult) {
                    holder.locationAddress.setText(reverseGeoCodeResult.getAddress());
                    ReverseGeoCodeResult.AddressComponent detail = reverseGeoCodeResult.getAddressDetail();
                    danger.setProvince(detail.province);
                    danger.setCity(detail.city);
                    danger.setDistrict(detail.district);
                    danger.setStreet(detail.street);
                }

                @Override
                public void onGetGeoCodeResult(GeoCodeResult geoCodeResult) {
                }
            });
        } else {
            //反地理编码发起失败
            holder.locationAddress.setText("无地址信息，请重试");
        }
    }

    /**
     * 只获取一次位置的监听器
     */
    private class OnceLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null)
                return;
            double lat = bdLocation.getLatitude();
            double lon = bdLocation.getLongitude();
            danger.setLatitude(lat);
            danger.setLongitude(lon);
            danger.setAccuracy(bdLocation.getRadius());
            StringBuilder sb = new StringBuilder();
            if (lon > 0) {
                sb.append("东经：").append(lon).append("度\n");
            } else {
                sb.append("西经：").append(-lon).append("度\n");
            }
            if (lat > 0) {
                sb.append("北纬：").append(lat).append("度\n");
            } else {
                sb.append("南纬：").append(-lat).append("度\n");
            }
            sb.append("误差范围：").append((int)(bdLocation.getRadius())).append("米");
            holder.locationLatLon.setText(sb.toString());
            //是否有地址信息
            if (bdLocation.hasAddr()) {
                holder.locationAddress.setText(bdLocation.getAddrStr());
                danger.setProvince(bdLocation.getProvince());
                danger.setCity(bdLocation.getCity());
                danger.setDistrict(bdLocation.getDistrict());
                danger.setStreet(bdLocation.getStreet());
            } else {
                //反地理编码
                reverseGeoCode(new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude()));
            }
            locClient.stop();
            locClient.unRegisterLocationListener(this);
        }

    }

    //------------------------定位模块--------END----------------------------------

    //------------------------添加图片--------START--------------------------------

    private static final int SELECT_PICTURE = 2;
    private static final int SELECT_CAMER = 3;
    Bitmap bmp;
    /**保存用于上传的照片*/
    Map<View,File> fileMap=new HashMap<View, File>();

    /**
     * 初始化添加图片模块
     */
    private void initTakePicture() {
        holder.takePictureButtion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //startActivityForResult(intent, 1);
                CharSequence[] items = {"相册", "相机"};
                new AlertDialog.Builder(getActivity())
                        .setTitle("选择图片来源")
                        .setItems(items, new AlertDialog.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (which == 0) {
                                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                                    intent.setType("image/*");
                                    startActivityForResult(Intent.createChooser(intent, "选择图片"), SELECT_PICTURE);
                                } else {
                                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    startActivityForResult(intent, SELECT_CAMER);
                                }
                            }
                        })
                        .create().show();
            }
        });
    }

    /**获取从相机或图库传来的照片*/
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            String sdStatus = Environment.getExternalStorageState();
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
                Log.v("TestFile", "SD card is not avaiable/writeable right now.");
                return;
            }
            File imageFile=null;
            if (requestCode == SELECT_CAMER) {
                //从相机获取
                Bundle bundle = data.getExtras();
                bmp = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式
                File file = new File("/sdcard/myImage/");
                FileOutputStream bout = null;
                file.mkdirs();// 创建文件夹
                String fileName = "/sdcard/myImage/"+(fileMap.size()+1)+".jpg";
                try {
                    bout = new FileOutputStream(fileName);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, bout);// 把数据写入文件
                    imageFile=new File(fileName);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        bout.flush();
                        bout.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                //从图库获取
                Uri uri = data.getData();
                ContentResolver cr = this.getActivity().getContentResolver();
                imageFile=uri2File(uri);
                try {
                    bmp = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            addPicture(bmp,imageFile);// 将图片显示在ImageView里
        }
    }
    private File uri2File(Uri uri) {
        File file = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor actualimagecursor = getActivity().managedQuery(uri, proj, null,
                null, null);
        int actual_image_column_index = actualimagecursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        actualimagecursor.moveToFirst();
        String img_path = actualimagecursor
                .getString(actual_image_column_index);
        file = new File(img_path);
        return file;
    }

    /**添加照片*/
    private void addPicture(Bitmap bitmap,File file){
        if(holder.picture0!=null){
            holder.pictureLinearLayout.removeView(holder.picture0);
            holder.picture0=null;
        }
        ImageView imageView=new ImageView(getActivity().getApplicationContext());
        imageView.setAdjustViewBounds(true);
        imageView.setMaxHeight(128);
        imageView.setMaxWidth(128);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setImageBitmap(bitmap);
        imageView.setPadding(4,4,4,4);
        imageView.setOnClickListener(clickToRemoveImage);
        fileMap.put(imageView,file);
        holder.pictureLinearLayout.addView(imageView);


    }
    View.OnClickListener clickToRemoveImage=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            holder.pictureLinearLayout.removeView(v);
            fileMap.remove(v);
        }
    };

    //------------------------添加图片---------END---------------------------------
    //------------------------上传图片--------START--------------------------------
    /**上传图片*/
    private void uploadPicture(){
        if(fileMap.isEmpty()){
            return;
        }
        Log.d("上传文件","文件数"+fileMap.size());
        RequestQueue mSingleQueue = Volley.newRequestQueue(this.getActivity(), new MultiPartStack());
        String url=HttpHelper.ROOT+"/server/Danger-uploadPicture";

        MultiPartStringRequest multiPartRequest = new MultiPartStringRequest(
                Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Toast.makeText(getActivity(), s, Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(getActivity(),"上传失败.",Toast.LENGTH_LONG);
                Log.d("上传失败",volleyError.toString());
            }
        }) {

            @Override
            public Map<String, File> getFileUploads() {
                Map<String,File> map=new IdentityHashMap<String, File>();
                for(File f:fileMap.values()){
                    map.put(new String("upload"),f);
                }
                Log.d("上传文件","参数包含的文件个数"+map.size());
                return map;
            }

            @Override
            public Map<String, String> getStringUploads() {
                Map<String,String> params=new HashMap<String, String>();
                params.put("danger.id",danger.id+"");
                return params;
            }

        };
        mSingleQueue.add(multiPartRequest);
        mSingleQueue.start();
    }
    //------------------------上传图片---------END---------------------------------
    class ViewHolder{
        @InjectView(R.id.upload_window_category)
        Spinner categorySpinner;
        @InjectView(R.id.upload_window_subcategory)
        Spinner subCategorySpinner;
        @InjectView(R.id.upload_window_title)
        EditText titleET;
        @InjectView(R.id.upload_window_rank)
        RatingBar rankRB;
        @InjectView(R.id.upload_window_description)
        EditText descriptionET;
        @InjectView(R.id.upload_window_location_description)
        EditText locationDescritionET;
        @InjectView(R.id.upload_window_upload_button)
        Button uploadButton;
        @InjectView(R.id.upload_window_latlon)
        TextView locationLatLon;
        @InjectView(R.id.upload_window_address)
        TextView locationAddress;
        @InjectView(R.id.upload_window_getLocButton)
        Button getLocButton;
        @InjectView(R.id.upload_window_jumpToMap)
        Button jumpToMapButton;

        @InjectView(R.id.upload_window_tags_input_editText)
        EditText tagsInputEditText;
        @InjectView(R.id.upload_window_tags_linearlayout)
        LinearLayout tagsLinearLayout;
        @InjectView(R.id.upload_window_tags_input_button)
        Button tagInputButton;

        @InjectView(R.id.upload_window_uploadpicture_button)
        Button takePictureButtion;
        @InjectView(R.id.upload_window_picturell)
        LinearLayout pictureLinearLayout;
        @InjectView(R.id.upload_window_picture)
        ImageView picture0;

    }
}