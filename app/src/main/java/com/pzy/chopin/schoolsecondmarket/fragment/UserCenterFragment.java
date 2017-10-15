package com.pzy.chopin.schoolsecondmarket.fragment;


import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.adpter.UserFragmentAdapter;
import com.pzy.chopin.schoolsecondmarket.utils.ImageFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * 用户中心页面
 * Created by Chopin on 2017/9/2.
 */

public class UserCenterFragment extends Fragment {

    ImageFactory factory;

    private static String url_changeicon="http://schoolc2c.applinzi.com/shopImooc/updateface.php";

    private static int REQUEST_CODE_GALLERY=1234;
    private static int REQUEST_CODE_CAMERA=4321;

    private TabLayout tab_usrcenter;

    private ViewPager mViewPager;
    private UserFragmentAdapter userfragmentAdapter;
    private  List<Fragment> fragmentlist=new ArrayList<>();

    private ImageView iv_usericon;
    private TextView  tv_username;



    SharedPreferences sp_user;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_usercenter,container,false);
        factory=new ImageFactory();
        initView(view);

        return view;
    }

    private void initdata() {

        fragmentlist.clear();

        fragmentlist.add(new UserCollection());
        fragmentlist.add(new UserHub());
        fragmentlist.add(new UserSetting());

        userfragmentAdapter=new UserFragmentAdapter(getChildFragmentManager(),fragmentlist);

        tab_usrcenter.setupWithViewPager(mViewPager);

        mViewPager.setAdapter(userfragmentAdapter);

        mViewPager.setCurrentItem(0);

    }

    private void initView(View view) {


        tab_usrcenter=(TabLayout) view.findViewById(R.id.tab_usercenter);

        mViewPager = (ViewPager)view.findViewById(R.id.id_stickynavlayout_viewpager);

        iv_usericon=(ImageView) view.findViewById(R.id.iv_usericon);
        tv_username=(TextView) view.findViewById(R.id.tv_username);

        sp_user=getContext().getSharedPreferences("login", MODE_PRIVATE);

        tv_username.setText(sp_user.getString("username",""));
        String picpath=sp_user.getString("face","");

        initdata();

        Glide.with(getContext()).load(picpath).into(iv_usericon);
        //TODO:对用户头像进行设置，然后设置用户头像点击事件
        iv_usericon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changetitle(view);
            }
        });
    }

    private void changetitle(View view) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        final AlertDialog dialog=builder.create();
        View dialogview=View.inflate(getContext(),R.layout.dialog_select_photo,null);
        Button btn_select_gallery=(Button) dialogview.findViewById(R.id.bt_select_gallery);
        Button btn_select_camera=(Button) dialogview.findViewById(R.id.bt_select_camera);

        dialog.setView(dialogview);
        dialog.show();

        btn_select_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Intent.ACTION_PICK, null);
                intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                dialog.dismiss();
                startActivityForResult(intent1, REQUEST_CODE_GALLERY);
            }
        });

        btn_select_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                startActivityForResult(intent2, REQUEST_CODE_CAMERA);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode>0){

        }else {
            if (requestCode==REQUEST_CODE_GALLERY){

            }
            if (requestCode==REQUEST_CODE_CAMERA){

            }
            Uri mImageCaptureUri = data.getData();
            Bitmap photoBmp = null;
            if (mImageCaptureUri != null) {
                try {
                    //图片进行压缩
                    photoBmp =factory.getBitmapFormUri(MainActivity.mainActivity, mImageCaptureUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //对图片进行旋转处理(对拍照后的图片进行操作，对从相册中选择的不需进行操作)
            int reg=factory.getBitmapDegree( factory.getRealPathFromUri(getContext(),mImageCaptureUri));
            photoBmp=factory.rotateBitmapByDegree(photoBmp,reg);
            photoBmp=factory.ImageCrop(photoBmp,true);
            iv_usericon.setImageBitmap(photoBmp);
            int uid=sp_user.getInt("uid",000);
            uploaddata(photoBmp,uid);
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void uploaddata(Bitmap bitmap, int uid) {
        String path=factory.saveBitmap(getContext(),bitmap);
        RequestParams params=new RequestParams(url_changeicon);
        params.addParameter("uId",uid);
        params.addBodyParameter("file",new File(path));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Log.d("resultsssss",result);
                Toast.makeText(getContext(), "修改成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }


    /**
     * 通过uri获取图片绝对路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }

    /**
     * 通过uri获得bitmp
     * @param uri
     * @return
     */
    private Bitmap getBitmapFromUri(Uri uri) {
        try {
            // 读取uri所在的图片
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            Matrix matrix = new Matrix();
            // 设置旋转角度
            matrix.setRotate(90);
            // 重新绘制Bitmap
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对图片进行切割
     * @param bitmap
     * @param isRecycled
     * @return
     */
    public static Bitmap ImageCrop(Bitmap bitmap, boolean isRecycled) {
        if (bitmap == null) {
            return null;
        }
        int w = bitmap.getWidth(); // 得到图片的宽，高
        int h = bitmap.getHeight();
        int wh = w > h ? h : w;// 裁切后所取的正方形区域边长
        int retX = w > h ? (w - h) / 2 : 0;// 基于原图，取正方形左上角x坐标
        int retY = w > h ? 0 : (h - w) / 2;
        Bitmap bmp = Bitmap.createBitmap(bitmap, retX, retY, wh, wh, null,false);
        if (isRecycled && bitmap != null && !bitmap.equals(bmp)
                && !bitmap.isRecycled())
        {
            bitmap.recycle();
            bitmap = null;
        }
        // 下面这句是关键
        return bmp;
    }




}
