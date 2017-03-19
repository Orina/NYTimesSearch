package me.elmira.nytimessearch;

import android.app.Application;

import me.elmira.nytimessearch.viewmodel.ArticlesSearchViewModel;
import me.elmira.nytimessearch.data.DataRepository;
import me.elmira.nytimessearch.data.IDataModel;
import me.elmira.nytimessearch.data.remote.RetrofitNYTimesSearchService;
import me.elmira.nytimessearch.schedulers.ISchedulerProvider;
import me.elmira.nytimessearch.schedulers.SchedulerProvider;

/**
 * Created by elmira on 3/14/17.
 */

public class NyTimesSearchApplication extends Application {

    private IDataModel mDataModel;

    public NyTimesSearchApplication() {
        mDataModel = DataRepository.getInstance(RetrofitNYTimesSearchService.newNYTSearchService());
    }

    public IDataModel getDataModel() {
        return mDataModel;
    }

    public ISchedulerProvider getScheduler() {
        return SchedulerProvider.getInstance();
    }

    public ArticlesSearchViewModel getArticlesViewModel() {
        return new ArticlesSearchViewModel(getScheduler(), getDataModel());
    }
}
