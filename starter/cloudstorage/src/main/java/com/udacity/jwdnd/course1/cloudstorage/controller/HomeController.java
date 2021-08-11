package com.udacity.jwdnd.course1.cloudstorage.controller;

//make sure imported correct annotations
import com.udacity.jwdnd.course1.cloudstorage.services.AuthenticationService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final UserService userService;
    private final NoteService noteService;
    private final FileService fileService;
    private final CredentialService credentialService;
    private final AuthenticationService authenticationService;
    private final EncryptionService encryptionService;

    public HomeController(UserService userService,
                          NoteService noteService,
                          FileService fileService,
                          CredentialService credentialService,
                          AuthenticationService authenticationService,
                          EncryptionService encryptionService) {
        this.userService = userService;
        this.noteService = noteService;
        this.fileService = fileService;
        this.credentialService = credentialService;
        this.authenticationService = authenticationService;
        this.encryptionService = encryptionService;
    }

    List<Note>notes;
    List<User>users;
    List<File>files;
    List<Credential>credentials;
    List<String> decryptedPasswords;

    @PostConstruct
    public void postConstruct(){
        notes = new ArrayList<>();
        users = new ArrayList<>();
        files = new ArrayList<>();
        credentials = new ArrayList<>();
        decryptedPasswords = new ArrayList<>();
    }

    @GetMapping
    public String getHome(Mapper mapper, Authentication authentication){
        // make sure link correct attribute names, spelling & syntax with HTML pages
        // and with Service class methods yet to be written ...
        Integer userFromId = userService.getUserFromId(authentication.getName());
        notes = noteService.getNotesFromUserId(userFromId);
        files = fileService.getFilesFromUserId(userFromId);
        credentials = credentialService.getCredentialsFromUserId(userFromId);
        decryptedPasswords = encryptionService.getDecryptedPasswordsFromUserId(userFromId);

        model.addAttribute("credential", new Credential());
        model.addAttribute("note", new Note());
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("credentialService", credentialService);
        model.addAttribute("credentials", credentials);
        model.addAttribute("notes", notes);
        model.addAttribute("files",files);

        return "home";
    }
}
