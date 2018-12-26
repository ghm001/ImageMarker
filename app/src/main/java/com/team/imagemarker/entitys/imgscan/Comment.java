package com.team.imagemarker.entitys.imgscan;

import java.io.Serializable;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public class Comment implements Serializable{
    //评论的id
    private int id;
    //用户昵称
    private String UserName;
    //用户评论内容
    private String CommentTitle;
    //用户头像地址
    private String userHeadImage;
    //图片的id
    private int ImageID;
    //评论时间
    private String SayTime;

    public Comment(int id, String userName, String commentTitle, String userHeadImage, int imageID, String SayTime) {
        this.id = id;
        this.UserName = userName;
        this.CommentTitle = commentTitle;
        this.userHeadImage = userHeadImage;
        this.ImageID = imageID;
        this.SayTime = SayTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getCommentTitle() {
        return CommentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        CommentTitle = commentTitle;
    }

    public String getUserHeadImage() {
        return userHeadImage;
    }

    public void setUserHeadImage(String userHeadImage) {
        this.userHeadImage = userHeadImage;
    }

    public int getImageID() {
        return ImageID;
    }

    public void setImageID(int imageID) {
        ImageID = imageID;
    }

    public String getSayTime() {
        return SayTime;
    }

    public void setSayTime(String sayTime) {
        SayTime = sayTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", UserName='" + UserName + '\'' +
                ", CommentTitle='" + CommentTitle + '\'' +
                ", userHeadImage='" + userHeadImage + '\'' +
                ", ImageID=" + ImageID +
                ", SayTime='" + SayTime + '\'' +
                '}';
    }
}
