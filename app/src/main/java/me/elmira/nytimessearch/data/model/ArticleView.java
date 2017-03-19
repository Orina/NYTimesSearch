package me.elmira.nytimessearch.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by elmira on 3/16/17.
 */

public class ArticleView implements Parcelable {

    private static final String BASE_URL = "http://www.nytimes.com/";
    private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMM d, yyyy");

    private String title;
    private String webUrl;
    private String multimedia;
    private String description;
    private String date;
    private String newsDesk;

    public ArticleView(Article article) {
        this.title = article.getHeadline().getMain();
        this.webUrl = article.webUrl;
        this.description = article.getLeadParagraph();
        this.date = article.getPublicationDate() == null ? null : simpleDateFormat.format(article.getPublicationDate());
        this.newsDesk = article.getNewsDesk();
        List<ArticleMedia> mediaList = article.getMultimedia();
        if (mediaList != null && mediaList.size() > 0) {
            for (ArticleMedia articleMedia : mediaList) {
                if ("image".equals(articleMedia.getType()) && "wide".equals(articleMedia.getSubtype())) {
                    multimedia = articleMedia.getUrl();
                    break;
                }
            }
        }
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getMultimedia() {
        return multimedia;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public boolean hasNewsDesk() {
        return newsDesk != null && !"none".equalsIgnoreCase(newsDesk);
    }

    public String getFullImageUrl() {
        if (multimedia == null) return null;
        return BASE_URL + multimedia;
    }

    public boolean isMedia() {
        return multimedia != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.webUrl);
        dest.writeString(this.multimedia);
        dest.writeString(this.description);
        dest.writeString(this.date);
        dest.writeString(this.newsDesk);
    }

    protected ArticleView(Parcel in) {
        this.title = in.readString();
        this.webUrl = in.readString();
        this.multimedia = in.readString();
        this.description = in.readString();
        this.date = in.readString();
        this.newsDesk = in.readString();
    }

    public static final Creator<ArticleView> CREATOR = new Creator<ArticleView>() {
        @Override
        public ArticleView createFromParcel(Parcel source) {
            return new ArticleView(source);
        }

        @Override
        public ArticleView[] newArray(int size) {
            return new ArticleView[size];
        }
    };
}
