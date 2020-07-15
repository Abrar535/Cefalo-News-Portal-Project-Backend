package com.cefalonewsportal.backend.service;
import com.cefalonewsportal.backend.model.Tag;
import com.cefalonewsportal.backend.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.List;
import static java.util.Comparator.comparingInt;

@Service
@CacheConfig(cacheNames = {"tags"})
@Slf4j
public class TagService {
    @Autowired
    TagRepository tagRepository;
    @CacheEvict(allEntries = true)
    public Tag createTag(Tag tag){
        tag.setTagName(tag.getTagName().toLowerCase());
        Tag existingTag = tagRepository.findByTagName(tag.getTagName());

        if(existingTag == null){

            log.info("New tag created and added to a story");
            tag = tagRepository.save(tag);

        }
        else{

            log.info("New tag added to a story");
            tag = existingTag;
        }

      return tag;
    }
    @Cacheable(key = "#root.method.name")
    public List<Tag> getAllTags(){
        log.info("Get all tags called");
        return tagRepository.findAll();

    }
    @Cacheable(key = "#root.method.name")
    public List<Tag> getTrendingTags(){

        List<Tag> tags = tagRepository.findAll();
        tags.sort (
                comparingInt(tag -> tag.getStories().size())
        );
        tags.forEach(tag -> {
            tag.setNumberOfStories(tag.getStories().size());
        });
        Collections.reverse(tags);
        log.info("Get all trending tags called");
        return tags ;


    }


}
