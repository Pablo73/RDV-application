package com.project.rdv.util;

import com.project.rdv.controllers.dto.CreateUserDto;
import com.project.rdv.controllers.dto.UserDto;
import com.project.rdv.models.entity.User;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;

public class DtoConverter {

  public static UserDto personToDto(User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getUserPhoto(),
        user.getRole(),
        user.getSector()
    );
  }

  public static CreateUserDto createPersonToDto(CreateUserDto createUserDto, MultipartFile userPhoto)
      throws IOException {
    return new CreateUserDto(
        createUserDto.username(),
        createUserDto.password(),
        userPhoto.getBytes(),
        createUserDto.role(),
        createUserDto.sector()
    );
  }

}
