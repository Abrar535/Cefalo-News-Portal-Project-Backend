package com.cefalonewsportal.backend.controller;


import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.service.FileSystemService;
import com.cefalonewsportal.backend.util.ImagesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class FileSystemController {
    @Autowired
    FileSystemService fileSystemService;
    @Autowired
    ImagesResponse imagesResponse;
    @PostMapping("/api/public/images/{storyId}")
    public ResponseEntity<?> addImages(@RequestParam("images") List<MultipartFile> images , @PathVariable("storyId") Integer storyId){

        return ResponseEntity.ok().body(fileSystemService.addImages(images,storyId));

    }

    @GetMapping("/api/public/images/{storyId}")
    public ResponseEntity<?> getImagesByStoryId( @PathVariable("storyId") Integer storyId){
        List<Resource> images =  fileSystemService.getImages(storyId);
        System.out.println("I am images "+ images);
        imagesResponse.setImages(images);
        return ResponseEntity.ok().body(imagesResponse);

    }




}
