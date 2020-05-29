package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface StoryRepository extends JpaRepository<Story,Integer> {

     List<Story> findByUserUserId(int userId);
     Story findByStoryIdAndUserUserId(int storyId , int userId);
     Page<Story> findByUserUserName(String userName, Pageable pageable);
     Page<Story> findByDrafted(boolean value,Pageable pageable);
     Page<Story> findByDraftedAndUserUserId(boolean value , int userId,Pageable pageable);



}
