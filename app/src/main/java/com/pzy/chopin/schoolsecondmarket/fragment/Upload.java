package com.pzy.chopin.schoolsecondmarket.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoFragment;
import com.jph.takephoto.model.TResult;
import com.pzy.chopin.schoolsecondmarket.activity.MainActivity;
import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.activity.UploadGoods;
import com.pzy.chopin.schoolsecondmarket.utils.ImageFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;


/**
 * Created by Chopin on 2017/9/11.
 */

public class Upload extends TakePhotoFragment implements View.OnClickListener{

    public static final int PHOTO_REQUEST_CAREMA=111;
    public static final int PHOTO_REQUEST_GALLERY=222;
    CardView cd_photo,cd_ablum;
    ImageFactory factory;
    Uri imageUri;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_upload,container,false);
        cd_photo=(CardView) v.findViewById(R.id.cardview_photo);
        cd_ablum=(CardView) v.findViewById(R.id.cardview_ablum);
        cd_ablum.setOnClickListener(this);
        cd_photo.setOnClickListener(this);
        factory=new ImageFactory();

        File file=new File(Environment.getExternalStorageDirectory(), "/temp/"+System.currentTimeMillis() + ".jpg");
        if (!file.getParentFile().exists())file.getParentFile().mkdirs();
        imageUri = Uri.fromFile(file);

        return  v;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cardview_photo:
                TakePhoto takePhoto=getTakePhoto();
                takePhoto.onPickFromCapture(imageUri);
//              Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
//                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                break;
            case R.id.cardview_ablum:
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                break;
        }
    }

    @Override
    public void takeSuccess(TResult result) {
        super.takeSuccess(result);
        Toast.makeText(getContext(),"操作成功",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode>-1){

        }else {
            Log.i("sadds", String.valueOf(22));
            Intent intent=new Intent(getContext(), UploadGoods.class);
            Bitmap photoBmp = null;
            if (imageUri != null) {
                try {
                    //图片进行压缩
                    photoBmp =factory.getBitmapFormUri(MainActivity.mainActivity, imageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //对图片进行旋转处理(对拍照后的图片进行操作，对从相册中选择的不需进行操作)
                int reg=factory.getBitmapDegree( factory.getRealPathFromUri(getContext(),imageUri));
                photoBmp=factory.rotateBitmapByDegree(photoBmp,reg);

            //图片转字节流
            ByteArrayOutputStream baos=new ByteArrayOutputStream();
            photoBmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte [] bitmapByte =baos.toByteArray();
            intent.putExtra("bitmap", bitmapByte);
            startActivity(intent);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
