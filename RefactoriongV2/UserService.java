package com.groom.manvsclass.controller;

import java.io.IOException;
import java.io.File;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.controller.AchievementService;
import com.example.service.adminService;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<ClassUT> ricercaClasse(String text) {
        return userRepository.ricercaClasse(text);
    }

    public String test() {
        return userRepository.test();
    }

    public ResponseEntity<?> downloadClasse(String name) throws Exception {
        return userRepository.downloadClasse(name);
    }

    public ResponseEntity<String> modificaClasse(String name, ClassUT newContent, String jwt, HttpServletRequest request) {
        return userRepository.modificaClasse(name, newContent, jwt);
    }

    public ResponseEntity<?> registraAdmin(Admin admin1, String jwt) {
        return userRepository.registraAdmin(admin1, jwt);
    }

    public ResponseEntity<Admin> getAdminByUsername(String username, String jwt) {
        return userRepository.getAdminByUsername(username, jwt);
    }

    public ModelAndView showPlayer(HttpServletRequest request, String jwt) {
        return userRepository.showPlayer(request, jwt);
    }

    public ModelAndView showClass(HttpServletRequest request, String jwt) {
        return userRepository.showClass(request, jwt);
    }
}