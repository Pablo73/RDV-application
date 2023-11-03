package com.project.rdv.models.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;
import com.project.rdv.security.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "\"users\"")
public class User implements UserDetails, GrantedAuthority {

  @Id
  @Column(unique = true)
  private Long id;

  @Column(unique = true)
  private String username;

  private String password;

  @Lob
  @Column
  private byte[] userPhoto;

  private Role role;

  private String sector;

  public User() {
  }

  public User(String username, String password, byte[] userPhoto, Role role,
      String sector) {
    this.username = username;
    this.password = password;
    this.userPhoto = userPhoto;
    this.role = role;
    this.sector = sector;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return false;
  }

  @Override
  public boolean isAccountNonLocked() {
    return false;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return false;
  }

  @Override
  public boolean isEnabled() {
    return false;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  @JsonIgnore
  public Collection<User> getAuthorities() {
    return List.of(this);
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public byte[] getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(byte[] userPhoto) {
    this.userPhoto = userPhoto;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getSector() {
    return sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  @Override
  @JsonIgnore
  public String getAuthority() {
    return this.getRole().getName();
  }
}