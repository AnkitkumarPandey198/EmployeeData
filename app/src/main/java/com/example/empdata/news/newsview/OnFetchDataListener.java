package com.example.empdata.news.newsview;

import com.example.empdata.news.newsmodel.NewsHeadlines;

import java.util.List;

public interface OnFetchDataListener<NewsApiResponse> {

    void onFetchData(List<NewsHeadlines> list, String message);
    void onError(String message);

}
