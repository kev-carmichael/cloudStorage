package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
public class FileService {

    private final FileMapper fileMapper;

    public FileService(FileMapper fileMapper) {
        this.fileMapper = fileMapper;
    }

    public Integer uploadFile(MultipartFile multipartFile, Integer userId) throws Exception{
        return fileMapper.uploadFile(new File
                        (null,
                        multipartFile.getOriginalFilename(),
                        multipartFile.getContentType(),
                        Long.toString(multipartFile.getSize()),
                        userId, multipartFile.getBytes()));
    }

    public File viewFileFromId(Integer fileId){
        return fileMapper.viewFileFromId(fileId);
    }

    public Integer deleteFile(Integer fileId){
        return fileMapper.deleteFile(fileId);
    }

    public boolean isOnlyFileName(Integer userId, String fileName){
        Optional<File>file = Optional.ofNullable(fileMapper.isOnlyFileName(userId,fileName));
        return(file.isEmpty());
    }

    public List<File> getFilesFromUserId(Integer userId){
        return fileMapper.getFilesFromUserId(userId);
    }

}
