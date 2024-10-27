package com.example.controller;

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

import com.example.service.AuthenticationService;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/home_adm")
    public ModelAndView showHomeAdmin(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showHomeAdmin(request, jwt);
    }

    @GetMapping("/registraAdmin")
    public String showRegistraAdmin() {
        return authenticationService.showRegistraAdmin();
    }

    @GetMapping("/modificaClasse")
    public ModelAndView showModificaClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showModificaClasse(request, jwt);
    }

    @GetMapping("/uploadClasse")
    public ModelAndView showUploadClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showUploadClasse(request, jwt);
    }

    @GetMapping("/uploadClasseAndTest")
    public ModelAndView showUploadClasseAndTest(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showUploadClasseAndTest(request, jwt);
    }

    @GetMapping("/reportClasse")
    public ModelAndView showReportClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showReportClasse(request, jwt);
    }

    @GetMapping("/Reports")
    public ModelAndView showReports(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showReports(request, jwt);
    }
    
    

    // parte report

    @Autowired
    private Utils utils;

    @GetMapping("/interaction")
    @ResponseBody
    public List<interaction> elencaInt() {
        return utils.elencaInt();
    }

    @GetMapping("/findreport")
    @ResponseBody
    public List<interaction> elencaReport() {
        return utils.elencaReport();
    }

    @GetMapping("/getLikes/{name}")
    @ResponseBody
    public long likes(@PathVariable String name) {
        return utils.likes(name);
    }

    @PostMapping("/newinteraction")
    @ResponseBody
    public interaction uploadInteraction(@RequestBody interaction interazione) {
        return utils.uploadInteraction(interazione);
    }

    @PostMapping("/newlike/{name}")
    @ResponseBody
    public String newLike(@PathVariable String name) {
        return utils.newLike(name);
    }

    @PostMapping("/newReport/{name}")
    @ResponseBody
    public String newReport(@PathVariable String name, @RequestBody String commento) {
        return utils.newReport(name, commento);
    }

    @PostMapping("/deleteint/{id_i}")
    @ResponseBody
    public interaction eliminaInteraction(@PathVariable int id_i) {
        return utils.eliminaInteraction(id_i);
    }

    // funzioni admin

     @GetMapping("/orderbydate")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> ordinaClassi(@CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.ordinaClassi(jwt);
    }

    @GetMapping("/orderbyname")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> ordinaClassiNomi(@CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.ordinaClassiNomi(jwt);
    }

    @GetMapping("/Cfilterby/{category}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> filtraClassi(@PathVariable String category, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.filtraClassi(category, jwt);
    }

    @GetMapping("/Cfilterby/{text}/{category}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> filtraClassi(@PathVariable String text, @PathVariable String category, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.filtraClassi(text, category, jwt);
    }

    @GetMapping("/Dfilterby/{difficulty}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> elencaClassiD(@PathVariable String difficulty, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.elencaClassiD(difficulty, jwt);
    }

    @GetMapping("/Dfilterby/{text}/{difficulty}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> elencaClassiD(@PathVariable String text, @PathVariable String difficulty, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.elencaClassiD(text, difficulty, jwt);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<ClassUT> uploadClasse(@RequestBody ClassUT classe, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.uploadClasse(classe, jwt);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile classFile, @RequestParam("model") String model, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) throws IOException {
        return authenticationService.uploadFile(classFile, model, jwt, request);
    }

    @PostMapping("/uploadTest")
    @ResponseBody
    public ResponseEntity<FileUploadResponse> uploadTest(@RequestParam("file") MultipartFile classFile, @RequestParam("model") String model, @RequestParam("test") MultipartFile testFile, @RequestParam("testEvo") MultipartFile testFileEvo, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) throws IOException {
        return authenticationService.uploadTest(classFile, model, testFile, testFileEvo, jwt, request);
    }

    @PostMapping("/delete/{name}")
    @ResponseBody
    public ResponseEntity<?> eliminaClasse(@PathVariable String name, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.eliminaClasse(name, jwt);
    }

    @PostMapping("/deleteFile/{fileName}")
    @ResponseBody
    public ResponseEntity<String> eliminaFile(@PathVariable String fileName, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.eliminaFile(fileName, jwt);
    }

    //parte user

    @Autowired
    private User user;

    @GetMapping("/home/{text}")
    @ResponseBody
    public List<ClassUT> ricercaClasse(@PathVariable String text) {
        return user.ricercaClasse(text);
    }

    @GetMapping("/test")
    @ResponseBody
    public String test() {
        return user.test();
    }

    @GetMapping("/downloadFile/{name}")
    @ResponseBody
    public ResponseEntity<?> downloadClasse(@PathVariable("name") String name) throws Exception {
        return user.downloadClasse(name);
    }

    @PostMapping("/update/{name}")
    @ResponseBody
    public ResponseEntity<String> modificaClasse(@PathVariable String name, @RequestBody ClassUT newContent, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return authenticationService.modificaClasse(name, newContent, jwt);
    }

    @PostMapping("/registraAdmin")
    @ResponseBody
    public ResponseEntity<?> registraAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.registraAdmin(admin1, jwt);
    }

    @GetMapping("/admins/{username}")
    @ResponseBody
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.getAdminByUsername(username, jwt);
    }

    @GetMapping("/player")
    public ModelAndView showPlayer(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showPlayer(request, jwt);
    }

    @GetMapping("/class")
    public ModelAndView showClass(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return authenticationService.showClass(request, jwt);
    }
}

