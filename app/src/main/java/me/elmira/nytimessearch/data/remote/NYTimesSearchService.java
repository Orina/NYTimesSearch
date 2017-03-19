package me.elmira.nytimessearch.data.remote;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by elmira on 3/14/17.
 */

public interface NYTimesSearchService {

    String ENDPOINT = "https://api.nytimes.com/svc/search/v2/";

    @GET("articlesearch.json")
    Observable<RemoteObject> searchArticles(@Query("api-key") String apiKey,
                                            @Query("sort") String sort,
                                            @Query("q") String searchQuery,
                                            @Query("begin_date") String beginDate,
                                            @Query("fq") String newsDesk,
                                            @Query("page") Integer page);
}
