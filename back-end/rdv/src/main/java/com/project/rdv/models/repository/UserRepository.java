package com.project.rdv.models.repository;

import com.project.rdv.models.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
  User findByUsername(String username);

  @Query("SELECT COALESCE(MAX(u.id), 0) FROM User u")
  Long findLastUserId();
}