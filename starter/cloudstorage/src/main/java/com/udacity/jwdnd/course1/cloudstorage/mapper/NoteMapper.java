package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface NoteMapper {

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid)" +
            "VALUES(#{notetitle}, #{notedescription},  #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    int addNote(Note note);

    //given a Note can we call on one of its parameters, like noteId?
    @Update("UPDATE NOTES SET noteTitle=#{noteTitle}, noteDescription=#{noteDescription} WHERE noteId = #{noteId}")
    int editNote(Note note);

    @Delete("DELETE FROM NOTES WHERE noteId = #{noteId}")
    int deleteNote(int noteId);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId} AND noteTitle=#{noteTitle} AND noteDescription=#{noteDecription}")
    Note isOnlyNote(int userId, String noteTitle, String noteDescription);

    @Select("SELECT * FROM NOTES WHERE userId=#{userId}")
    List<Note> getNotesFromUserId(int userId);

}
