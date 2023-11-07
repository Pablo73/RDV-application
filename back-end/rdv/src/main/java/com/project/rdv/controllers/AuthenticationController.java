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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

  private final AuthenticationManager authenticationManager;

  private final UserService userService;

  private final TokenService tokenService;

  @Autowired
  public AuthenticationController(AuthenticationManager authenticationManager,
      UserService userService, TokenService tokenService) {
    this.authenticationManager = authenticationManager;
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> registerUser(
      @Valid @RequestBody CreateUserDto createUserDto
  ) throws IOException {
    DtoConverter dtoConverter = new DtoConverter();

    User createUser = userService.create(createUserDto.toUser());

    UserDto userDto = dtoConverter.personToDto(createUser);

    return ResponseEntity.status(HttpStatus.CREATED).body(userDto);
  }


  @PostMapping("/login")
  public ResponseEntity<?> loginUser(@RequestBody AuthenticationDto authenticationDto) {
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
