package com.cefalonewsportal.backend.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "stories")

public class Story   {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private  Integer storyId;

    private String title;
    private String body;
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "published_Date", nullable = false)
    private Date publishedDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Story(){

    }

    public Story(String title , String body ) {

        this.title = title ;
        this.body = body ;

    }


    public Story(String title , String body , Date publishedDate , User user) {

        this.title = title ;
        this.body = body ;
        this.publishedDate = publishedDate ;
        this.user = user;
    }

    public Story(int storyId,String title , String body , Date publishedDate , User user) {
    this.storyId = storyId ;
    this.title = title ;
    this.publishedDate = publishedDate ;
    this.user = user;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Story{" +
                "storyId=" + storyId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                ", publishedDate=" + publishedDate +
                ", user=" + user +
                '}';
    }
}
