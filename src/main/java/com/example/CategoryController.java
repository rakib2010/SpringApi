package com.example;

import com.example.model.CategoryModel;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)

public class CategoryController {

    @Autowired
    SessionFactory sessionFactory;

    @PostMapping(value = "/saveCategory", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> saveCategory(@RequestBody CategoryModel category) {
        try {
            Session session = sessionFactory.openSession();
            session.save(category);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }

    @GetMapping(value = "/getAllCategory")
    public ResponseEntity<?> getAllCategory() {
        try {
            Session session = sessionFactory.openSession();
            List<CategoryModel> categoryList = session.createQuery("From CategoryModel").list();
            session.flush();
            session.close();
            return ResponseEntity.ok(categoryList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }

    @PostMapping(value = "/categoryUpdate", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> categoryUpdate(@RequestBody CategoryModel entity) {
        try {
            Session session = sessionFactory.openSession();
            session.saveOrUpdate(entity);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data updated successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }

    @GetMapping(value = "/deleteCategory/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> deleteCategory(@PathVariable(value = "id") long id) {
        try {
            Session session = sessionFactory.openSession();
            CategoryModel entity = session.get(CategoryModel.class, id);
            session.delete(entity);
            session.flush();
            session.close();
            return ResponseEntity.ok("Delete successful");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }

}
