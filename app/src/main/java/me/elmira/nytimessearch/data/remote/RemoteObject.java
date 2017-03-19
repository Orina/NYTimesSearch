package me.elmira.nytimessearch.data.remote;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import me.elmira.nytimessearch.data.model.Article;

/**
 * Created by elmira on 3/15/17.
 */

public class RemoteObject {

    @SerializedName("response")
    Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public static class Response{
        @SerializedName("docs")
        public List<Article> articles;

        public List<Article> getArticles() {
            return articles;
        }

        public void setArticles(List<Article> articles) {
            this.articles = articles;
        }
    }
}
