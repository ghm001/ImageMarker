package com.team.imagemarker.entitys;

/**
 * Created by Lmy on 2017/5/31.
 * email 1434117404@qq.com
 */

public class UserModel {

    //用户id
    private int id;

    //用户登陆用的电话号码
    private String phoneNumber;

    //用户密码
    private String passWord;

    //用户头像地址
    private String userHeadImage;

    //用户昵称
    private String userNickName;

    //用户性别
    private String userSex;

    //用户年龄
    private String userAge;

    //专业领域
    private String userHobby;

    //积分
    private int integral;

    //用户兴趣
    private String userFlag;

    //第三方登录账号
    private String otherLogin;

    //用户完成任务次数
    private int num;

    //推送状态  Y:开  N：关
    private String PushFlag;

    public UserModel() {
    }

    public UserModel(int id, String phoneNumber, String passWord, String userHeadImage, String userNickName, String userSex, String userAge, String userHobby, int integral, String userFlag, String otherLogin, int num, String pushFlag) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.passWord = passWord;
        this.userHeadImage = userHeadImage;
        this.userNickName = userNickName;
        this.userSex = userSex;
        this.userAge = userAge;
        this.userHobby = userHobby;
        this.integral = integral;
        this.userFlag = userFlag;
        this.otherLogin = otherLogin;
        this.num = num;
        PushFlag = pushFlag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getUserSex() {
        return userSex;
    }

    public void setUserSex(String userSex) {
        this.userSex = userSex;
    }

    public String getUserAge() {
        return userAge;
    }

    public void setUserAge(String userAge) {
        this.userAge = userAge;
    }

    public String getUserHobby() {
        return userHobby;
    }

    public void setUserHobby(String userHobby) {
        this.userHobby = userHobby;
    }

    public int getIntegral() {
        return integral;
    }

    public void setIntegral(int integral) {
        this.integral = integral;
    }

    public String getUserFlag() {
        return userFlag;
    }

    public void setUserFlag(String userFlag) {
        this.userFlag = userFlag;
    }

    public String getOtherLogin() {
        return otherLogin;
    }

    public void setOtherLogin(String otherLogin) {
        this.otherLogin = otherLogin;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getPushFlag() {
        return PushFlag;
    }

    public void setPushFlag(String pushFlag) {
        PushFlag = pushFlag;
    }

    @Override
    public String toString() {
        return "UserModel{" +
                "id=" + id +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", passWord='" + passWord + '\'' +
                ", userHeadImage='" + userHeadImage + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userAge='" + userAge + '\'' +
                ", userHobby='" + userHobby + '\'' +
                ", integral=" + integral +
                ", userFlag='" + userFlag + '\'' +
                ", otherLogin='" + otherLogin + '\'' +
                ", num=" + num +
                ", PushFlag='" + PushFlag + '\'' +
                '}';
    }
}
