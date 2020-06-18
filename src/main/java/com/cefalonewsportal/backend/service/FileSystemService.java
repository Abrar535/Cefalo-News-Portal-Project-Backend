package com.cefalonewsportal.backend.service;

import com.cefalonewsportal.backend.model.Story;
import com.cefalonewsportal.backend.util.FileStorageNotFoundException;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileSystemService {
    @Autowired
    StoryService storyService;
    private final String rootDirectory = "src/main/resources/public/images/stories/";
    private final Path rootDirectoryPath = Paths.get(rootDirectory).toAbsolutePath().normalize();

    public boolean addImages(List<MultipartFile> images , Integer storyId){

        for(MultipartFile image:images){
            addImage(image, storyId);
        }
        return true ;
    }


    public void addImage(MultipartFile image,Integer storyId){
        Story story = storyService.findById(storyId);
        String imageName = StringUtils.cleanPath(image.getOriginalFilename());


        String imagePath = rootDirectory+imageName;

        try (InputStream is = image.getInputStream()) {
            String images = story.getImages();
            System.out.println(images);
            if(images.equals("")){
                story.setImages(imageName);
            }
            else{
                story.setImages(images+","+imageName);
            }
            FileUtils.copyInputStreamToFile(is,new File(imagePath));
        } catch (IOException ex) {
            throw new RuntimeException(ex.getMessage());
        }
        storyService.save(story);


    }

    public List<Resource> getImages(Integer storyId){
        Story story = storyService.findById(storyId);
         List<Resource> storyImages = new ArrayList<>();
        String[] imageNames =  story.getImages().split(",");

        for(String imageName:imageNames){
            System.out.println("I am imageNames "+imageName);
            storyImages.add(getAnImage(imageName));
        }
        //storyImages.forEach(blah -> System.out.println(blah));
        return storyImages;

    }

    public Resource getAnImage(String imageName) {
        try {
        Path image = this.rootDirectoryPath.resolve(imageName).normalize();
        Resource resource = new UrlResource(image.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new FileStorageNotFoundException("Could not read image" + imageName);
            }
        }
        catch (MalformedURLException e){
            throw new FileStorageNotFoundException("Could not read image"+imageName,e);
        }


    }






}
