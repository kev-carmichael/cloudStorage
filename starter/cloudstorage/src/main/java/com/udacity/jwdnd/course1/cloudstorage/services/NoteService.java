package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {

    @Autowired
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note, int userId){
        //changed instantiation to take Integer value of 0, instead of null
        return noteMapper.addNote(new Note(0, note.getNoteTitle(),
                note.getNoteDescription(), userId));
    }

    public int editNote(Note note){
        return noteMapper.editNote(note);
    }

    public int deleteNote(Integer noteId){
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
