package com.team.imagemarker.entitys.home;

import com.team.imagemarker.entitys.MarkerModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class CategoryModel {
    private int imgId;
    private int imgId1;
    private String name;
    private String simpleMessage;

    private String userName;
    private String integral;

    private String imgUrl;
    private String headUrl;
    private List<MarkerModel> itemList;

    public CategoryModel() {
    }

    public CategoryModel(int imgId, String name, String simpleMessage) {
        this.imgId = imgId;
        this.name = name;
        this.simpleMessage = simpleMessage;
    }

    public CategoryModel(int imgId, int imgId1, String name){
        this.imgId = imgId;
        this.imgId1 = imgId1;
        this.name = name;
    }

    public CategoryModel(int imgId, List<MarkerModel> itemList, String cateGroyName){
        this.imgId = imgId;
        this.itemList = itemList;
        this.name = cateGroyName;
    }

    public CategoryModel(String imgUrl, String name, String simpleMessage) {
        this.name = name;
        this.simpleMessage = simpleMessage;
        this.imgUrl = imgUrl;
    }

    public CategoryModel(int imgId, int imgId1, String name, String userName, String integral){
        this.imgId = imgId;
        this.imgId1 = imgId1;
        this.name = name;
        this.userName = userName;
        this.integral = integral;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId1() {
        return imgId1;
    }

    public void setImgId1(int imgId1) {
        this.imgId1 = imgId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleMessage() {
        return simpleMessage;
    }

    public void setSimpleMessage(String simpleMessage) {
        this.simpleMessage = simpleMessage;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public List<MarkerModel> getItemList() {
        return itemList;
    }

    public void setItemList(List<MarkerModel> itemList) {
        this.itemList = itemList;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIntegral() {
        return integral;
    }

    public void setIntegral(String integral) {
        this.integral = integral;
    }
}
