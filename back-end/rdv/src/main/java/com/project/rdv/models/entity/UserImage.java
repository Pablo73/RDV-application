package com.project.rdv.models.entity;

import jakarta.persistence.*;


@Entity
public class UserImage {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  private byte[] imageData;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public UserImage() {
  }

  public UserImage(Long id, byte[] imageData, User user) {
    this.id = id;
    this.imageData = imageData;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public byte[] getImageData() {
    return imageData;
  }

  public void setImageData(byte[] imageData) {
    this.imageData = imageData;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
