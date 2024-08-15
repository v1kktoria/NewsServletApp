package com.example.news.servlet;

import com.example.news.dao.CategoryDAO;
import com.example.news.dao.CategoryDAOImpl;
import com.example.news.model.Category;
import jakarta.persistence.PersistenceException;
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
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/categories")
@AllArgsConstructor
@NoArgsConstructor
public class CategoryServlet extends HttpServlet {
    private CategoryDAO categoryDAO;

    @Override
    public void init()
    {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        categoryDAO = new CategoryDAOImpl(sessionFactory);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("create".equals(action)){
            request.getRequestDispatcher("/WEB-INF/createCategory.jsp").forward(request, response);
        } else if ("delete".equals(action)) {
            Long id = Long.parseLong(request.getParameter("id"));
            try {
                categoryDAO.deleteCategory(id);
            } catch (PersistenceException e)
            {
                String errorMessage = "This category cannot be deleted because it contains news articles. Please remove the articles first.";
                response.sendRedirect("categories?error=" + errorMessage);
                return;
            }
            response.sendRedirect("categories");
        } else {
            List<Category> categories = categoryDAO.getAllCategories();
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("/WEB-INF/categoryList.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String action = request.getParameter("action");
        String name = request.getParameter("name");
        if ("add".equals(action)) {
            Category category = new Category();
            category.setName(name);
            categoryDAO.saveCategory(category);
            response.sendRedirect("categories");
        }
    }
}
