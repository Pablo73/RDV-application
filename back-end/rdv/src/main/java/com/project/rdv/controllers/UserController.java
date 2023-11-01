package com.project.rdv.controllers;

import com.project.rdv.controllers.dto.CreateUserDto;
import com.project.rdv.controllers.dto.UserDto;
import com.project.rdv.models.entity.User;
import com.project.rdv.services.TokenService;
import com.project.rdv.services.UserService;
import com.project.rdv.util.DtoConverter;
import jakarta.validation.Valid;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
      @Valid @RequestPart("createUserDto") CreateUserDto createUserDto,
      @RequestPart("userPhoto") MultipartFile userPhoto
  ) throws IOException {
    System.out.println("------------------------------------------------");
    System.out.println(userPhoto.getContentType());
    System.out.println("------------------------------------------------");
    DtoConverter dtoConverter = new DtoConverter();

    CreateUserDto newUserDto = dtoConverter.createPersonToDto(createUserDto, userPhoto);

    User createUser = userService.create(newUserDto.toUser());

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

  @GetMapping("users")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<List<User>> getAllUsers() {
    return (ResponseEntity.ok(userService.getAllUser())) ;
  }

}
