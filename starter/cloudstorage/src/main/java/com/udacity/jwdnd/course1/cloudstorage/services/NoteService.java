package com.udacity.jwdnd.course1.cloudstorage.services;


import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NoteService {

    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int addNote(Note note, int userId){
        return noteMapper.addNote(new Note(0, note.getNoteTitle(),
                note.getNoteDescription(), userId));
    }

    public int editNote(Note note){
        return noteMapper.editNote(note);
    }

    public int deleteNote(int noteId){
        return noteMapper.deleteNote(noteId);
    }

    public boolean isOnlyNote(int userId, String noteTitle, String noteDescription){
        Optional<Note>note =
                Optional.ofNullable(noteMapper.isOnlyNote(userId, noteTitle, noteDescription));
        return (note.isEmpty());
    }

    public List<Note>getNotesFromUserId(int userId){
        return noteMapper.getNotesFromUserId(userId);
    }
}
