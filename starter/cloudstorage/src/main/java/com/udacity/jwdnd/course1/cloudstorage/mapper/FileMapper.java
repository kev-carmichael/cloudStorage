package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (fileId, filename, contenttype, filesize, userid, filedata)" +
            "VALUES(#{fileId}, #{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File viewFileFromId(int fileId);

    // selected more than one parameter, as method takes 2 input parameters
    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName}")
    File isOnlyFileName(int userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesFromUserId(int userId);

}
