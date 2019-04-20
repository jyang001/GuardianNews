package com.example.guardiannews;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Article {

    @JsonProperty("id")
    private String id = "defaultid";

    @JsonProperty("type")
    private String type;

    @JsonProperty("sectionId")
    private String sectionId;

    @JsonProperty("webTitle")
    private String webTitle;

    @JsonProperty("fields")
    private List imageUrl;

    private Bitmap image;

    @JsonProperty("pillarName")
    private String pillarName = "defaultPillarName";

    public Article() {

    }

    public Article(String id, String type, String sectionId, String webTitle, List imageUrl, String pillarName) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.webTitle = webTitle;
        this.imageUrl = imageUrl;
        this.pillarName = pillarName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getWebTitle() {
        return webTitle;
    }

    public void setWebTitle(String webTitle) {
        this.webTitle = webTitle;
    }

    public List getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(List imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getPillarName() {
        return pillarName;
    }

    public void setPillarName(String pillarName) {
        this.pillarName = pillarName;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", webTitle='" + webTitle + '\'' +
                ", imageUrl=" + imageUrl +
                ", pillarName='" + pillarName + '\'' +
                '}';
    }
}
