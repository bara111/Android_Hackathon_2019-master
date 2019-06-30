package com.example.android_hackathon_2019;

public class ImageRecord {

    private  String type;
    private String time;
    private  String imageUrl;
    private  String valid;
    public ImageRecord() {

    }

    public ImageRecord(String type, String date, String url, String valid) {
        this.type = type;
        this.time = date;
        this.imageUrl = url;
        this.valid=valid;

    }

    public void setValid(String valid) {
        this.valid = valid;
    }

    public String getValid() {
        return valid;
    }

    public String getType() {
        return type;
    }


    public String getTime() {
        return time;
    }

    public  String getImageUrl(){return this.imageUrl;}

    public void setType(String type) {
        this.type = type;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}


