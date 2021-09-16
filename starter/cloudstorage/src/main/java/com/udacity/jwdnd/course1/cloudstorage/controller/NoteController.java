package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/note")
public class NoteController {
    private final UserService userService;
    private final NoteService noteService;

    @Autowired
    public NoteController(UserService userService, NoteService noteService) {
        this.userService = userService;
        this.noteService = noteService;
    }


    @PostMapping("/addnote")
    public String addOrEditNote(@ModelAttribute Note note, Model model, Authentication authentication) {
        // make sure link correct attribute names, spelling & syntax with HTML pages
        // and with Service class methods yet to be written ...
        int userFromId = userService.getUserFromId(authentication.getName());
        //.ofNullable() used to get instance of Optional (noteId)
        //returns empty if null, not NPE
        //THEREFORE noteId must be an INteger, not int
        Optional<Integer> noteId = Optional.ofNullable(note.getNoteId());
        if (noteId.isEmpty()) {
            return addNote(note, model, userFromId);
        } else return editNote(note, model, userFromId);
    }

    private String addNote(Note note, Model model, int userFromId){
        if(noteService.isOnlyNote(userFromId, note.getNoteTitle(), note.getNoteDescription())){
            return displayResult(model, noteService.addNote(note, userFromId));
        } else{
            return displayOtherErrorMsg( "Note already exists", model);
        }
    }

    private String editNote(Note note, Model model, int userFromId){
        if(noteService.isOnlyNote(userFromId, note.getNoteTitle(), note.getNoteDescription())){
            return displayResult(model, noteService.editNote(note));
        } else{
            return displayOtherErrorMsg( "Note already exists", model);
        }
    }

    private String displayOtherErrorMsg(String msg, Model model){
        model.addAttribute("otherErrorMsg", model);
        return "result";
    }

    @GetMapping("/deletenote/{noteId:.+}")
    public String deleteNote(@PathVariable Integer noteId, Model model){
        return displayResult(model, noteService.deleteNote(noteId));
    }

    private String displayResult(Model model, int entry){
        if(entry==1){
            model.addAttribute("successMsg", true);
        } else{
            model.addAttribute("notSavedErrorMsg", true);
        }
        return "result";
    }

}