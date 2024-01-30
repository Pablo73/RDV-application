package com.project.rdv.services;

import com.project.rdv.models.entity.User;
import com.project.rdv.models.entity.UserImage;
import com.project.rdv.models.repository.UserImageRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserImageService {

  private final UserImageRepository userImageRepository;

  @Autowired
  public UserImageService(UserImageRepository userImageRepository) {
    this.userImageRepository = userImageRepository;
  }

  public UserImage uploadImage (User user, byte[] imageData) {
    UserImage getUserImageByIdUser = userImageRepository.findUserImageByIdUser(user.getId());

    if (getUserImageByIdUser != null) {
      getUserImageByIdUser.setImageData(imageData);
      return userImageRepository.save(getUserImageByIdUser);

    } else {
      UserImage newUserImage = new UserImage();
      newUserImage.setImageData(imageData);
      newUserImage.setUser(user);
      return userImageRepository.save(newUserImage);
    }
  }

  public UserImage getImage (Long id) {
    return userImageRepository.findById(id).orElse(null);
  }
}
