// lato utente
package com.groom.manvsclass.controller;

import com.example.model.ClassUT;
import com.example.repository.Srepo;
import com.example.utils.FileDownloadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class User {

    @Autowired
    private Srepo srepo;

    public List<ClassUT> ricercaClasse(String text) {
        return srepo.findByText(text);
    }

    public String test() {
        return "test T1";
    }

    public ResponseEntity<?> downloadClasse(String name) throws Exception {
        try {
            List<ClassUT> classe = srepo.findByText(name);
            ResponseEntity<?> file = FileDownloadUtil.downloadClassFile(classe.get(0).getcode_Uri());
            return file;
        } catch (Exception e) {
            return new ResponseEntity<>("Cartella non trovata.", HttpStatus.NOT_FOUND);
        }
    }
}
