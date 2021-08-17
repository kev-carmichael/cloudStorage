package com.udacity.jwdnd.course1.cloudstorage.services;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public int uploadFile(MultipartFile multipartFile, int userId) throws Exception{
        return fileMapper.uploadFile(new File
                        (null,
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        Long.toString(multipartFile.getSize(),
                        multipartFile.getBytes(),
                        userId)));
    }

    public File viewFileFromId(int fileId){
        return fileMapper.viewFileFromId(fileId);
    }

    public int deleteFile(int fileId){
        return fileMapper.deleteFile(fileId);
    }

    public isOnlyFileName(int userId, String fileName){
        Optional<File>file = Optional.ofNullable(fileMapper.isOnlyFileName(userId,fileName));
        return(file.isEmpty());
    }

    public List<File>getFilesFromUserId(int userId){
        return fileMapper.getFilesFromUserId(userId);
    }

}