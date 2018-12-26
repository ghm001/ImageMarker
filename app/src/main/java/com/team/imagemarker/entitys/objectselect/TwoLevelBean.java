package com.team.imagemarker.entitys.objectselect;

/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class TwoLevelBean {
    public String city;
    public boolean isTitle;//判断是否为一级领域，来进行加载数据
    public String province;
    public String tag;//一个position，同时将一级领域与二级领域绑定

    public void setTitle(boolean title) {
        isTitle = title;
    }

    public void setProvince(String province) {
        this.province = province;

    }

    public String getProvince() {
        return province;
    }

    public boolean isTitle() {
        return isTitle;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getTag() {
        return tag;
    }
}
