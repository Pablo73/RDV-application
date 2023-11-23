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

    User getUserByName = userRepository.findByUsername(user.getUsername());

    if (getUserByName != null) {
      throw new NotFoundException("Unable to register, username already exists!");
    } else {
      String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());

      user.setPassword(hashedPassword);

      return userRepository.save(user);
    }
  }

  public User updateUser(Long id, User user) {

    User getUserByName = userRepository.findUserById(id);

    if (getUserByName == null) {
      throw new NotFoundException("Unable to update! User not found.");
    } else {
      getUserByName.setUsername(user.getUsername());
      String hashedPassword = new BCryptPasswordEncoder().encode(user.getPassword());
      getUserByName.setPassword(hashedPassword);
      getUserByName.setRole(user.getRole());
      getUserByName.setSector(user.getSector());
    }
    return userRepository.save(getUserByName);
  }

  public User getPersonByUsername(String userName) {
    User byUsername = userRepository.findByUsername(userName);
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
  public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    return userRepository.findByUsername(userName);
  }
}
