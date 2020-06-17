package com.cefalonewsportal.backend.controller;

import com.cefalonewsportal.backend.model.Tag;

import com.cefalonewsportal.backend.repository.TagRepository;
import com.cefalonewsportal.backend.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TagController {

    @Autowired
    TagService tagService;


    /*Get all tags*/
    @GetMapping("/api/public/tags")
    public ResponseEntity<?> getAllTags(){

        return ResponseEntity.ok().body(tagService.getAllTags());

    }
    @GetMapping("/api/public/tags/trending")
    public ResponseEntity<?> getTrendingTags() {
        return ResponseEntity.ok().body(tagService.getTrendingTags());
    }



}
