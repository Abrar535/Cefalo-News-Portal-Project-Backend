package com.cefalonewsportal.backend.util;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ImagesResponse {

    private List<Resource> images;

    public List<Resource> getImages() {
        return images;
    }

    public void setImages(List<Resource> images) {
        this.images = images;
    }
}
