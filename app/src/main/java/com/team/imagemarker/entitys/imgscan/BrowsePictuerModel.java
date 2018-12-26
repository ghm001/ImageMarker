package com.team.imagemarker.entitys.imgscan;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lmy on 2017/8/31.
 * email 1434117404@qq.com
 */

public class BrowsePictuerModel implements Serializable {
    //图片的id
    private int ImageId;
    //图片的url
    private String ImageUrl;
    //图片的标签
    private String[] label;
    //图片的评论表
    private List<Comment> CommentList;

    public BrowsePictuerModel(int imageId, String imageUrl, String[] label, List<Comment> commentList) {
        ImageId = imageId;
        ImageUrl = imageUrl;
        this.label = label;
        CommentList = commentList;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String[] getLabel() {
        return label;
    }

    public void setLabel(String[] label) {
        this.label = label;
    }

    public List<Comment> getCommentList() {
        return CommentList;
    }

    public void setCommentList(List<Comment> commentList) {
        CommentList = commentList;
    }

    @Override
    public String toString() {
        return "BrowsePictuerModel{" +
                "ImageId=" + ImageId +
                ", ImageUrl='" + ImageUrl + '\'' +
                ", label=" + Arrays.toString(label) +
                ", CommentList=" + CommentList +
                '}';
    }
}
