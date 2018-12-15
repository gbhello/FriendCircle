package com.itheima.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itheima.friendcircle.httphelp.HttpHelp;
import com.itheima.friendcircle.httphelp.HttpMethod;
import com.itheima.friendcircle.utils.Utils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.Policy;


public class MainActivity extends Activity implements View.OnClickListener {

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
                    String username = et_login_username.getText().toString().trim();
                    String password = et_login_password.getText().toString().trim();

                    //拼接url
                    try {
                        //解决用户名或者是密码中包含中文乱码问题
                        String params = "username=" + URLEncoder.encode(username,"utf-8") + "&password=" + URLEncoder.encode(password,"utf-8");

                        URL url = new URL(HttpHelp.loginPath);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setRequestMethod(String.valueOf(HttpMethod.POST));
                        connection.setConnectTimeout(10000);

                        //设置和post请求相关的请求头
                        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                        //传递的数据长度
                        connection.setRequestProperty("Content-Length",String.valueOf(params.length()));
                        //打开输出流
                        connection.setDoOutput(true);
                        //通过流把请求体写到服务端
                        connection.getOutputStream().write(params.getBytes());

                        int responseCode = connection.getResponseCode();
                        if (responseCode == 200) {
                            InputStream inputStream = connection.getInputStream();
                            String result = Utils.getStringFromStream(inputStream);
                            showToast(result);
                            //登录验证成功，跳转到朋友圈页面
                            Intent intent_personalCentre = new Intent(MainActivity.this, PersonalCentreActivity.class);
                            startActivity(intent_personalCentre);
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
