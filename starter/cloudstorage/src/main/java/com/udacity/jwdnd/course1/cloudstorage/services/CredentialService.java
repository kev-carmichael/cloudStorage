package com.udacity.jwdnd.course1.cloudstorage.services;

import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<Credential>credentialFromUserId(int userId);

    public int addCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        return credentialMapper.addCredential(credential);
    }

    public String editCredential(Credential credential){
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);
        return credentialMapper.editCredential(credential);
    }

    public String deleteCredential(int credentialId){
        return credentialMapper.deleteCredential(credentialId);
    }

    public List<String> getDecryptedPasswordsFromUserId(int userFromId){
        List<Credential>listOfCredentials = credentailFromUserId((userFromId));
        if(listOfCredentials!=null){
            return listOfCredentials.stream().filter(Objects::nonNull)
                    .map(credential -> encryptionService.decryptValue(credential.getPassword(), credential.getKey()))
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }

    public boolean isOnlyUsername(int userId, String username){
        Optional<Credential> credential =
                Optional.ofNullable(credentialMapper.isOnlyUsername(userId, username));
        return (credential.isEmpty());
    }

    public List<Credential> getFromUserId(int userId) {
        return credentialMapper.getFromUserId(userId);
    }


}
