package com.team.imagemarker.entitys.task;

/**
 * Created by Lmy on 2017/5/26.
 * email 1434117404@qq.com
 */

public class TaskHistory {
    private String title;
    private String operatorType;

    public TaskHistory() {
    }

    public TaskHistory(String title, String operatorType){
        this.title = title;
        this.operatorType = operatorType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOperatorType() {
        return operatorType;
    }

    public void setOperatorType(String operatorType) {
        this.operatorType = operatorType;
    }
}
