package com.cefalonewsportal.backend.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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

    @Temporal(TemporalType.DATE)
    @Column(name = "published_Date", nullable = false)
    private Date publishedDate;

    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

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
