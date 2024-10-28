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

    // ancora admin da admin list ad invitation

    @GetMapping("/admins_list")
@ResponseBody
public Object getAllAdmins(@CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.getAllAdmins(jwt);
}

@GetMapping("/info")
public ModelAndView showAdmin(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showAdmin(request, jwt);
}

@PostMapping("/password_change_admin")
@ResponseBody
public ResponseEntity<?> changePasswordAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.changePasswordAdmin(admin1, jwt);
}

@GetMapping("/password_change_admin")
public ModelAndView showChangePswAdminForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showChangePswAdminForm(request, jwt);
}

@PostMapping("/password_reset_admin")
@ResponseBody
public ResponseEntity<?> resetPasswordAdmin(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.resetPasswordAdmin(admin1, jwt);
}

@GetMapping("/password_reset_admin")
public ModelAndView showResetPswAdminForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showResetPswAdminForm(request, jwt);
}

@GetMapping("/invite_admins")
public ModelAndView showInviteAdmins(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showInviteAdmins(request, jwt);
}

@PostMapping("/invite_admins")
@ResponseBody
public ResponseEntity<?> inviteAdmins(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.inviteAdmins(admin1, jwt);
}

@GetMapping("/login_with_invitation")
public ModelAndView showLoginWithInvitationForm(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showLoginWithInvitationForm(request, jwt);
}

@PostMapping("/login_with_invitation")
@ResponseBody
public ResponseEntity<?> loginWithInvitation(@RequestBody Admin admin1, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.loginWithInvitation(admin1, jwt);
}

// scalata

@Autowired
private ScalataService scalataService;

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

// achievements and statistics admin
@GetMapping("/achievements")
public ModelAndView showAchievementsPage(HttpServletRequest request, @CookieValue(name = "jwt", required = false) String jwt) {
    return authenticationService.showAchievementsPage(request, jwt);
}

@GetMapping("/achievements/list")
@ResponseBody
public ResponseEntity<?> listAchievements() {
    return authenticationService.listAchievements();
}

@PostMapping("/achievements")
public Object createAchievement(Achievement achievement, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.createAchievement(achievement, jwt, request);
}

@GetMapping("/statistics/list")
@ResponseBody
public ResponseEntity<?> listStatistics() {
    return authenticationService.listStatistics();
}

@PostMapping("/statistics")
public Object createStatistic(Statistic statistic, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.createStatistic(statistic, jwt, request);
}

@DeleteMapping("/statistics/{Id}")
public Object deleteStatistic(@PathVariable("Id") String Id, @CookieValue(name = "jwt", required = false) String jwt, HttpServletRequest request) {
    return authenticationService.deleteStatistic(Id, jwt, request);
}

}
