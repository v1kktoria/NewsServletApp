package com.example.news.servlet;

import com.example.news.dao.CategoryDAO;
import com.example.news.dao.CategoryDAOImpl;
import com.example.news.dao.NewsDAO;
import com.example.news.dao.NewsDAOImpl;
import com.example.news.model.Category;
import com.example.news.model.News;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@WebServlet(name = "NewsServlet", value = "/news")
@AllArgsConstructor
@NoArgsConstructor
public class NewsServlet extends HttpServlet {
    private NewsDAO newsDAO;
    private CategoryDAO categoryDAO;

    @Override
    public void init() throws ServletException{
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        newsDAO = new NewsDAOImpl(sessionFactory);
        categoryDAO = new CategoryDAOImpl(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            newsDAO.deleteNews(id);
            response.sendRedirect("news");
            return;
        } else if ("edit".equals(action)){
            Long id = Long.parseLong(request.getParameter("id"));
            News news = newsDAO.getNewsById(id);
            request.setAttribute("news", news);
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/editNews.jsp").forward(request, response);
            return;
        } else if ("create".equals(action)) {
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/createNews.jsp").forward(request, response);
            return;
        } else if ("filter".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            List<News> newsList = newsDAO.getNewsByCategory(id);
            request.setAttribute("newsList", newsList);
            request.setAttribute("categories", categoryDAO.getAllCategories());
            request.getRequestDispatcher("/WEB-INF/newsList.jsp").forward(request, response);
            return;
        }
        List<News> newsList = newsDAO.getAllNews();
        List<Category> categories = categoryDAO.getAllCategories();
        request.setAttribute("newsList", newsList);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/WEB-INF/newsList.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        String title = request.getParameter("title");
        String content = request.getParameter("content");
        String dateStr = request.getParameter("publishedDate");
        Long categoryId = Long.parseLong(request.getParameter("categoryId"));
        Date publishedDate = null;
        try {
            publishedDate = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);
        } catch (ParseException e){
            e.printStackTrace();
        }

        if ("add".equals(action)){
            News news = new News();
            news.setTitle(title);
            news.setContent(content);
            news.setPublishedDate(publishedDate);
            news.setCategory(categoryDAO.getCategoryById(categoryId));
            newsDAO.saveNews(news);
            response.sendRedirect("news");
        } else if ("update".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            News news = newsDAO.getNewsById(id);
            news.setTitle(title);
            news.setContent(content);
            news.setPublishedDate(publishedDate);
            news.setCategory(categoryDAO.getCategoryById(categoryId));
            newsDAO.updateNews(news);
            response.sendRedirect("news");
    }}

}
