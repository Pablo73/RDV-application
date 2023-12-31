package com.project.rdv.controllers;

import com.project.rdv.controllers.dto.UserDto;
import com.project.rdv.models.entity.User;
import com.project.rdv.services.TokenService;
import com.project.rdv.services.UserService;
import com.project.rdv.util.DtoConverter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.ArrayList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  private final TokenService tokenService;

  public UserController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @GetMapping("/{username}")
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<UserDto> getUserByUsername(@PathVariable @Valid String username) {
    User getUserByName = userService.getPersonByUsername(username);
    UserDto userDto = DtoConverter.personToDto(getUserByName);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }

  @GetMapping
  @PreAuthorize("hasAnyRole('ADMIN')")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    List<User> allUsers = userService.getAllUser();

    List<UserDto> userDtos = new ArrayList<>();

    for (User user : allUsers) {
      UserDto userDto = DtoConverter.personToDto(user);
      userDtos.add(userDto);
    }

    return (ResponseEntity.ok(userDtos)) ;
  }

  @GetMapping("/details")
  public ResponseEntity<Object> getUser(Authentication authentication) {
    User user = (User) authentication.getPrincipal();

    User personByUsername = userService.getPersonByUsername(user.getUsername());

    UserDto userDto = DtoConverter.personToDto(personByUsername);

    return (ResponseEntity.ok(userDto));
  }

  @PutMapping("/update")
  public ResponseEntity<UserDto> updateUserProfile(
      Authentication authentication,
      @RequestBody User updatedUserData) {

    User user = (User) authentication.getPrincipal();

    User updateUser = userService.updateUser(user.getId(), updatedUserData);

    DtoConverter dtoConverter = new DtoConverter();

    UserDto userDto = dtoConverter.personToDto(updateUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }
}

