package com.example.guardiannews;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Article {

    @JsonProperty("id")
    private String id = "defaultid";

    @JsonProperty("type")
    private String type;

    @JsonProperty("sectionId")
    private String sectionId;

    @JsonProperty("webTitle")
    private String webTitle;

    @JsonProperty("pillarName")
    private String pillarName = "defaultPillarName";

    public Article() {

    }

    public Article(String id, String type, String sectionId, String webTitle, String pillarName) {
        this.id = id;
        this.type = type;
        this.sectionId = sectionId;
        this.webTitle = webTitle;
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
                ", pillarName='" + pillarName + '\'' +
                '}';
    }

}
