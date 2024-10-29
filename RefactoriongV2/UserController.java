package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.http.ResponseEntity;
import javax.servlet.http.HttpServletRequest;
import com.example.service.UserService;
import com.example.model.ClassUT;
import com.example.model.Admin;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/home/{text}")
    @ResponseBody
    public List<ClassUT> ricercaClasse(@PathVariable String text) {
        return userService.ricercaClasse(text);
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return userService.test();
    }

    @GetMapping("/downloadFile/{name}")
    @ResponseBody
    public ResponseEntity<?> downloadClasse(@PathVariable("name") String name) throws Exception {
        return userService.downloadClasse(name);
    }
}