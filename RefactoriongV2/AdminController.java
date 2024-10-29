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

@Controller
public class AdminController {

    @Autowired
    private AuthenticationService adminService;

    @Autowired
    private ScalataService scalataService;

    @Autowired
    private AchievementService achievementService;

    @GetMapping("/home_adm")
    public ModelAndView showHomeAdmin(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showHomeAdmin(request, jwt);
    }

    @GetMapping("/registraAdmin")
    public String showRegistraAdmin() {
        return adminService.showRegistraAdmin();
    }

    @GetMapping("/modificaClasse")
    public ModelAndView showModificaClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showModificaClasse(request, jwt);
    }

    @GetMapping("/uploadClasse")
    public ModelAndView showUploadClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showUploadClasse(request, jwt);
    }

    @GetMapping("/uploadClasseAndTest")
    public ModelAndView showUploadClasseAndTest(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showUploadClasseAndTest(request, jwt);
    }

    @GetMapping("/reportClasse")
    public ModelAndView showReportClasse(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showReportClasse(request, jwt);
    }

    @GetMapping("/Reports")
    public ModelAndView showReports(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showReports(request, jwt);
    }

    @GetMapping("/orderbydate")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> ordinaClassi(@CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.ordinaClassi(jwt);
    }

    @GetMapping("/orderbyname")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> ordinaClassiNomi(@CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.ordinaClassiNomi(jwt);
    }

    @GetMapping("/Cfilterby/{category}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> filtraClassi(@PathVariable String category, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.filtraClassi(category, jwt);
    }

    @GetMapping("/Cfilterby/{text}/{category}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> filtraClassi(@PathVariable String text, @PathVariable String category, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.filtraClassi(text, category, jwt);
    }

    @GetMapping("/Dfilterby/{difficulty}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> elencaClassiD(@PathVariable String difficulty, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.elencaClassiD(difficulty, jwt);
    }

    @GetMapping("/Dfilterby/{text}/{difficulty}")
    @ResponseBody
    public ResponseEntity<List<ClassUT>> elencaClassiD(@PathVariable String text, @PathVariable String difficulty, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.elencaClassiD(text, difficulty, jwt);
    }

    @PostMapping("/insert")
    @ResponseBody
    public ResponseEntity<ClassUT> uploadClasse(@RequestBody ClassUT classe, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.uploadClasse(classe, jwt);
    }

    @PostMapping("/uploadFile")
    @ResponseBody
    public ResponseEntity<FileUploadResponse> uploadFile(@RequestParam("file") MultipartFile classFile, @RequestParam("model") String model, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) throws IOException {
        return adminService.uploadFile(classFile, model, jwt, request);
    }

    @PostMapping("/uploadTest")
    @ResponseBody
    public ResponseEntity<FileUploadResponse> uploadTest(@RequestParam("file") MultipartFile classFile, @RequestParam("model") String model, @RequestParam("test") MultipartFile testFile, @RequestParam("testEvo") MultipartFile testFileEvo, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) throws IOException {
        return adminService.uploadTest(classFile, model, testFile, testFileEvo, jwt, request);
    }

    @PostMapping("/delete/{name}")
    @ResponseBody
    public ResponseEntity<?> eliminaClasse(@PathVariable String name, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.eliminaClasse(name, jwt);
    }

    @PostMapping("/deleteFile/{fileName}")
    @ResponseBody
    public ResponseEntity<String> eliminaFile(@PathVariable String fileName, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.eliminaFile(fileName, jwt);
    }
    
    @GetMapping("/admins_list")
    @ResponseBody
    public Object getAllAdmins(@CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.getAllAdmins(jwt);
    }
    
    @GetMapping("/info")
    public ModelAndView showAdmin(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showAdmin(request, jwt);
    }
    
    @PostMapping("/password_change_admin")
    @ResponseBody
    public ResponseEntity<?> changePasswordAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return adminService.changePasswordAdmin(admin1, jwt);
    }
    
    @GetMapping("/password_change_admin")
    public ModelAndView showChangePswAdminForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showChangePswAdminForm(request, jwt);
    }
    
    @PostMapping("/password_reset_admin")
    @ResponseBody
    public ResponseEntity<?> resetPasswordAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return adminService.resetPasswordAdmin(admin1, jwt);
    }
    
    @GetMapping("/password_reset_admin")
    public ModelAndView showResetPswAdminForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showResetPswAdminForm(request, jwt);
    }
    
    @GetMapping("/invite_admins")
    public ModelAndView showInviteAdmins(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showInviteAdmins(request, jwt);
    }
    
    @PostMapping("/invite_admins")
    @ResponseBody
    public ResponseEntity<?> inviteAdmins(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return adminService.inviteAdmins(admin1, jwt);
    }
    
    @GetMapping("/login_with_invitation")
    public ModelAndView showLoginWithInvitationForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showLoginWithInvitationForm(request, jwt);
    }
    
    @PostMapping("/login_with_invitation")
    @ResponseBody
    public ResponseEntity<?> loginWithInvitation(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return adminService.loginWithInvitation(admin1, jwt);
    }

    @GetMapping("/scalata")
    public ModelAndView showGamePageScalata(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return scalataService.showGamePageScalata(request, jwt);
    }

    @PostMapping("/configureScalata")
    public ResponseEntity<?> uploadScalata(@RequestBody Scalata scalata, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return scalataService.uploadScalata(scalata, jwt);
    }

    @GetMapping("/scalate_list")
    @ResponseBody
    public ResponseEntity<?> listScalate() {
        return scalataService.listScalate();
    }

    @DeleteMapping("delete_scalata/{scalataName}")
    @ResponseBody
    public ResponseEntity<?> deleteScalataByName(@PathVariable String scalataName, @CookieValue(name = "jwt", required = false) String jwt) {
        return scalataService.deleteScalataByName(scalataName, jwt);
    }

    @GetMapping("/retrieve_scalata/{scalataName}")
    @ResponseBody
    public ResponseEntity<?> retrieveScalataByName(@PathVariable String scalataName) {
        return scalataService.retrieveScalataByName(scalataName);
    }

    @GetMapping("/achievements")
    public ModelAndView showAchievementsPage(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return achievementService.showAchievementsPage(request, jwt);
    }

    @GetMapping("/achievements/list")
    @ResponseBody
    public ResponseEntity<?> listAchievements() {
        return achievementService.listAchievements();
    }

    @PostMapping("/achievements")
    public Object createAchievement(Achievement achievement, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return achievementService.createAchievement(achievement, jwt, request);
    }

    @GetMapping("/statistics/list")
    @ResponseBody
    public ResponseEntity<?> listStatistics() {
        return achievementService.listStatistics();
    }

    @PostMapping("/statistics")
    public Object createStatistic(Statistic statistic, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return achievementService.createStatistic(statistic, jwt, request);
    }

    @DeleteMapping("/statistics/{Id}")
    public Object deleteStatistic(@PathVariable("Id") String Id, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return achievementService.deleteStatistic(Id, jwt, request);
    }

    @PostMapping("/update/{name}")
    @ResponseBody
    public ResponseEntity<String> modificaClasse(@PathVariable String name, @RequestBody ClassUT newContent, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
        return adminService.modificaClasse(name, newContent, jwt, request);
    }

    @PostMapping("/registraAdmin")
    @ResponseBody
    public ResponseEntity<?> registraAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.registraAdmin(admin1, jwt);
    }

    @GetMapping("/admins/{username}")
    @ResponseBody
    public ResponseEntity<Admin> getAdminByUsername(@PathVariable String username, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.getAdminByUsername(username, jwt);
    }

    @GetMapping("/player")
    public ModelAndView showPlayer(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showPlayer(request, jwt);
    }

    @GetMapping("/class")
    public ModelAndView showClass(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
        return adminService.showClass(request, jwt);
    }
}