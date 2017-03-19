package me.elmira.nytimessearch.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

/**
 * Created by elmira on 3/14/17.
 */

public class Article {

    @SerializedName("web_url")
    String webUrl;

    @SerializedName("headline")
    ArticleHeadline headline;

    @SerializedName("lead_paragraph")
    String leadParagraph;

    @SerializedName("multimedia")
    private List<ArticleMedia> multimedia = null;

    @SerializedName("news_desk")
    private String newsDesk;

    @SerializedName("pub_date")
    private Date publicationDate;

    public String getWebUrl() {
        return webUrl;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public List<ArticleMedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<ArticleMedia> multimedia) {
        this.multimedia = multimedia;
    }

    public ArticleHeadline getHeadline() {
        return headline;
    }

    public void setHeadline(ArticleHeadline headline) {
        this.headline = headline;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}