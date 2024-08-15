package com.example.news.dao;

import com.example.news.model.Category;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class CategoryDAOImpl implements CategoryDAO{
    private SessionFactory sessionFactory;
    @Override
    public void saveCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.persist(category);
            transaction.commit();
        }
    }

    @Override
    public void updateCategory(Category category) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.merge(category);
            transaction.commit();
        }
    }

    @Override
    public void deleteCategory(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            Category category = session.get(Category.class, id);
            if (category != null) {
                session.remove(category);
            }
            transaction.commit();
        }
    }

    @Override
    public Category getCategoryById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Category.class, id);
        }
    }

    @Override
    public List<Category> getAllCategories() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM Category", Category.class).list();
        }
    }
}
