package com.project.rdv.controllers.dto;

import com.project.rdv.models.entity.User;
import com.project.rdv.security.Role;
import java.io.IOException;
import org.springframework.web.multipart.MultipartFile;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(

    Long id,
    @NotNull String username,
    @NotNull String password,
    @NotNull Role role,
    @NotNull String sector
) {
  public User toUser() throws IOException {
    return new User(id, username, password, role, sector);
  }

}
