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

public class Register extends AppCompatActivity {
    EditText emailet,usernameet,passwordet,comfirgue_passwordet;
    Button btn_register;
    private static int INTERNET_POST_ISSUEECSS=10;
    private static String url="http://schoolc2c.applinzi.com/shopImooc/acreg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        init();

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email= emailet.getText().toString().trim();
                String username=usernameet.getText().toString().trim();
                String  password=passwordet.getText().toString().trim();
                String passwordconfig=comfirgue_passwordet.getText().toString().trim();

                if(email.equals("")||username.equals("")||password.equals("")||passwordconfig.equals("")){
                    Toast.makeText(Register.this,"请填完所有信息",Toast.LENGTH_SHORT).show();
                }
                else{
                    isvalid(email,username,password,passwordconfig);
                }

            }
        });
    }

    private void isvalid( String email,String username,String  password, String passwordconfig) {
        if(password.equals(passwordconfig)){
            if (isEmailValid(email)) {
                if(isPasswordValid(password)){
                    Log.d("email",email);
                    Log.d("username",username);
                    Log.d("password",password);
                    Log.d("passwordconfig",passwordconfig);
                    //上传到后台
                    int flag=onpost(email,username,password);
                    if(flag==INTERNET_POST_ISSUEECSS){
                        startActivity(new Intent(Register.this, MainActivity.class));
                    }

                }
                else {
                    Toast.makeText(Register.this,"密码太短",Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(Register.this,"非法邮箱地址",Toast.LENGTH_SHORT).show();
            }
        }
        else {
            Toast.makeText(Register.this,"密码不统一",Toast.LENGTH_SHORT).show();
        }
    }

    private int onpost(String email,String username,String  password) {
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("email",email);
        params.addBodyParameter("username",username);
        params.addBodyParameter("password",password);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                INTERNET_POST_ISSUEECSS=111;

                Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
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
        return INTERNET_POST_ISSUEECSS;
    }

    //判断用户名是否为有效邮箱格式
    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    //判断密码是否有效（太短）
    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    private void init() {
        emailet= (EditText) findViewById(R.id.reigster_useremail);
        usernameet= (EditText) findViewById(R.id.reigster_username);
        passwordet= (EditText) findViewById(R.id.reigster_password);
        comfirgue_passwordet= (EditText) findViewById(R.id.reigster_passwordForaffirm);
        btn_register= (Button) findViewById(R.id.btn_register_comfirge);
    }
}
