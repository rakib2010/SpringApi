package com.example;

import com.example.model.OrderModel;
import java.util.List;
import javax.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {

    @Autowired
    SessionFactory sessionFactory;

    @PostMapping(value = "/saveOrder", consumes = {MediaType.APPLICATION_JSON_VALUE})
    @Transactional
    public ResponseEntity<?> save(@RequestBody OrderModel entity) {
        try {

            Session session = sessionFactory.openSession();
            session.save(entity);            
            return ResponseEntity.ok("Data saved successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Save failed");
        }
    }
    
    
    
    @GetMapping(value = "/getAllOrder")
    public ResponseEntity<?> getAllOrder() {
        try {
            Session session = sessionFactory.openSession();
            List<OrderModel> orderList = session.createQuery("From OrderModel").list();
            session.flush();
            session.close();
            return ResponseEntity.ok(orderList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok("Data fetch failed!");
        }
    }

}
