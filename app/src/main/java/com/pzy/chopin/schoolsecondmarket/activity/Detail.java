package com.pzy.chopin.schoolsecondmarket.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pzy.chopin.schoolsecondmarket.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class Detail extends AppCompatActivity {
    private static String url="http://schoolc2c.applinzi.com/shopImooc/collect.php";
    private static String stateurl="http://schoolc2c.applinzi.com/shopImooc/ifco.php";
    private static int REQUEST_FOR_UPDATE=123456;
    private static final String ACTION="testforActivityResult";

    BroadcastReceiver broadcastReceiver;

    /**
     * 0：表示未收藏
     * 1：表示已经收藏
     * 2：表示用户修改
     * 3：表示用户上传
     */
    private static int TAG=0;

    SharedPreferences sp;

    TextView tv_goodnamne,tv_goodprice,tv_goodusername,tv_gooddesc,tv_email;
    ImageView iv_pic;
    Button btn_collect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();
        loaddata();
    }

    private void loaddata() {
        Intent intent=getIntent();
        final String name=intent.getStringExtra("name");
        final String price=intent.getStringExtra("price");
        String username=intent.getStringExtra("belongto_man");
        final String descrip=intent.getStringExtra("descrip");
        String picpath=intent.getStringExtra("picpath");
        String email=intent.getStringExtra("email");
        final int goodsid=Integer.valueOf(intent.getStringExtra("goodsid"));

        tv_goodnamne.setText(name);
        tv_goodprice.setText("￥"+price);
        tv_goodusername.setText(username);
        tv_gooddesc.setText(descrip);
        tv_email.setText(email);

        Glide.with(getApplicationContext()).load(picpath).into(iv_pic);

        btn_collect.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(TAG==2){
                            //TODO进行用户修改操作
                            Intent i=new Intent(getApplicationContext(),UpdataGoods.class);
                            i.putExtra("name",name);
                            i.putExtra("price",price);
                            i.putExtra("descrip",descrip);
                            i.putExtra("uid",goodsid);
                            Log.d("goodid", String.valueOf(goodsid));
                            startActivity(i);
                        }else {
                            RequestParams params=new RequestParams(url);
                            params.addParameter("uId",sp.getInt("uid",1));
                            params.addParameter("cId",goodsid);
                            x.http().post(params, new Callback.CommonCallback<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    String flag=btn_collect.getText().toString().trim();
                                    if(flag.equals("收藏")){
                                        btn_collect.setText("取消收藏");
                                        btn_collect.setBackgroundColor(Color.parseColor("#89848f"));
                                    }else {
                                        btn_collect.setText("收藏");
                                        btn_collect.setBackgroundColor(Color.parseColor("#FF34A350"));
                                    }
                                }

                                @Override
                                public void onError(Throwable ex, boolean isOnCallback) {
                                    Toast.makeText(Detail.this, "操作失误，请重试", Toast.LENGTH_SHORT).show();
                                }

                                @Override
                                public void onCancelled(CancelledException cex) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });
                        }
                    }
                }
        );


    }

    private void buttonstateget(int cid) {
        int uid=sp.getInt("uid",00000);
        RequestParams params=new RequestParams(stateurl);
        params.addParameter("uId",uid);
        params.addParameter("cId",cid);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    String is_collected = object.getString("message");
                    if(is_collected.equals("collection")){
                        TAG=1;
                        btn_collect.setText("取消收藏");
                        btn_collect.setBackgroundColor(Color.parseColor("#89848f"));
                    }else {
                        btn_collect.setText("收藏");
                        btn_collect.setBackgroundColor(Color.parseColor("#FF34A350"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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

    private void init() {
        tv_goodnamne= (TextView) findViewById(R.id.tv_goodsname);
        tv_goodprice= (TextView) findViewById(R.id.tv_goodsprice);
        tv_goodusername= (TextView) findViewById(R.id.tv_goodsusername);
        tv_gooddesc= (TextView) findViewById(R.id.tv_goodsdescr);
        tv_email= (TextView) findViewById(R.id.tv_email);

        sp=LoginActivity.mlogin.getSharedPreferences("login", MODE_PRIVATE);

        btn_collect= (Button) findViewById(R.id.btn_collect);

        iv_pic= (ImageView) findViewById(R.id.iv_detail);

        broadcastReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               String name=intent.getStringExtra("name");
                String price=intent.getStringExtra("price");
                String desc=intent.getStringExtra("desc");
                tv_goodnamne.setText(name);
                tv_gooddesc.setText(desc);
                tv_goodprice.setText(price);
            }
        };

        //广播接收器
        registerBoradcastReceiver();

        Intent intent=getIntent();
        String name=sp.getString("username","");
        Log.d("ussname",name+":"+intent.getStringExtra("belongto_man"));
        if(name.equals(intent.getStringExtra("belongto_man"))){
            TAG=2;
            btn_collect.setText("进行修改");
            btn_collect.setBackgroundColor(Color.parseColor("#FF34A350"));

        }else {
            buttonstateget(Integer.valueOf(intent.getStringExtra("goodsid")));
        }

    }

    private void registerBoradcastReceiver() {
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }

}
