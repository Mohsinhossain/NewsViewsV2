package com.mohsinmonad.newsviews.network;



import com.mohsinmonad.newsviews.home.GetSources;
import com.mohsinmonad.newsviews.news.GetArticles;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsClient {
    @GET("v1/sources")
    Call<GetSources> getSources(@Query("language") String language);

    @GET("v1/articles")
    Call<GetArticles> getArticles(@Query("source") String source, @Query("apiKey") String apiKey);
}
