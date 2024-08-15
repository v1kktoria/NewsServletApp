package dao;

import com.example.news.dao.NewsDAO;
import com.example.news.dao.NewsDAOImpl;
import com.example.news.model.Category;
import com.example.news.model.News;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class NewsDAOImplTest {
    private NewsDAO newsDAO;
    private SessionFactory sessionFactory;
    private Session session;
    private Transaction transaction;

    @BeforeEach
    public void setUp(){
        sessionFactory = Mockito.mock(SessionFactory.class);
        session = Mockito.mock(Session.class);
        transaction = Mockito.mock(Transaction.class);
        Mockito.when(sessionFactory.openSession()).thenReturn(session);
        Mockito.when(session.beginTransaction()).thenReturn(transaction);
        newsDAO = new NewsDAOImpl(sessionFactory);
    }

    @Test
    public void testSaveNews()
    {
        News news = new News();
        news.setTitle("Test Title");
        news.setContent("Test Content");
        newsDAO.saveNews(news);
        Mockito.verify(session).persist(news);
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testUpdateNews()
    {
        News news = new News();
        news.setTitle("Test Title");
        news.setContent("Test Content");
        newsDAO.updateNews(news);
        Mockito.verify(session).merge(news);
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testDeleteNews()
    {
        Long id = 1L;
        Mockito.when(session.get(News.class, id)).thenReturn(new News());
        newsDAO.deleteNews(id);
        Mockito.verify(session).remove(Mockito.any(News.class));
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testGetNewsById(){
        Long id = 1L;
        Mockito.when(session.get(News.class, id)).thenReturn(new News());
        News news = newsDAO.getNewsById(id);
        Assertions.assertNotNull(news);
        Mockito.verify(session).get(News.class, id);
    }

    @Test
    public void testGetAllNews()
    {
        org.hibernate.query.Query<News> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("FROM News", News.class)).thenReturn(query);
        Mockito.when(query.list()).thenReturn(Arrays.asList(
                new News(1L, "Test Title 1", "Test Content 1", new Date(), new Category()),
                new News(2L, "Test Title 2", "Test Content 2", new Date(), new Category())
                ));
        List<News> newsList = newsDAO.getAllNews();
        Assertions.assertNotNull(newsList);
        Assertions.assertEquals(2, newsList.size());
        Assertions.assertEquals("Test Title 1", newsList.get(0).getTitle());
        Assertions.assertEquals("Test Title 2", newsList.get(1).getTitle());
    }

    @Test
    public void testGetNewsByCategory()
    {
        Long id = 1L;
        org.hibernate.query.Query<News> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("FROM News WHERE category.id = :id", News.class)).thenReturn(query);
        Mockito.when(query.setParameter("id", id)).thenReturn(query);
        Mockito.when(query.list()).thenReturn(List.of(new News(1L, "Test Title 1", "Test Content 1", new Date(), new Category(1L, "Test Name1", new HashSet<>()))));
        List<News> news = newsDAO.getNewsByCategory(id);
        Assertions.assertEquals(1, news.size());
        Assertions.assertEquals("Test Title 1", news.get(0).getTitle());
        Assertions.assertEquals("Test Content 1", news.get(0).getContent());
    }
}
