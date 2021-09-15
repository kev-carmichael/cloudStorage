package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public Integer addNote(Note note, Integer userId){
        //changed instantiation to take Integer value of null, instead of int value of 0
        return noteMapper.addNote(new Note(null, note.getNoteTitle(),
                note.getNoteDescription(), userId));
    }

    public Integer editNote(Note note){
        return noteMapper.editNote(note);
    }

    public Integer deleteNote(Integer noteId){
        return noteMapper.deleteNote(noteId);
    }

    public boolean isOnlyNote(Integer userId, String noteTitle, String noteDescription){
        Optional<Note>note =
                Optional.ofNullable(noteMapper.isOnlyNote(userId, noteTitle, noteDescription));
        return (note.isEmpty());
    }

    public List<Note> getNotesFromUserId(Integer userId){
        return noteMapper.getNotesFromUserId(userId);
    }
}
