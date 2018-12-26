package com.team.imagemarker.entitys.task;

import java.util.List;

public class TimeLineModel {

    private String mMessage;
    private String mDate;
    private String mStatus;
    private String mType;
    private List<TaskHistory> histories;


    public TimeLineModel() {
    }

    public TimeLineModel(String mMessage, String mDate, String mStatus, String mType) {
        this.mMessage = mMessage;
        this.mDate = mDate;
        this.mStatus = mStatus;
        this.mType = mType;
    }

    public TimeLineModel(String mDate, List<TaskHistory> histories, String mStatus){
        this.mDate = mDate;
        this.histories = histories;
        this.mStatus = mStatus;
    }

    public String getMessage() {
        return mMessage;
    }

    public void semMessage(String message) {
        this.mMessage = message;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        this.mDate = date;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String mStatus) {
        this.mStatus = mStatus;
    }

    public String getmType() {
        return mType;
    }

    public void setmType(String mType) {
        this.mType = mType;
    }

    public List<TaskHistory> getHistories() {
        return histories;
    }

    public void setHistories(List<TaskHistory> histories) {
        this.histories = histories;
    }
}
