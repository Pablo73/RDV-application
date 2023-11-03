package com.project.rdv.controllers.dto;

import jakarta.validation.constraints.NotNull;

public record AuthenticationDto(String username, String password) {}
