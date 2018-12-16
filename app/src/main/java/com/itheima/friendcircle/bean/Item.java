package com.itheima.friendcircle.bean;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 耿宾 on 2018/12/12.
 */
public class Item {
    public String avatar;
    public String username;
    public String description;
    public String descriptionimage;

    @Override
    public String toString() {
        return "Item{" +
                "avatar='" + avatar + '\'' +
                ", username='" + username + '\'' +
                ", description='" + description + '\'' +
                ", descriptionimage='" + descriptionimage + '\'' +
                '}';
    }
}
