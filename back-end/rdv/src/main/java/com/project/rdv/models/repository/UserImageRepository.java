package com.project.rdv.models.repository;

import com.project.rdv.models.entity.UserImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserImageRepository extends JpaRepository<UserImage, Long> {

  @Query("SELECT i FROM UserImage i WHERE i.user.id = :id")
  UserImage findUserImageByIdUser(@Param("id")Long id);
}
