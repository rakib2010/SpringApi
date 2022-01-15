/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Instructor
 */

@RestController
public class HomeController {
    
    @GetMapping("/")
    public String helloWorld(){
        return "Welcome our api";
    }
    
}
