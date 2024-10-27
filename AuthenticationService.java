package com.groom.manvsclass.controller;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.servlet.ModelAndView;
import com.example.model.ClassUT;
import com.example.model.Operation;
import com.example.repository.ClassRepository;
import com.example.repository.OperationRepository;
import com.example.repository.Srepo;
import com.example.utils.FileUploadResponse;
import com.example.utils.FileUploadUtil;
import com.example.utils.RobotUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


//AuthenticationService.java:

// Create methods for each admin-related route.
// Implement JWT validation logic within these methods.

@Service
public class AuthenticationService {

    public boolean isJwtValid(String jwt) {
        // Implement JWT validation logic here
        return jwt != null && !jwt.isEmpty(); // Example logic
    }

    public ModelAndView showHomeAdmin(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("home_adm");
        return new ModelAndView("redirect:/loginAdmin");
    }

    public String showRegistraAdmin() {
        return "registraAdmin";
    }

    public ModelAndView showModificaClasse(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("modificaClasse");
        return new ModelAndView("redirect:/loginAdmin");
    }

    public ModelAndView showUploadClasse(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("uploadClasse");
        return new ModelAndView("redirect:/loginAdmin");
    }

    public ModelAndView showUploadClasseAndTest(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("uploadClasseAndTest");
        return new ModelAndView("redirect:/loginAdmin");
    }

    public ModelAndView showReportClasse(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("reportClasse");
        return new ModelAndView("redirect:/loginAdmin");
    }

    public ModelAndView showReports(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("Reports");
        return new ModelAndView("redirect:/loginAdmin");
    }

    // parte funzioni admin

    @Autowired
    private Srepo srepo;

    @Autowired
    private ClassRepository repo;

    @Autowired
    private OperationRepository orepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AdminRepository arepo;

    @Autowired
    private EmailService emailService;

