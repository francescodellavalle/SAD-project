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
import com.groom.manvsclass.controller.Util;

import org.jcp.xml.dsig.internal.dom.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/utils")
public class UtilsController {

    @Autowired
    private Util utilsService;

    @GetMapping("/elencaInt")
    public List<interaction> elencaInt() {
        return utilsService.elencaInt();
    }

    @GetMapping("/elencaReport")
    public List<interaction> elencaReport() {
        return utilsService.elencaReport();
    }

    @GetMapping("/likes/{name}")
    public long likes(@PathVariable String name) {
        return utilsService.likes(name);
    }

    @PostMapping("/uploadInteraction")
    public interaction uploadInteraction(@RequestBody interaction interazione) {
        return utilsService.uploadInteraction(interazione);
    }

    @PostMapping("/newLike/{name}")
    public String newLike(@PathVariable String name) {
        return utilsService.newLike(name);
    }

    @PostMapping("/newReport/{name}")
    public String newReport(@PathVariable String name, @RequestBody String commento) {
        return utilsService.newReport(name, commento);
    }

    @DeleteMapping("/eliminaInteraction/{id_i}")
    public interaction eliminaInteraction(@PathVariable int id_i) {
        return utilsService.eliminaInteraction(id_i);
    }
}