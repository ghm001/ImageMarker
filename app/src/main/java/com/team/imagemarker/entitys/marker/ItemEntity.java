package com.team.imagemarker.entitys.marker;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by Lmy on 2017/5/21.
 * email 1434117404@qq.com
 */

public class ItemEntity implements Serializable{

    private String coverImageUrl;
    private String[] tags;

    public ItemEntity(){

    }

    public ItemEntity(JSONObject jsonObject) {
        this.coverImageUrl = jsonObject.optString("coverImageUrl");
        this.tags = jsonObject.optString("imgTag").equals("") ? new String[]{} : jsonObject.optString("imgTag").split("-");
    }

    public ItemEntity(String coverImageUrl, String[] tags){
        this.coverImageUrl = coverImageUrl;
        this.tags = tags;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "ItemEntity{" +
                "coverImageUrl='" + coverImageUrl + '\'' +
                ", tags=" + Arrays.toString(tags) +
                '}';
    }
}
