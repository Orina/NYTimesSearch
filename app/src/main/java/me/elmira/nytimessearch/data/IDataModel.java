package me.elmira.nytimessearch.data;

import android.support.annotation.NonNull;

import java.util.List;

import me.elmira.nytimessearch.data.model.Article;
import rx.Observable;

/**
 * Created by elmira on 3/14/17.
 */

public interface IDataModel {

    @NonNull
    Observable<List<Article>> searchArticles(ArticleSearchFilter filter);

}