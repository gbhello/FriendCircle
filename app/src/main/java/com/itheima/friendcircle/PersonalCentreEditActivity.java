package com.itheima.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class PersonalCentreEditActivity extends Activity implements View.OnClickListener {

    TextView tv_personal_centre_edit_publish;
    TextView tv_personal_centre_edit_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_centre_edit);

        //找到控件
        tv_personal_centre_edit_publish = (TextView) findViewById(R.id.tv_personal_centre_edit_publish);//发表朋友圈
        tv_personal_centre_edit_cancel = (TextView) findViewById(R.id.tv_personal_centre_edit_cancel);//取消发表

        //为文字添加点击事件
        tv_personal_centre_edit_publish.setOnClickListener(this);
        tv_personal_centre_edit_cancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.tv_personal_centre_edit_publish){
            Intent intent_toPublic = new Intent(PersonalCentreEditActivity.this,PersonalCentreActivity.class);
            startActivity(intent_toPublic);
        }else if(v.getId() == R.id.tv_personal_centre_edit_cancel){
            Intent intent_toCancel = new Intent(PersonalCentreEditActivity.this,PersonalCentreActivity.class);
            startActivity(intent_toCancel);
        }
    }
}
