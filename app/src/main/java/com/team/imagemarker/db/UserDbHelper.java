package com.team.imagemarker.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.team.imagemarker.entitys.UserModel;

/**
 * Created by Lmy on 2017/6/11.
 * email 1434117404@qq.com
 */

public class UserDbHelper {
    private static UserDbHelper instance;
    private SharedPreferences sharedPreferences;

    private UserDbHelper(Context context){
        sharedPreferences = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
    }

    public static void setInstance(Context context){
        instance = new UserDbHelper(context);
    }

    public static UserDbHelper getInstance(){
        return instance;
    }

    /**
     * 保存用户的信息到当前的数据库
     */
    public void saveUserLoginInfo(UserModel userModel){
        saveIntegerConfig("userId", userModel.getId());
        saveStringConfig("userName", userModel.getUserNickName());
        saveStringConfig("userPhone", userModel.getPhoneNumber());
        saveStringConfig("userPassword", userModel.getPassWord());
        saveStringConfig("userHeadImg", userModel.getUserHeadImage());
        saveStringConfig("userSex", userModel.getUserSex());
        saveStringConfig("userAge", userModel.getUserAge());
        saveStringConfig("userHobby", userModel.getUserHobby());
        saveIntegerConfig("userIntegral", userModel.getIntegral());
        saveIntegerConfig("userTaskNum", userModel.getNum());
        saveStringConfig("userFlag", userModel.getUserFlag());
        saveStringConfig("PushFlag", userModel.getPushFlag());
        saveStringConfig("OtherLogin", userModel.getOtherLogin());
    }

    /**
     * 得到用户信息
     */
    public UserModel getUserInfo(){
        UserModel userModel = new UserModel();
        userModel.setId(getInegerConfig("userId"));
        userModel.setUserNickName(getStringConfig("userName"));
        userModel.setPhoneNumber(getStringConfig("userPhone"));
        userModel.setPassWord(getStringConfig("userPassword"));
        userModel.setUserHeadImage(getStringConfig("userHeadImg"));
        userModel.setUserSex(getStringConfig("userSex"));
        userModel.setUserAge(getStringConfig("userAge"));
        userModel.setUserHobby(getStringConfig("userHobby"));
        userModel.setIntegral(getInegerConfig("userIntegral"));
        userModel.setNum(getInegerConfig("userTaskNum"));
        userModel.setUserFlag(getStringConfig("userFlag"));
        userModel.setPushFlag(getStringConfig("PushFlag"));
        userModel.setOtherLogin(getStringConfig("OtherLogin"));
        return userModel;
    }

    /**
     * 保存用户的登录状态
     */
    public void saveLoginState(boolean loginState){
        saveBooleanConfig("loginState", loginState);
    }

    /**
     * 得到用户的登录状态
     */
    public boolean getLoginState(){
        return getBooleanConfig("loginState");
    }


    /**
     * 保存用户的头像
     */
    public void saveUserHeadImg(String value){
        sharedPreferences.edit().putString("userHeadImg", value);
    }

    /**
     * 得到用户头像
     */
    public String getUserHeadImg(){
        return sharedPreferences.getString("userHeadImg", "");
    }

    /**
     * 保存String类型数据
     */
    public void saveStringConfig(String key, String value){
        sharedPreferences.edit().putString(key, value).commit();
    }

    /**
     * 保存boolean类型数据
     */
    public void saveBooleanConfig(String key, boolean value){
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    /**
     * 保存Integer类型数据
     */
    public void saveIntegerConfig(String key, int value){
        sharedPreferences.edit().putInt(key, value).commit();
    }

    /**
     * 得到String类型的数据
     */
    public String getStringConfig(String key){
        return sharedPreferences.getString(key, "");
    }

    /**
     * 得到Boolean类型的数据
     */
    public boolean getBooleanConfig(String key){
        return sharedPreferences.getBoolean(key, true);
    }

    /**
     * 得到integer类型的数据
     */
    public int getInegerConfig(String key){
        return sharedPreferences.getInt(key, -1);
    }
}
