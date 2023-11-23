package com.project.rdv.controllers.dto;

import com.project.rdv.models.entity.User;

public record UserImageDto(
        Long id,
        byte[] imageData,
        User userId
) {}
