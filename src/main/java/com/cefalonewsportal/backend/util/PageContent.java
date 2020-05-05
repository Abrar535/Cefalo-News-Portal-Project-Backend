package com.cefalonewsportal.backend.util;

import com.cefalonewsportal.backend.model.Story;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class PageContent {
    private int totalNumberOfPages;
    private Long totalNumberOfStories;
    private List<Story> stories;

    public Long getTotalNumberOfStories() {
        return totalNumberOfStories;
    }

    public void setTotalNumberOfStories(Long totalNumberOfStories) {
        this.totalNumberOfStories = totalNumberOfStories;
    }

    public int getTotalNumberOfPages() {
        return totalNumberOfPages;
    }

    public void setTotalNumberOfPages(int totalNumberOfPages) {
        this.totalNumberOfPages = totalNumberOfPages;
    }

    public List<Story> getStories() {
        return stories;
    }

    public void setStories(List<Story> stories) {
        this.stories = stories;
    }

    @Override
    public String toString() {
        return "PageContent{" +
                "totalNumberOfPages=" + totalNumberOfPages +
                ", totalNumberOfStories=" + totalNumberOfStories +
                ", stories=" + stories +
                '}';
    }
}
