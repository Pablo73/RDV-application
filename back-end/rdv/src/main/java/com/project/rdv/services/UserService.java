package com.project.rdv.services;

import com.project.rdv.exception.NotFoundException;
import com.project.rdv.models.entity.User;
import com.project.rdv.models.repository.UserRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;

@Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User create(User user) {
    User userOptional = userRepository.findByUsername(user.getUsername());
    if (userOptional != null) {
      throw new NotFoundException("Unable to register, username already exists!");
    } else {
      String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

      Long lastUserId = userRepository.findLastUserId();

      if (lastUserId.equals(0L)) {
        user.setId(1L);
      } else {
        Long newUserId = lastUserId + 1L;;
        user.setId(newUserId);
      }

      user.setPassword(hashedPassword);


      return userRepository.save(user);
    }
  }

  public User getPersonByUsername(String username) {
    User byUsername = userRepository.findByUsername(username);
    if (byUsername == null) {
      throw new NotFoundException("User not exist");
    }
    return byUsername;
  }

  public List<User> getAllUser() {
    List<User> allUser = userRepository.findAll();
    return allUser;
  }

  public void deleteUser(Long id) {
  userRepository.deleteById(id);
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return userRepository.findByUsername(username);
  }
}
