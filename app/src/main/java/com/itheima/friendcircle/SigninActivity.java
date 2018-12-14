package com.itheima.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class SigninActivity extends Activity implements View.OnClickListener {

    private Button bt_signin_sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        Button bt_signin_sign = (Button)findViewById(R.id.bt_signin_sign);
        bt_signin_sign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent_tosigninsuccess = new Intent(SigninActivity.this, SigninSuccess.class);
        startActivity(intent_tosigninsuccess);
    }
}
