package servlet;

import com.example.news.dao.CategoryDAO;
import com.example.news.dao.NewsDAO;
import com.example.news.model.Category;
import com.example.news.model.News;
import com.example.news.servlet.NewsServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

public class NewsServletTest{
    private NewsServlet newsServlet;
    private NewsDAO newsDAO;
    private CategoryDAO categoryDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp(){
        newsDAO = Mockito.mock(NewsDAO.class);
        categoryDAO = Mockito.mock(CategoryDAO.class);
        newsServlet = new NewsServlet(newsDAO, categoryDAO);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
    }

    @Test
    public void testDoGetList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ServletException, IOException {
        Method method = NewsServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);
        List<News> newsList = Arrays.asList(new News(), new News());
        Mockito.when(newsDAO.getAllNews()).thenReturn(newsList);
        Mockito.when(request.getRequestDispatcher("/WEB-INF/newsList.jsp")).thenReturn(requestDispatcher);
        method.invoke(newsServlet, request, response);
        Mockito.verify(request).setAttribute("newsList", newsList);
        Mockito.verify(request.getRequestDispatcher("/WEB-INF/newsList.jsp")).forward(request, response);
    }

    @Test
    public void testDoPostAdd() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ParseException {
        Method method = NewsServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);
        Mockito.when(request.getParameter("action")).thenReturn("add");
        Mockito.when(request.getParameter("title")).thenReturn("Test Title");
        Mockito.when(request.getParameter("content")).thenReturn("Test Content");
        Mockito.when(request.getParameter("publishedDate")).thenReturn("2024-08-03");
        Mockito.when(request.getParameter("id")).thenReturn("1");
        method.invoke(newsServlet, request, response);
        News news = new News(0L, "Test Title", "Test Content", new SimpleDateFormat("yyyy-MM-dd").parse("2024-08-03"), new Category());
        ArgumentCaptor<News> newsCaptor = ArgumentCaptor.forClass(News.class);
        Mockito.verify(newsDAO).saveNews(newsCaptor.capture());
        News capturedNews = newsCaptor.getValue();
        Assertions.assertEquals(news.getTitle(), capturedNews.getTitle());
        Assertions.assertEquals(news.getContent(), capturedNews.getContent());
        Assertions.assertEquals(news.getPublishedDate(), capturedNews.getPublishedDate());
        Mockito.verify(response).sendRedirect("news");
    }
}
