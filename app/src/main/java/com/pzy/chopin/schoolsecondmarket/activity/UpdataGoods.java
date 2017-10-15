package com.pzy.chopin.schoolsecondmarket.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pzy.chopin.schoolsecondmarket.R;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class UpdataGoods extends AppCompatActivity {

    private static final String update_url="http://schoolc2c.applinzi.com/shopImooc/admin/doAdminAction.php?act=editPro&id=";
    private static final String ACTION_SEND="testforActivityResult";


    EditText et_update_name,et_update_price,et_update_desc;
    Button btn_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata_goods);

        init();
    }

    private void init() {

        btn_update= (Button) findViewById(R.id.btn_update);

        et_update_name= (EditText) findViewById(R.id.et_update_name);
        et_update_price= (EditText) findViewById(R.id.et_update_price);
        et_update_desc= (EditText) findViewById(R.id.et_update_desc);

        Intent i=getIntent();
        String name=i.getStringExtra("name");
        String price=i.getStringExtra("price");
        String desc=i.getStringExtra("descrip");
        final int goodid=i.getIntExtra("uid",123);

        et_update_desc.setText(desc);
        et_update_name.setText(name);
        et_update_price.setText(price);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String uname= String.valueOf(et_update_name.getText());
                final String uprice= String.valueOf(et_update_price.getText());
                final String udesc= String.valueOf(et_update_desc.getText());
                RequestParams params=new RequestParams(update_url+goodid);
                params.addBodyParameter("pName",uname);
                params.addBodyParameter("iPrice",uprice);
                params.addBodyParameter("pDesc",udesc);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        Log.d("update_sit",result);
                        Intent intent=new Intent(ACTION_SEND);
                        intent.putExtra("name",uname);
                        intent.putExtra("price",uprice);
                        intent.putExtra("desc",udesc);
                        sendBroadcast(intent);
                        finish();
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
        });
    }
}
