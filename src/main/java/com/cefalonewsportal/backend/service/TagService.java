package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.model.Tag;
import com.cefalonewsportal.backend.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparingInt;

@Service
public class TagService {
    @Autowired
    TagRepository tagRepository;

    public Tag createTag(Tag tag){
        Tag existingTag = tagRepository.findByTagName(tag.getTagName());

        if(existingTag == null){

            tag = tagRepository.save(tag);

        }
        else{
            tag = existingTag;
        }
      return tag;
    }

    public List<Tag> getAllTags(){

        return tagRepository.findAll();

    }

    public List<Tag> getTrendingTags(){

        List<Tag> tags = tagRepository.findAll();
        tags.sort (
                comparingInt(tag -> tag.getStories().size())
        );
        Collections.reverse(tags);
        return tags ;


    }


}
