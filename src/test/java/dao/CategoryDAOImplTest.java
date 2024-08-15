package dao;

import com.example.news.dao.CategoryDAO;
import com.example.news.dao.CategoryDAOImpl;
import com.example.news.model.Category;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class CategoryDAOImplTest {
    private CategoryDAO categoryDAO;
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
        categoryDAO = new CategoryDAOImpl(sessionFactory);
    }

    @Test
    public void testSaveCategory()
    {
        Category category = new Category();
        category.setName("Test Name");
        category.setNewsSet(new HashSet<>());
        categoryDAO.saveCategory(category);
        Mockito.verify(session).persist(category);
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testUpdateCategory()
    {
        Category category = new Category();
        category.setName("Test Name");
        category.setNewsSet(new HashSet<>());
        categoryDAO.updateCategory(category);
        Mockito.verify(session).merge(category);
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testDeleteCategory()
    {
        Long id = 1L;
        Mockito.when(session.get(Category.class, id)).thenReturn(new Category());
        categoryDAO.deleteCategory(id);
        Mockito.verify(session).remove(Mockito.any(Category.class));
        Mockito.verify(transaction).commit();
    }

    @Test
    public void testGetCategoryById()
    {
        Long id = 1L;
        Mockito.when(session.get(Category.class, id)).thenReturn(new Category());
        Category category = categoryDAO.getCategoryById(id);
        Assertions.assertNotNull(category);
        Mockito.verify(session).get(Category.class, id);
    }

    @Test
    public void testGetAllCategories()
    {
        org.hibernate.query.Query<Category> query = Mockito.mock(Query.class);
        Mockito.when(session.createQuery("FROM Category", Category.class)).thenReturn(query);
        Mockito.when(query.list()).thenReturn(Arrays.asList(
                new Category(1L, "Test Name1", new HashSet<>()),
                new Category(2L, "Test Name2", new HashSet<>())
        ));
        List<Category> categories = categoryDAO.getAllCategories();
        Assertions.assertNotNull(categories);
        Assertions.assertEquals(2, categories.size());
        Assertions.assertEquals("Test Name1", categories.get(0).getName());
        Assertions.assertEquals("Test Name2", categories.get(1).getName());
    }
}
