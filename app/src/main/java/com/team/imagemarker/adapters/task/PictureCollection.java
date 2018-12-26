package com.team.imagemarker.adapters.task;

public class PictureCollection {
    private int id;
    private int userId;
    private int ImageId;
    private String saveTime;
    private String ImageUrl;

    public PictureCollection(int id, int userId, int imageId, String saveTime, String imageUrl) {
        this.id = id;
        this.userId = userId;
        ImageId = imageId;
        this.saveTime = saveTime;
        ImageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getImageId() {
        return ImageId;
    }

    public void setImageId(int imageId) {
        ImageId = imageId;
    }

    public String getSaveTime() {
        return saveTime;
    }

    public void setSaveTime(String saveTime) {
        this.saveTime = saveTime;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", userId=" + userId +
                ", ImageId=" + ImageId +
                ", saveTime='" + saveTime + '\'' +
                ", ImageUrl='" + ImageUrl + '\'' +
                '}';
    }
}
