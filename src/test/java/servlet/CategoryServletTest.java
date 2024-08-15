package servlet;

import com.example.news.dao.CategoryDAO;
import com.example.news.model.Category;
import com.example.news.servlet.CategoryServlet;
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
import java.util.Arrays;
import java.util.List;

public class CategoryServletTest {
    private CategoryServlet categoryServlet;
    private CategoryDAO categoryDAO;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    public void setUp(){
        categoryDAO = Mockito.mock(CategoryDAO.class);
        categoryServlet = new CategoryServlet(categoryDAO);
        request = Mockito.mock(HttpServletRequest.class);
        response = Mockito.mock(HttpServletResponse.class);
        requestDispatcher = Mockito.mock(RequestDispatcher.class);
    }

    @Test
    public void testDoGetList() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ServletException, IOException {
        Method method = CategoryServlet.class.getDeclaredMethod("doGet", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);
        List<Category> categories = Arrays.asList(new Category(), new Category());
        Mockito.when(categoryDAO.getAllCategories()).thenReturn(categories);
        Mockito.when(request.getRequestDispatcher("/WEB-INF/categoryList.jsp")).thenReturn(requestDispatcher);
        method.invoke(categoryServlet, request, response);
        Mockito.verify(request).setAttribute("categories", categories);
        Mockito.verify(request.getRequestDispatcher("/WEB-INF/categoryList.jsp")).forward(request, response);
    }

    @Test
    public void testDoPostAdd() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, IOException, ParseException {
        Method method = CategoryServlet.class.getDeclaredMethod("doPost", HttpServletRequest.class, HttpServletResponse.class);
        method.setAccessible(true);
        Mockito.when(request.getParameter("action")).thenReturn("add");
        Mockito.when(request.getParameter("name")).thenReturn("Test Name");
        method.invoke(categoryServlet, request, response);
        Category category = new Category();
        category.setName("Test Name");
        ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        Mockito.verify(categoryDAO).saveCategory(captor.capture());
        Category capturedCategory = captor.getValue();
        Assertions.assertEquals(category.getName(), capturedCategory.getName());
        Mockito.verify(response).sendRedirect("categories");
    }
}
