package com.example.news.dao;

import com.example.news.model.News;

import java.util.List;

public interface NewsDAO {
    void saveNews(News news);
    void updateNews(News news);
    void deleteNews(Long id);
    News getNewsById(Long id);
    List<News> getAllNews();
    List<News> getNewsByCategory(Long id);
}
