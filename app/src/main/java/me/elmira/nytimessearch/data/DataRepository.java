package me.elmira.nytimessearch.data;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import me.elmira.nytimessearch.data.model.Article;
import me.elmira.nytimessearch.data.remote.NYTimesSearchService;
import rx.Observable;

/**
 * Created by elmira on 3/14/17.
 */

public class DataRepository implements IDataModel {

    private static final String LOG_TAG = "DataRepository";

    private static DataRepository instance = null;

    private NYTimesSearchService mRemoteService;

    private static final String API_KEY = "1ad66c23b17040b19151a8dae2e826a4";


    private DataRepository(NYTimesSearchService nyTimesSearchService) {
        this.mRemoteService = nyTimesSearchService;
    }

    public static DataRepository getInstance(NYTimesSearchService nyTimesSearchService) {
        if (instance == null) {
            instance = new DataRepository(nyTimesSearchService);
        }
        return instance;
    }

    @NonNull
    @Override
    public Observable<List<Article>> searchArticles(ArticleSearchFilter filter) {
        Log.d(LOG_TAG, "Try to search articles...filter:" + filter.toString());

        return mRemoteService.searchArticles(API_KEY,
                filter.getFormattedSortOrder(),
                filter.getQuery(),
                filter.getFormattedBeginDate(),
                filter.getFormattedNewsDesks(),
                filter.getFormattedPage())
                .map(remoteObject -> remoteObject.getResponse().getArticles())
                .doOnError(error -> Log.e(LOG_TAG, error.toString()));
    }
}
