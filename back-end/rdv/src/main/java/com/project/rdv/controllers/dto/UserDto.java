package com.project.rdv.controllers.dto;

import com.project.rdv.security.Role;

public record UserDto(
    Long id,
    String username,
    Role role,
    String sector) {
}
