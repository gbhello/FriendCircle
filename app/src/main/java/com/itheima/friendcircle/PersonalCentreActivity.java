package com.itheima.friendcircle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.itheima.friendcircle.bean.Item;
import com.itheima.friendcircle.httphelp.HttpHelp;
import com.itheima.friendcircle.httphelp.HttpMethod;
import com.loopj.android.image.SmartImageView;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 耿宾 on 2018/12/12.
 */
public class PersonalCentreActivity extends Activity implements View.OnClickListener {

    private static final int GET_DATA = 0;
    private ListView lv_personal_centre_content;
    ImageView iv_personal_centre_activity_camera;
    private ArrayList<Item> messageList = new ArrayList<Item>(); //消息集合

    private PersonalCentreAdapter personalCentreAdapter;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GET_DATA:
                    lv_personal_centre_content.setAdapter(personalCentreAdapter);
                    break;
            }
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_centre);

        lv_personal_centre_content = (ListView) findViewById(R.id.lv_personal_centre_content);  //找到对应的listView
        personalCentreAdapter = new PersonalCentreAdapter();

        iv_personal_centre_activity_camera = (ImageView) findViewById(R.id.iv_personal_centre_activity_camera);
        iv_personal_centre_activity_camera.setOnClickListener(this);

        initData();
    }

    @Override
    public void onClick(View v) {
        Intent intent_toEdit = new Intent(PersonalCentreActivity.this, PersonalCentreEditActivity.class);
        startActivity(intent_toEdit);
    }

    public void initData() {
        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL(HttpHelp.listViewPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(10000);
                    connection.setRequestMethod(String.valueOf(HttpMethod.GET));

                    //获取响应码，检查连接状态
                    int responseCode = connection.getResponseCode();
                    if (responseCode == 200) {
                        InputStream inputStream = connection.getInputStream();
                        XmlPullParser xmlPullParser = Xml.newPullParser();
                        xmlPullParser.setInput(inputStream, "utf-8");
                        int eventType = xmlPullParser.getEventType();
                        Item item = null;

                        //解析xml文件信息
                        while (eventType != XmlPullParser.END_DOCUMENT) {
                            switch (eventType) {
                                case XmlPullParser.START_TAG:
                                    if ("item".equals(xmlPullParser.getName())) {
                                        item = new Item();
                                    } else if ("avatar".equals(xmlPullParser.getName())) {
                                        item.avatar = xmlPullParser.nextText();
                                    } else if ("description".equals(xmlPullParser.getName())) {
                                        item.description = xmlPullParser.nextText();
                                    } else if ("username".equals(xmlPullParser.getName())) {
                                        item.username = xmlPullParser.nextText();
                                    }else if ("descriptionimage".equals(xmlPullParser.getName())) {
                                        item.descriptionimage = xmlPullParser.nextText();
                                    }
                                    break;
                                case XmlPullParser.END_TAG:
                                    if ("item".equals(xmlPullParser.getName())) {
                                        messageList.add(item);
                                    }
                                    break;
                            }
                            eventType = xmlPullParser.next();
                        }
                        //打印取到的信息
                        for (Item item1 : messageList) {
                            System.out.println(item1);
                        }
                    }
                    //已经拿到所需数据
                    handler.sendEmptyMessage(GET_DATA);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private class PersonalCentreAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messageList.size();
        }

        @Override
        public Object getItem(int position) {
            return messageList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            if (convertView == null) {
                view = View.inflate(PersonalCentreActivity.this, R.layout.personal_centre_layout, null);
            } else {
                view = convertView;
            }

            //找到要修改的控件
            SmartImageView avatarView = (SmartImageView) view.findViewById(R.id.iv_personal_centre_layout_avatar);
            TextView usernameView = (TextView) view.findViewById(R.id.tv_personal_centre_layout_username);
            TextView descriptionView = (TextView) view.findViewById(R.id.tv_personal_centre_layout_textcontent);
            SmartImageView descriptionImageView = (SmartImageView) view.findViewById(R.id.iv_personal_centre_layout_imagecontent);

            //找到要显示的数据

            Item messageItem = messageList.get(position);

            //讲数据设置到对应的控件上
            usernameView.setText(messageItem.username);
            descriptionView.setText(messageItem.description);
            avatarView.setImageUrl(messageItem.avatar);
            descriptionImageView.setImageUrl(messageItem.descriptionimage);


            return view;
        }
    }

}