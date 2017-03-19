package me.elmira.nytimessearch.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by elmira on 3/15/17.
 */

public class ArticleHeadline {

    @SerializedName("main")
    String main;

    @SerializedName("print_headline")
    String print;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getPrint() {
        return print;
    }

    public void setPrint(String print) {
        this.print = print;
    }
}