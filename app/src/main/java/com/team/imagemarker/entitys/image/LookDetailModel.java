package com.team.imagemarker.entitys.image;

import java.util.List;

/**
 * 详细图片浏览
 * Created by Lmy on 2017/5/12.
 * email 1434117404@qq.com
 */

public class LookDetailModel {
    private int imgId;//使用本地用户测试
    private String imgUrl;//图片地址
    private String[] imgTag;//图片对应的标签
    private List<CommentInfoModel> commentList;//评论列表

    public LookDetailModel(String[] imgTag, List<CommentInfoModel> commentList, String imgUrl) {
        this.imgTag = imgTag;
        this.commentList = commentList;
        this.imgUrl = imgUrl;
    }

    public LookDetailModel(int imgId, String[] imgTag, List<CommentInfoModel> commentList) {
        this.imgId = imgId;
        this.imgTag = imgTag;
        this.commentList = commentList;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String[] getImgTag() {
        return imgTag;
    }

    public void setImgTag(String[] imgTag) {
        this.imgTag = imgTag;
    }

    public List<CommentInfoModel> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentInfoModel> commentList) {
        this.commentList = commentList;
    }
}
