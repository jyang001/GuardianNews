package com.example.guardiannews.models;

import android.graphics.Bitmap;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Represents a news article
 */
public class Article {

    /**
     * the media type, such as article, poll, video, etc
     */
    @JsonProperty("type")
    private String articleType;

    /**
     * category of media
     */
    @JsonProperty("sectionName")
    private String sectionName;

    @JsonProperty("webTitle")
    private String articleTitle;

    @JsonProperty("webUrl")
    private String webUrl;

    private Bitmap image;

    public Article() {

    }

    public Article(String articleType, String sectionName, String articleTitle, String webUrl, Bitmap image) {
        this.articleType = articleType;
        this.sectionName = sectionName;
        this.articleTitle = articleTitle;
        this.webUrl = webUrl;
        this.image = image;
    }

    public String getArticleType() {
        return articleType;
    }

    public void setArticleType(String articleType) {
        this.articleType = articleType;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Article{" +
                "articleType='" + articleType + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", articleTitle='" + articleTitle + '\'' +
                ", webUrl='" + webUrl + '\'' +
                ", image=" + image +
                '}';
    }

}