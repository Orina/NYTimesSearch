package me.elmira.nytimessearch.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Created by elmira on 3/14/17.
 */

public interface ISchedulerProvider {
    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler ui();
}
