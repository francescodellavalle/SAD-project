// controller delle funzionalità ritenute utili (findreport, orderbydate etc)
package com.groom.manvsclass.controller;

import com.example.model.interaction;
import com.example.repository.InteractionRepository;
import com.example.repository.Srepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;

@Component
public class Utils {

    @Autowired
    private InteractionRepository repo_int;

    @Autowired
    private Srepo srepo;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<interaction> elencaInt() {
        return repo_int.findAll();
    }

    public List<interaction> elencaReport() {
        return srepo.findReport();
    }

    public long likes(String name) {
        return srepo.getLikes(name);
    }

    public interaction uploadInteraction(interaction interazione) {
        return repo_int.save(interazione);
    }

    public int API_id() {
        Random random = new Random();
        return random.nextInt(1000000 - 0 + 1) + 0;
    }

    public String API_email(int id_u) {
        return "prova." + id_u + "@email.com";
    }

    public String newLike(String name) {
        interaction newInteraction = new interaction();
        int id_u = API_id();
        String email_u = API_email(id_u);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String data = currentDate.format(formatter);

        newInteraction.setId_i(0);
        newInteraction.setId(id_u);
        newInteraction.setEmail(email_u);
        newInteraction.setName(name);
        newInteraction.setType(1);
        newInteraction.setDate(data);
        repo_int.save(newInteraction);

        return "Nuova interazione di tipo 'like' inserita per la classe: " + name;
    }

    public String newReport(String name, String commento) {
        interaction newInteraction = new interaction();
        int id_u = API_id();
        String email_u = API_email(id_u);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String data = currentDate.format(formatter);

        newInteraction.setId_i(0);
        newInteraction.setId(id_u);
        newInteraction.setEmail(email_u);
        newInteraction.setName(name);
        newInteraction.setType(0);
        newInteraction.setDate(data);
        newInteraction.setCommento(commento);
        repo_int.save(newInteraction);

        return "Nuova interazione di tipo 'report' inserita per la classe: " + name;
    }

    public interaction eliminaInteraction(int id_i) {
        Query query = new Query();
        query.addCriteria(Criteria.where("id_i").is(id_i));
        return mongoTemplate.findAndRemove(query, interaction.class);
    }
}