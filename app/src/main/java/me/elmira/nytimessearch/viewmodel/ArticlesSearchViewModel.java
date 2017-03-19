package me.elmira.nytimessearch.viewmodel;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;
import java.util.List;

import me.elmira.nytimessearch.data.ArticleSearchFilter;
import me.elmira.nytimessearch.data.IDataModel;
import me.elmira.nytimessearch.data.model.ArticleView;
import me.elmira.nytimessearch.schedulers.ISchedulerProvider;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by elmira on 3/14/17.
 */

public class ArticlesSearchViewModel {

    private static final String LOG_TAG = "ArticlesSearchViewModel";

    @NonNull
    private final ISchedulerProvider mSchedulerProvider;

    @NonNull
    private final IDataModel mDataModel;

    private final BehaviorSubject<List<ArticleView>> mSearchResultsSubject = BehaviorSubject.create();
    private final BehaviorSubject<List<ArticleView>> mSearchPreloadSubject = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    private ArticleSearchFilter filter;

    public ArticlesSearchViewModel(@NonNull ISchedulerProvider mSchedulerProvider, @NonNull IDataModel mDataModel) {
        this.mSchedulerProvider = mSchedulerProvider;
        this.mDataModel = mDataModel;
        this.filter = new ArticleSearchFilter();
    }

    public final Observable<List<ArticleView>> searchResults() {
        return mSearchResultsSubject.asObservable();
    }

    public final Observable<List<ArticleView>> searchPreload() {
        return mSearchPreloadSubject.asObservable();
    }

    public final Observable<Boolean> isLoading() {
        return isLoadingSubject.asObservable();
    }


    public Observable<List<ArticleView>> initSearch(boolean updateQuery) {
        Log.d(LOG_TAG, "initSearch()");
        return searchArticles(null, updateQuery);
    }

    public Observable<List<ArticleView>> searchArticles(String query, boolean updateQuery) {
        Log.d(LOG_TAG, "searchArticles()");
        isLoadingSubject.onNext(true);

        if (updateQuery) filter.setQuery(query);
        filter.setPage(0);

        return mDataModel.searchArticles(filter)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .flatMapIterable(list -> list)
                .map(ArticleView::new)
                .toList()
                .doOnNext(list -> {
                    Log.d(LOG_TAG, "searchArticles: onNext(), list size: " + list.size());
                    mSearchResultsSubject.onNext(list);
                })
                .doOnError(error -> {
                    Log.e(LOG_TAG, error.toString());
                    isLoadingSubject.onNext(false);
                })
                .doOnTerminate(() -> isLoadingSubject.onNext(false));
    }

    public Observable<List<ArticleView>> preloadArticles(int page) {
        Log.d(LOG_TAG, "loadMoreArticles page: " + page);
        filter.setPage(page);

        return mDataModel.searchArticles(filter)
                .subscribeOn(mSchedulerProvider.computation())
                .observeOn(mSchedulerProvider.ui())
                .flatMapIterable(list -> list)
                .map(ArticleView::new)
                .toList()
                .doOnNext(list -> {
                    Log.d(LOG_TAG, "it was additionally loaded: " + (list == null ? 0 : list.size()));
                    mSearchPreloadSubject.onNext(list);
                })
                .doOnError(error -> Log.e(LOG_TAG, error.toString()));
    }

    public ArticleSearchFilter getFilter() {
        return filter;
    }

    public void updateFilter(Date beginDate, String order, List<String> newsDesks) {
        Log.d(LOG_TAG, "update filter: beginDate=" + beginDate + ", order: " + order);
        filter.setNewsDesks(newsDesks == null || newsDesks.size() == 0 ? null : newsDesks);
        filter.setBeginDate(beginDate);
        filter.setSortOrder(order);
    }
}
