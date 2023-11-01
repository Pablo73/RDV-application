package com.project.rdv.controllers.dto;

import com.project.rdv.models.entity.User;
import com.project.rdv.security.Role;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
    @NotNull String username,
    @NotNull String password,
    byte[] userPhoto,
    @NotNull Role role,
    @NotNull String sector
) {
  public User toUser() throws IOException {
    return new User(username, password, userPhoto, role, sector);
  }

}
