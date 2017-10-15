package com.pzy.chopin.schoolsecondmarket.activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.Toast;

import com.pzy.chopin.schoolsecondmarket.R;
import com.pzy.chopin.schoolsecondmarket.utils.ImageFactory;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

public class UploadGoods extends AppCompatActivity implements
    NumberPicker.OnValueChangeListener, NumberPicker.Formatter, NumberPicker.OnScrollListener {
    private static final String url="http://schoolc2c.applinzi.com/shopImooc/admin/doAdminAction.php?act=addPro";

    ImageFactory factory;

    int currtentitem=0;

    String[] cate = {"电器","日用品","数码产品","书籍"};

    ImageView iv_return,iv_goodspic;

    EditText et_title,et_desc,et_price;

    NumberPicker np_cate;

    Button btn_upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_goods);
        init();

        //获取图片字节流
        Intent intent=getIntent();
        byte [] bis=intent.getByteArrayExtra("bitmap");
        //将字节流写入bitmap中
        Bitmap bitmap= BitmapFactory.decodeByteArray(bis, 0, bis.length);
        //把bitmap切割为正方形
        bitmap=ImageCrop(bitmap,true);
        iv_goodspic.setImageBitmap(bitmap);

        //获取uid
        final int uid= getSharedPreferences("login", MODE_PRIVATE).getInt("uid",1111);

        btn_upload= (Button) findViewById(R.id.btn_upload);
        final Bitmap finalBitmap = bitmap;
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name=et_title.getText().toString().trim();
                double price=Double.parseDouble(et_price.getText().toString().trim());
                String desc=et_desc.getText().toString().trim();
                int cateid=getcateid(currtentitem);
                if(name.equals("")||et_price.getText().equals("")||desc.equals("")){
                    Toast.makeText(getApplicationContext(),"请填写完所有信息",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(UploadGoods.this, "请稍等片刻", Toast.LENGTH_SHORT).show();
                    upload(name,cateid,price,uid,desc, finalBitmap);
                }
            }
        });
    }

    private int getcateid(int currtentitem) {
        int id = -1;
        switch (currtentitem){
            case 0:
                id=5;
                break;
            case 1:
                id= 6;
                break;
            case 2:
                id=8;
                break;
            case 3:
                id=9;
                break;
        }
        return id;
    }

    private void init() {

        factory=new ImageFactory();

        iv_goodspic= (ImageView) findViewById(R.id.iv_goodspic);
        et_title= (EditText) findViewById(R.id.et_title);
        et_price= (EditText) findViewById(R.id.et_price);
        et_desc= (EditText) findViewById(R.id.et_desc);

        np_cate= (NumberPicker) findViewById(R.id.np_cate);
        np_cate.setFormatter(this);
        np_cate.setOnScrollListener(this);
        np_cate.setOnValueChangedListener(this);
        np_cate.setDisplayedValues(cate);
        np_cate.setMinValue(0);
        np_cate.setMaxValue(cate.length - 1);
        np_cate.setValue(0);


        iv_return= (ImageView) findViewById(R.id.iv_upload_return);
        //点击箭头返回前一个activity
        iv_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    //将bitmap保存到本地并返回path
    public static String saveBitmap(Context context, Bitmap mBitmap) {
        String savePath;
        File filePic;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            savePath = SD_PATH;
        } else {
            savePath = context.getApplicationContext().getFilesDir()
                    .getAbsolutePath()
                    + IN_PATH;
        }
        try {
            filePic = new File(savePath + generateFileName() + ".jpg");
            if (!filePic.exists()) {
                filePic.getParentFile().mkdirs();
                filePic.createNewFile();
            }FileOutputStream fos = new FileOutputStream(filePic);
            mBitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }

        return filePic.getAbsolutePath();
    }

    private static final String SD_PATH = "/sdcard/dskqxt/pic/";
    private static final String IN_PATH = "/dskqxt/pic/";

    /**
     * 上传
     */
    private void upload(String name,int cateid,double price
                ,int uid,String desc,Bitmap bitmap) {
        String path=saveBitmap(getApplicationContext(),bitmap);

        RequestParams params=new RequestParams(url);
        params.setMultipart(true);
        params.addBodyParameter("pName",name);
        params.addParameter("cId",cateid);
        params.addParameter("iPrice",price);
        params.addBodyParameter("pDesc",desc);
        params.addParameter("uId",uid);
        params.addBodyParameter("file",new File(path));
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                finish();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(UploadGoods.this, "发生了错误", Toast.LENGTH_SHORT).show();
                ex.printStackTrace();
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
     * 将图片切割为正方形
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

    @Override
    public String format(int value) {
        String tmpStr = String.valueOf(value);
        return tmpStr;
    }

    @Override
    public void onScrollStateChange(NumberPicker view, int scrollState) {

    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        currtentitem=newVal;
    }
}
