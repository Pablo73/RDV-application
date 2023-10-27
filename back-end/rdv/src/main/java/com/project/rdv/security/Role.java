package com.project.rdv.security;

public enum Role {
  ADMIN("ROLE_ADMIN"),

  MANAGER("ROLE_MANAGER"),

  USER("ROLE_USER");

  private final String name;

  Role(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }
}