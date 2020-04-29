package com.cefalonewsportal.backend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","stories"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO )
    private Integer userId;
    private String userName;
    private String fullName;
    private String password;

    @OneToMany(mappedBy = "user" , cascade = {CascadeType.ALL},fetch = FetchType.LAZY ,orphanRemoval = true)
    private List<Story> stories;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", stories=" + stories +
                '}';
    }
}
