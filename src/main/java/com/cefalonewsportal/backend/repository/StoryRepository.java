package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Integer> {

    public List<Story> findByUserUserId(int userId);
    public Story findByStoryIdAndUserUserId(int storyId , int userId);
    //public List<Story> findAllByStoryId();

}
