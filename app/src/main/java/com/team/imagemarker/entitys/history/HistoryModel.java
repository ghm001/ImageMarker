package com.team.imagemarker.entitys.history;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class HistoryModel {
    private int userId;//用户ID
    private int rdcordId;//历史记录ID
    private String firstImg;//用户头像
    private String recordName;//记录名称
    private String operateType;//操作类型
    private String recordTime;//记录时间
    private int recordType;//记录类型(保存，已提交)

    public HistoryModel() {
    }

    public HistoryModel(int userId, String firstImg, String recordName, String operateType, String recordTime, int recordType) {
        this.userId = userId;
        this.firstImg = firstImg;
        this.recordName = recordName;
        this.operateType = operateType;
        this.recordTime = recordTime;
        this.recordType = recordType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRdcordId() {
        return rdcordId;
    }

    public void setRdcordId(int rdcordId) {
        this.rdcordId = rdcordId;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getOperateType() {
        return operateType;
    }

    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public int getRecordType() {
        return recordType;
    }

    public void setRecordType(int recordType) {
        this.recordType = recordType;
    }
}
