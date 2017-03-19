package me.elmira.nytimessearch.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by elmira on 3/14/17.
 */

public class ArticleMedia {

    @SerializedName("width")
    private Integer width;

    @SerializedName("url")
    private String url;

    @SerializedName("height")
    private Integer height;

    @SerializedName("subtype")
    private String subtype;

    @SerializedName("type")
    private String type;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}