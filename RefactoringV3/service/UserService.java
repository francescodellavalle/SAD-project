package com.groom.manvsclass.service;

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

import com.groom.manvsclass.controller.*;
import com.groom.manvsclass.model.ClassUT;
import com.groom.manvsclass.model.filesystem.download.FileDownloadUtil;
import com.groom.manvsclass.model.repository.SearchRepositoryImpl;
import com.groom.manvsclass.service.*;
import com.groom.manvsclass.controller.Authentication.AuthenticatedAdminRepository;

import org.springframework.http.HttpStatus;
import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Service
public class UserService {

    @Autowired
    private final SearchRepositoryImpl srepo; 
   // @GetMapping("/test")
   // @ResponseBody
    public String test() {
        return "test T1";
    }

    //@GetMapping("/downloadFile/{name}")
    //@ResponseBody
    public ResponseEntity<?> downloadClasse(@PathVariable("name") String name) throws Exception {

        System.out.println("/downloadFile/{name} (HomeController) - name: "+ name);
        System.out.println("test");
        try{
            List<ClassUT> classe= srepo.findByText(name);
            System.out.println("File download:");
            System.out.println(classe.get(0).getcode_Uri());
            ResponseEntity file =  FileDownloadUtil.downloadClassFile(classe.get(0).getcode_Uri());
            return file;
        }
        catch(Exception e){
            System.out.println("Eccezione------------");
            return new ResponseEntity<>("Cartella non trovata.", HttpStatus.NOT_FOUND);
            }
        }

    //@GetMapping("/home/{text}")
        //@ResponseBody
    public    List<ClassUT>    ricercaClasse(@PathVariable String text) {
    return srepo.findByText(text);
    }
}