package com.project.rdv.controllers;

import com.project.rdv.controllers.dto.CreateUserDto;
import com.project.rdv.controllers.dto.UserDto;
import com.project.rdv.models.entity.User;
import com.project.rdv.services.TokenService;
import com.project.rdv.services.UserService;
import com.project.rdv.util.DtoConverter;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class UserController {

  private final UserService userService;
  private final TokenService tokenService;
  private final AuthenticationManager authenticationManager;


  public UserController(UserService userService, TokenService tokenService,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.tokenService = tokenService;
    this.authenticationManager = authenticationManager;
  }

  @PostMapping("/users")
  public ResponseEntity<UserDto> createUser(
      @RequestBody
      @Valid
      CreateUserDto createUserDto
  ) {
    User createUser = userService.create(createUserDto.toUser());

    DtoConverter dtoConverter = new DtoConverter();
    UserDto userDto = dtoConverter.personToDto(createUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }

  @GetMapping("/users/{username}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<UserDto> getUserByUsername(@PathVariable @Valid String username) {
    User userById = userService.getPersonByUsername(username);
    UserDto userDto = DtoConverter.personToDto(userById);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }
}