package com.project.rdv.controllers.dto;

import com.project.rdv.models.entity.User;
import com.project.rdv.security.Role;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(
    Long id,
    @NotNull String username,
    @NotNull String password,
    byte[] userPhoto,
    @NotNull Role role,
    @NotNull String sector
) {
  public User toUser() {
    return new User(id, username, password, userPhoto, role, sector);
  }

}
