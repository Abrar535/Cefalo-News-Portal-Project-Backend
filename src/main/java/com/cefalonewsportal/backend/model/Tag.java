package com.cefalonewsportal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="tag")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    @NotNull
    private Integer tagId;
    @NotNull
    private String tagName;

    private Integer numberOfStories = 0;


    @ManyToMany(mappedBy = "tags")
    @JsonIgnore
    private Set<Story> stories;

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Set<Story> getStories() {
        return stories;
    }

    public void setStories(Set<Story> stories) {
        this.stories = stories;
    }

    public Integer getNumberOfStories() {
        return numberOfStories;
    }

    public void setNumberOfStories(Integer numberOfStories) {
        this.numberOfStories = numberOfStories;
    }

    public Tag() {
    }
    public Tag(String tagName){
        this.tagName = tagName;
    }

    //    @Override
//    public String toString() {
//        return "Tag{" +
//                "tagId=" + tagId +
//                ", tagName='" + tagName + '\'' +
//                ", viewCount=" + viewCount +
//                ", stories=" + stories +
//                '}';
//    }
}
