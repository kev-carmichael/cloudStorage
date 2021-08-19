package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {

    @Insert("INSERT INTO CREDENTIALS (credentialId, url, username, key, password, userId)" +
            ("VALUES(#{credentialId}, #{url}, #{username}, #{key}, #{password}, #{userId})"))
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int addCredential(Credential credential);

    @Update("UPDATE CREDENTIALS SET url=#{url}, username=#{username}, " +
            "key=#{key}, password=#{password} WHERE credentialId = #{credentialId}")
    Credential editCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialId = #{credentialId}")
    int deleteCredential(int credentialId);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId} AND username=#{username}")
    Credential isOnlyUsername(int userId, String username);

    @Select("SELECT * FROM CREDENTIALS WHERE userId=#{userId}")
    List<Credential> getFromUserId(int userId);

}
