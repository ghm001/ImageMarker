package com.team.imagemarker.fragments.hobby.test;

import java.io.Serializable;


public class PicModel implements Serializable,IJsonModel{
    /**
     * 图片id
     */
    public int picid;
    /**
     * 图片地址
     */
    public String imageUrl;


    public PicModel(int picid,String imageUrl){
        this.picid = picid;
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "PicModel{" +
                "picid=" + picid +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
