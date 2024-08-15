package com.example.news.dao;

import com.example.news.model.News;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@AllArgsConstructor
public class NewsDAOImpl implements NewsDAO{
    private SessionFactory sessionFactory;
    @Override
    public void saveNews(News news) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(news);
            transaction.commit();
        }
    }

    @Override
    public void updateNews(News news) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(news);
            transaction.commit();
        }
    }

    @Override
    public void deleteNews(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            News news = session.get(News.class, id);
            if (news != null) {
                session.remove(news);
            }
            transaction.commit();
        }
    }

    @Override
    public News getNewsById(Long id) {
        try(Session session = sessionFactory.openSession()){
            return session.get(News.class, id);
        }
    }

    @Override
    public List<News> getAllNews() {
        try(Session session = sessionFactory.openSession()){
            return session.createQuery("FROM News", News.class).list();
        }
    }

    @Override
    public List<News> getNewsByCategory(Long id)
    {
        try(Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM News WHERE category.id = :id", News.class)
                    .setParameter("id", id)
                    .list();
        }
    }
}
