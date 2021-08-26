package com.udacity.jwdnd.course1.cloudstorage.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

@RequestMapping("/file")
@Controller
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @GetMapping("/viewfile/{fileId:.+}")
    public ResponseEntity<Resource>viewFile(@PathVariable int fileId){
        File file = fileService.viewFileFromId(fileId);
        InputStream inputStream = new ByteArrayInputStream(file.getFileData());
        InputStreamResource inputStreamResource = new InputStreamResource(inputStream);
        return ResponseEntity.ok().headers(HttpHeaders.CONTENT_DISPOSITION, "fileName="*file.getFileName()).
                contentType(MediaType.parseMediaType(file.getContentType())).body(inputStreamResource);
    }

    @GetMapping("/deletefile/{fileId:.+}")
    public String deleteFile(@PathVariable int fileId, Model model){
        return displayResult(model, fileService.deleteFile(fileId));
    }

    @PostMapping("/uploadfile")
    public String uploadFile(@RequestParam("fileUpload")MultipartFile multipartFile,
                             Authentication authentication, Model model) throws Exception{
        int userId = userService.getUserFromId(authentication.getName());
        if(!multipartFile.isEmpty()){
            if(fileService.isOnlyFileName(userId, multipartFile.getOriginalFilename())){
                return displayResult(model, fileService.uploadFile(multipartFile, userId));
            } else{
                return displayFileErrorMsg( "That file name already exists", model);
            }
        }else {
            return displayFileErrorMsg( "Error. Select file to upload first", model);
        }
    }

    private String displayResult(Model model, int entry){
        String resultMsg = null;
        if(entry==1){
            resultMsg.equals("successMsg");
        } else{
            resultMsg.equals("notSavedErrorMsg");
        }
        model.addAttribute(resultMsg, model);
        return "result";
    }

    private String displayFileErrorMsg(String msg, Model model){
        model.addAttribute("fileErrorMsg", msg);
        return "result";
    }

    private String displayOtherErrorMsg(String msg, Model model){
        model.addAttribute("otherErrorMsg", msg);
        return "result";
    }

}
