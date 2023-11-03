package com.project.rdv.controllers;

import com.project.rdv.controllers.dto.AuthenticationDto;
import com.project.rdv.controllers.dto.CreateUserDto;
import com.project.rdv.controllers.dto.ResponseDto;
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
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

  @PostMapping("auth/login")
  public ResponseEntity<?> login(@RequestBody AuthenticationDto authenticationDto) {
    try {
      UsernamePasswordAuthenticationToken usernamePassword =
          new UsernamePasswordAuthenticationToken(
              authenticationDto.username(),
              authenticationDto.password()
          );

      Authentication auth = authenticationManager.authenticate(usernamePassword);

      User user = (User) auth.getPrincipal();

      String token = tokenService.generateToken(user);

      ResponseDto<String> response = new ResponseDto<>(token);

      return ResponseEntity.status(HttpStatus.OK).body(response);

    } catch (BadCredentialsException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized password!");

    } catch (InternalAuthenticationServiceException e) {
      return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized user!");
    }
  }
}
