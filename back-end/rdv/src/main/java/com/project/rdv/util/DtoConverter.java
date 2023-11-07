package com.project.rdv.util;

import com.project.rdv.controllers.dto.UserDto;
import com.project.rdv.models.entity.User;

public class DtoConverter {

  public static UserDto personToDto(User user) {
    return new UserDto(
        user.getId(),
        user.getUsername(),
        user.getRole(),
        user.getSector()
    );
  }
}
