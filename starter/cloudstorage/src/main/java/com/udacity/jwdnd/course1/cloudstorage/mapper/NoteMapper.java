package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (noteTitle, noteDescription, userId)" +
            "VALUES(#{noteTitle}, #{noteDescription},  #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note); //return value changed from int to Integer

    //given a Note can we call on one of its parameters, like noteId?
    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId = #{noteId}")
    int editNote(Note note); //return value changed from int to Integer

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(Integer noteId); //return value changed from int to Integer

    @Select("SELECT * FROM NOTES WHERE userId=#{userId} AND noteTitle=#{noteTitle} AND noteDescription=#{noteDescription}")
    Note isOnlyNote(Integer userId, String noteTitle, String noteDescription);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getNotesFromUserId(Integer userId);

}
