package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Story;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoryRepository extends JpaRepository<Story,Integer> {

}
