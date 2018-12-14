package com.itheima.friendcircle;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


public class SigninSuccess extends ActionBarActivity implements View.OnClickListener {
    private TextView tv_signin_tologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin_success);

        TextView tv_signin_tologin = (TextView)findViewById(R.id.tv_signin_tologin);
        tv_signin_tologin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        Intent intent_tologin = new Intent(SigninSuccess.this, MainActivity.class);
        startActivity(intent_tologin);
    }
}
