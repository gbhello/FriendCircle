package com.itheima.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.friendcircle.utils.Utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity implements View.OnClickListener {

    String path = "http://192.168.19.1:8080/Login/LoginServlet";
    private EditText et_login_username;
    private EditText et_login_password;
    private Button bt_login;
    private TextView tv_signin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_login_username = (EditText) findViewById(R.id.et_login_username);//用户名
        et_login_password = (EditText) findViewById(R.id.et_login_password);//密码
        bt_login = (Button) findViewById(R.id.bt_login);//登录按钮
        tv_signin = (TextView) findViewById(R.id.tv_signin);//注册按钮

        bt_login.setOnClickListener(this);
        tv_signin.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_login) {
            new Thread() {
                @Override
                public void run() {
                    //拿到输入的用户名和密码
                    String username = et_login_username.getText().toString();
                    String password = et_login_password.getText().toString();

                    //拼接url
                    String tempUrl = path + "?username=" + username + "&password=" + password;
                    try {
                        URL url = new URL(tempUrl);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod("GET");
                        connection.setConnectTimeout(10000);
                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            InputStream inputStream = connection.getInputStream();
                            String result = Utils.getStringFromStream(inputStream);
                            showToast(result);
                            //登录验证成功，跳转到朋友圈页面
                            
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showToast("网络链接异常");
                    }
                }
            }.start();

        } else if (v.getId() == R.id.tv_signin) {
            //点击注册按钮跳转到注册页面
            Intent intent_signin = new Intent(MainActivity.this, SigninActivity.class);
            startActivity(intent_signin);
        }


    }

    /**
     * 界面提示方法
     *
     * @param str
     */
    private void showToast(final String str) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
