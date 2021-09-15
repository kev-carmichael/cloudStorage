package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Insert("INSERT INTO FILES (fileId, fileName, contentType, fileSize, userId, fileData)" +
            "VALUES(#{fileId}, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int uploadFile(File file);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(Integer fileId);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File viewFileFromId(int fileId);

    // selected more than one parameter, as method takes 2 input parameters
    @Select("SELECT * FROM FILES WHERE userId = #{userId} AND fileName = #{fileName}")
    File isOnlyFileName(Integer userId, String fileName);

    @Select("SELECT * FROM FILES WHERE userId = #{userId}")
    List<File> getFilesFromUserId(int userId);

}
