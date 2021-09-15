package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userId)" +
            ("VALUES(#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})"))
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, " +
            "key=#{key}, password=#{password} WHERE credentialId = #{credentialId}")
    Integer editCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    Integer deleteCredential(Integer credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId} AND username=#{username}")
    Credential isOnlyUsername(Integer userId, String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getFromUserId(Integer userId);

}
