package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/credential")
public class CredentialController {
    private final UserService userService;
    private final CredentialService credentialService;

    public CredentialController(UserService userService, CredentialService credentialService) {
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/addcredential")
    public String addOrEditCredential(@ModelAttribute Credential credential, Model model, Authentication authentication) {
        // make sure link correct attribute names, spelling & syntax with HTML pages
        Integer userFromId = userService.getUserFromId(authentication.getName());
        //.ofNullable() used to get instance of Optional (noteId)
        //returns empty if null, not NPE
        Optional<Integer> credentialId = Optional.ofNullable(credential.getCredentialId());
        if (credentialId.isEmpty()) {
            return addCredential(credential, model, userFromId);
        } else return editCredential(credential, model, userFromId);
    }

    private String addCredential(Credential credential, Model model, Integer userFromId){
        if(credentialService.isOnlyUsername(userFromId, credential.getUsername())){
            credential.setUserId(userFromId);
            return displayResult(model, credentialService.addCredential(credential));
        } else{
            return displayOtherErrorMsg( "User already exists", model);
        }
    }

    private String editCredential(Credential credential, Model model, Integer userFromId){
        if(credentialService.isOnlyUsername(userFromId, credential.getUsername())){
        return displayResult(model, credentialService.editCredential(credential));
        } else{
            return displayOtherErrorMsg( "User already exists", model);
        }
    }

    private String displayOtherErrorMsg(String msg, Model model){
        model.addAttribute("otherErrorMsg", msg);
        return "result";
    }

    @GetMapping("/deletecredential/{credentialId:.+}")
    public String deleteCredential(@PathVariable Integer credentialId, Model model){
        return displayResult(model, credentialService.deleteCredential(credentialId));
    }

    private String displayResult(Model model, int entry){
        String resultMsg = null;
        if(entry==1){
            resultMsg.equals("successMsg");
        } else{
            resultMsg.equals("notSavedErrorMsg");
        }
        model.addAttribute(resultMsg, model);
        return "result";
    }
}