    public ResponseEntity<List<ClassUT>> ordinaClassi(String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiOrdinate = srepo.orderByDate();
            return ResponseEntity.ok().body(classiOrdinate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<List<ClassUT>> ordinaClassiNomi(String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiOrdinateNome = srepo.orderByName();
            return ResponseEntity.ok().body(classiOrdinateNome);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<List<ClassUT>> filtraClassi(String category, String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiFiltrate = srepo.filterByCategory(category);
            return ResponseEntity.ok().body(classiFiltrate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<List<ClassUT>> filtraClassi(String text, String category, String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiFiltrate = srepo.searchAndFilter(text, category);
            return ResponseEntity.ok().body(classiFiltrate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<List<ClassUT>> elencaClassiD(String difficulty, String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiFiltrate = srepo.filterByDifficulty(difficulty);
            return ResponseEntity.ok().body(classiFiltrate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<List<ClassUT>> elencaClassiD(String text, String difficulty, String jwt) {
        if (isJwtValid(jwt)) {
            List<ClassUT> classiFiltrate = srepo.searchAndDFilter(text, difficulty);
            return ResponseEntity.ok().body(classiFiltrate);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<ClassUT> uploadClasse(ClassUT classe, String jwt) {
        if (isJwtValid(jwt)) {
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String data = currentDate.format(formatter);
            Operation operation1 = new Operation((int) orepo.count(), "userAdmin", classe.getName(), 0, data);
            orepo.save(operation1);
            ClassUT savedClasse = repo.save(classe);
            return ResponseEntity.ok().body(savedClasse);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    public ResponseEntity<FileUploadResponse> uploadFile(MultipartFile classFile, String model, String jwt, HttpServletRequest request) throws IOException {
        if (isJwtValid(jwt)) {
            ObjectMapper mapper = new ObjectMapper();
            ClassUT classe = mapper.readValue(model, ClassUT.class);
            String fileName = classFile.getOriginalFilename();
            long size = classFile.getSize();
            FileUploadUtil.saveCLassFile(fileName, classe.getName(), classFile);
            RobotUtil.generateAndSaveRobots(fileName, classe.getName(), classFile);
            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileName);
            response.setSize(size);
            response.setDownloadUri("/downloadFile");
            classe.setUri("Files-Upload/" + classe.getName() + "/" + fileName);
            classe.setDate(LocalDate.now().toString());
            Operation operation1 = new Operation((int) orepo.count(), "userAdmin", classe.getName(), 0, LocalDate.now().toString());
            orepo.save(operation1);
            repo.save(classe);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            FileUploadResponse response = new FileUploadResponse();
            response.setErrorMessage("Errore, il token non è valido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<FileUploadResponse> uploadTest(MultipartFile classFile, String model, MultipartFile testFile, MultipartFile testFileEvo, String jwt, HttpServletRequest request) throws IOException {
        if (isJwtValid(jwt)) {
            ObjectMapper mapper = new ObjectMapper();
            ClassUT classe = mapper.readValue(model, ClassUT.class);
            String fileNameClass = classFile.getOriginalFilename();
            long size = classFile.getSize();
            FileUploadUtil.saveCLassFile(fileNameClass, classe.getName(), classFile);
            String fileNameTest = testFile.getOriginalFilename();
            String fileNameTestEvo = testFileEvo.getOriginalFilename();
            RobotUtil.saveRobots(fileNameClass, fileNameTest, fileNameTestEvo, classe.getName(), classFile, testFile, testFileEvo);
            FileUploadResponse response = new FileUploadResponse();
            response.setFileName(fileNameClass);
            response.setSize(size);
            response.setDownloadUri("/downloadFile");
            classe.setUri("Files-Upload/" + classe.getName() + "/" + fileNameClass);
            classe.setDate(LocalDate.now().toString());
            Operation operation1 = new Operation((int) orepo.count(), "userAdmin", classe.getName() + " con Robot", 0, LocalDate.now().toString());
            orepo.save(operation1);
            repo.save(classe);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            FileUploadResponse response = new FileUploadResponse();
            response.setErrorMessage("Errore, il token non è valido");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<?> eliminaClasse(String name, String jwt) {
        if (isJwtValid(jwt)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            eliminaFile(name, jwt);
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String data = currentDate.format(formatter);
            Operation operation1 = new Operation((int) orepo.count(), "userAdmin", name, 2, data);
            orepo.save(operation1);
            ClassUT deletedClass = mongoTemplate.findAndRemove(query, ClassUT.class);
            if (deletedClass != null) {
                return ResponseEntity.ok().body(deletedClass);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Classe non trovata");
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token JWT non valido");
        }
    }

    public ResponseEntity<String> eliminaFile(String fileName, String jwt) {
        if (isJwtValid(jwt)) {
            String folderPath = "Files-Upload/" + fileName;
            File directoryRandoop = new File("/VolumeT9/app/FolderTree/" + fileName);
            File directoryEvo = new File("/VolumeT8/FolderTreeEvo/" + fileName);
            File folderToDelete = new File(folderPath);
            if (folderToDelete.exists() && folderToDelete.isDirectory()) {
                try {
                    FileUploadUtil.deleteDirectory(folderToDelete);
                    FileUploadUtil.deleteDirectory(directoryRandoop);
                    FileUploadUtil.deleteDirectory(directoryEvo);
                    return new ResponseEntity<>("Cartella eliminata con successo (/deleteFile/{fileName})", HttpStatus.OK);
                } catch (IOException e) {
                    return new ResponseEntity<>("Impossibile eliminare la cartella.", HttpStatus.INTERNAL_SERVER_ERROR);
                }
            } else {
                return new ResponseEntity<>("Cartella non trovata.", HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>("Token JWT non valido.", HttpStatus.UNAUTHORIZED);
        }
    }

    // registra admine e update name

    @Autowired
    private PasswordEncoder myPasswordEncoder;

    private Admin userAdmin = new Admin();

    public ResponseEntity<String> modificaClasse(String name, ClassUT newContent, String jwt) {
        if (isJwtValid(jwt)) {
            Query query = new Query();
            query.addCriteria(Criteria.where("name").is(name));
            Update update = new Update().set("name", newContent.getName())
                    .set("date", newContent.getDate())
                    .set("difficulty", newContent.getDifficulty())
                    .set("description", newContent.getDescription())
                    .set("category", newContent.getCategory());
            long modifiedCount = mongoTemplate.updateFirst(query, update, ClassUT.class).getModifiedCount();

            if (modifiedCount > 0) {
                LocalDate currentDate = LocalDate.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String data = currentDate.format(formatter);
                Operation operation1 = new Operation((int) orepo.count(), userAdmin.getUsername(), newContent.getName(), 1, data);
                orepo.save(operation1);
                return new ResponseEntity<>("Aggiornamento eseguito correttamente.", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Nessuna classe trovata o nessuna modifica effettuata.", HttpStatus.NOT_FOUND);
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Errore nel completamente dell'operazione");
        }
    }

    public ResponseEntity<?> registraAdmin(Admin admin1, String jwt) {
        if (isJwtValid(jwt)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Already logged in");
        }

        if (admin1.getNome().length() >= 2 && admin1.getNome().length() <= 30 && Pattern.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*", admin1.getNome())) {
            this.userAdmin.setNome(admin1.getNome());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome non valido");
        }

        if (admin1.getCognome().length() >= 2 && admin1.getCognome().length() <= 30 && Pattern.matches("[a-zA-Z]+(\\s?[a-zA-Z]+\\'?)*", admin1.getCognome())) {
            this.userAdmin.setCognome(admin1.getCognome());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cognome non valido");
        }

        if (admin1.getUsername().length() >= 2 && admin1.getUsername().length() <= 30 && Pattern.matches(".*_unina$", admin1.getUsername())) {
            this.userAdmin.setUsername(admin1.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username non valido, deve rispettare il seguente formato: [username di lunghezza compresa tra 2 e 30 caratteri]_unina");
        }

        if (Pattern.matches("^[a-zA-Z0-9._%+-]+@(?:studenti\\.)?unina\\.it$", admin1.getEmail())) {
            Admin existingAdmin = arepo.findById(admin1.getEmail()).orElse(null);
            if (existingAdmin != null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin con questa mail già registrato");
            }
            this.userAdmin.setEmail(admin1.getEmail());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email non valida, registrarsi con le credenziali istituzionali!");
        }

        Matcher m = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$").matcher(admin1.getPassword());
        if (admin1.getPassword().length() > 16 || admin1.getPassword().length() < 8 || !m.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password non valida! La password deve contenere almeno una lettera maiuscola, una minuscola, un numero ed un carattere speciale e deve essere lunga tra gli 8 e i 16 caratteri");
        }

        String crypted = myPasswordEncoder.encode(admin1.getPassword());
        this.userAdmin.setPassword(crypted);

        Admin savedAdmin = arepo.save(this.userAdmin);
        return ResponseEntity.ok().body(savedAdmin);
    }

    // admin altro

    public Object getAllAdmins(String jwt) {
        if (isJwtValid(jwt)) {
            return arepo.findAll();
        } else {
            return new RedirectView("/loginAdmin");
        }
    }

    public ModelAndView showAdmin(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("info");
        return new ModelAndView("login_admin");
    }

    public ResponseEntity<?> changePasswordAdmin(Admin admin1, String jwt) {
        if (isJwtValid(jwt)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Già loggato");
        }

        Admin admin = arepo.findById(admin1.getEmail()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trovata");
        }

        Admin admin_reset = srepo.findAdminByResetToken(admin1.getResetToken());
        if (!admin_reset.getResetToken().equals(admin1.getResetToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token di reset invalido!");
        }

        Matcher m = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$").matcher(admin1.getPassword());
        if (admin1.getPassword().length() > 16 || admin1.getPassword().length() < 8 || !m.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password non valida! La password deve contenere almeno una lettera maiuscola, una minuscola, un numero ed un carattere speciale e deve essere lunga tra gli 8 e i 16 caratteri");
        }

        String crypted = myPasswordEncoder.encode(admin1.getPassword());
        admin1.setPassword(crypted);
        admin.setPassword(admin1.getPassword());
        admin1.setResetToken(null);
        admin.setResetToken(admin1.getResetToken());

        Admin savedAdmin = arepo.save(admin);
        return ResponseEntity.ok().body(savedAdmin);
    }

    public ModelAndView showChangePswAdminForm(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("redirect:/login_admin");
        return new ModelAndView("password_change_admin");
    }

    public ResponseEntity<?> resetPasswordAdmin(Admin admin1, String jwt) {
        if (isJwtValid(jwt)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Già loggato");
        }

        Admin admin = arepo.findById(admin1.getEmail()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email non trovata");
        }

        String resetToken = generateToken(admin);
        admin.setResetToken(resetToken);

        Admin savedAdmin = arepo.save(admin);
        try {
            emailService.sendPasswordResetEmail(savedAdmin.getEmail(), savedAdmin.getResetToken());
            return ResponseEntity.ok().body(savedAdmin);
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'invio del messaggio di posta");
        }
    }

    public ModelAndView showResetPswAdminForm(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("redirect:/login_admin");
        return new ModelAndView("password_reset_admin");
    }

    public ModelAndView showInviteAdmins(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("invite_admins");
        return new ModelAndView("login_admin");
    }

    public ResponseEntity<?> inviteAdmins(Admin admin1, String jwt) {
        if (!isJwtValid(jwt)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Attenzione, non sei loggato");
        }

        Admin admin = arepo.findById(admin1.getEmail()).orElse(null);
        if (admin != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email trovata, la persona che stai tentando di invitare è già un amministratore!");
        }

        Admin new_admin = new Admin("default", "default", "default", "default", "default");
        new_admin.setEmail(admin1.getEmail());

        String invitationToken = generateToken(new_admin);
        new_admin.setInvitationToken(invitationToken);

        Admin savedAdmin = arepo.save(new_admin);
        try {
            emailService.sendInvitationToken(savedAdmin.getEmail(), savedAdmin.getInvitationToken());
            return ResponseEntity.ok().body("Invitation token inviato correttamente all'indirizzo:" + savedAdmin.getEmail());
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore nell'invio del messaggio di posta");
        }
    }

    public ModelAndView showLoginWithInvitationForm(HttpServletRequest request, String jwt) {
        if (isJwtValid(jwt)) return new ModelAndView("login_admin");
        return new ModelAndView("login_with_invitation");
    }

    public ResponseEntity<?> loginWithInvitation(Admin admin1, String jwt) {
        if (isJwtValid(jwt)) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Attenzione, hai già un token valido!");
        }

        Admin admin = arepo.findById(admin1.getEmail()).orElse(null);
        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email non trovata");
        }

        Admin admin_invited = srepo.findAdminByInvitationToken(admin1.getInvitationToken());
        if (!admin_invited.getInvitationToken().equals(admin1.getInvitationToken())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token di invito invalido!");
        }

        admin.setEmail(admin1.getEmail());

        if (admin1.getNome().length() >= 2 && admin1.getNome().length() <= 30 && Pattern.matches("[a-zA-Z]+(\\s[a-zA-Z]+)*", admin1.getNome())) {
            admin.setNome(admin1.getNome());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Nome non valido");
        }

        if (admin1.getCognome().length() >= 2 && admin1.getCognome().length() <= 30 && Pattern.matches("[a-zA-Z]+(\\s?[a-zA-Z]+\\'?)*", admin1.getCognome())) {
            admin.setCognome(admin1.getCognome());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Cognome non valido");
        }

        if (admin1.getUsername().length() >= 2 && admin1.getUsername().length() <= 30 && Pattern.matches(".*_invited$", admin1.getUsername())) {
            admin.setUsername(admin1.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username non valido, deve rispettare il seguente formato: [username di lunghezza compresa tra 2 e 30 caratteri]_invited");
        }

        Matcher m = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,16}$").matcher(admin1.getPassword());
        if (admin1.getPassword().length() > 16 || admin1.getPassword().length() < 8 || !m.matches()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password non valida! La password deve contenere almeno una lettera maiuscola, una minuscola, un numero ed un carattere speciale e deve essere lunga tra gli 8 e i 16 caratteri");
        }

        String crypted = myPasswordEncoder.encode(admin1.getPassword());
        admin1.setPassword(crypted);
        admin.setPassword(admin1.getPassword());

        admin1.setInvitationToken(null);
        admin.setInvitationToken(admin1.getInvitationToken());

        Admin savedAdmin = arepo.save(admin);
        return ResponseEntity.ok().body(savedAdmin);
    }
}