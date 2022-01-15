
package com.example;

import com.example.model.Product;
import com.example.storage.service.FileStorageService;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Instructor
 */
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    @Autowired
    SessionFactory sessionFactory;
    
     @Autowired
    private FileStorageService fileStorageService;

    @PostMapping(value = "/product/save", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> save(@RequestBody Product entity) {
        try {
            
            Session session = sessionFactory.openSession();
            session.save(entity);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }
    
    
    
    
    @PostMapping(value = "/product/save2")
    @Transactional
    public ResponseEntity<?> save2(@ModelAttribute Product entity, @RequestParam("file") MultipartFile file) {
        try {
            String fileName = fileStorageService.storeFile(file);
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(fileName)
                .toUriString();
            
            Session session = sessionFactory.openSession();
            entity.setImageName(fileName);
            entity.setImageUri(fileDownloadUri);
            
            session.save(entity);
            session.flush();
            session.close();
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }
    
    @GetMapping(value = "/product/getAll")
    public ResponseEntity<?> getAll() {
        try {
            Session session = sessionFactory.openSession();
            List<Product> entityList = session.createQuery("From Product").list();
            session.flush();
            session.close();
            return ResponseEntity.ok(entityList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }
    
    @GetMapping(value = "/product/getOne/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> getOne(@PathVariable(value = "id") long id) {
        try {
            Session session = sessionFactory.openSession();
            Product entity = session.get(Product.class, id);
            session.flush();
            session.close();
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }
    
    
    @GetMapping(value = "/product/ByCategoryId/{id}")
    @Transactional
    public ResponseEntity<?> getProductsByCategoryId(@PathVariable(value = "id") long id) {
        try {
            Session session = sessionFactory.openSession();
            Query q = session.createQuery("from Product where categoryId=:categoryId");
            q.setParameter("categoryId", id);
            List<Product> pList = q.list();
            session.flush();
            session.close();
            return ResponseEntity.ok(pList);
            
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
        
    }
    
    
    
    
    
    @PostMapping(value = "/product/update", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> update(@RequestBody Product entity) {
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
    
    @GetMapping(value = "/product/delete/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> delete(@PathVariable(value = "id") long id) {
        try {
            Session session = sessionFactory.openSession();
            Product entity = session.get(Product.class, id);
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
