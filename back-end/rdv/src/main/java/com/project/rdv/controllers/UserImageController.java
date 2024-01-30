package com.project.rdv.controllers;

import com.project.rdv.models.entity.User;
import com.project.rdv.models.entity.UserImage;
import com.project.rdv.services.UserImageService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;


@RestController
@RequestMapping("/image")
public class UserImageController {
    private final UserImageService userImageService;

    public UserImageController(UserImageService userImageService) {
        this.userImageService = userImageService;
    }
    @PutMapping
    public ResponseEntity<UserImage> uploadImage(
            @RequestParam("image") MultipartFile imageFile,
            Authentication authentication) throws IOException {

        User user = (User) authentication.getPrincipal();
        byte[] bytesImgem = imageFile.getBytes();

        UserImage userImage = userImageService.uploadImage(user, bytesImgem);

        return ResponseEntity.status(HttpStatus.CREATED).body(userImage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getImage(Authentication authentication, @PathVariable Long id) {
        User user = (User) authentication.getPrincipal();
        UserImage imageData = userImageService.getImage(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(imageData.getImageData(), headers, HttpStatus.OK);
    }

}
