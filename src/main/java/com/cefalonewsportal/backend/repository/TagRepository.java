package com.cefalonewsportal.backend.repository;

import com.cefalonewsportal.backend.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag,Integer> {

    Tag findByTagName(String tagName);

}