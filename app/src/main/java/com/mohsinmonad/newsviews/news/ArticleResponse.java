package com.mohsinmonad.newsviews.news;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ArticleResponse {

    @SerializedName("status")
    private String status;
    @SerializedName("sortBy")
    private String sortBy;
    @SerializedName("source")
    private String source;
    @SerializedName("articles")
    private List<Article> articles;

    public ArticleResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSortBy() {
        return sortBy;
    }

    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
