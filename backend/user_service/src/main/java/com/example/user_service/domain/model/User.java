package com.example.user_service.domain.model;

import lombok.Getter;
import lombok.Setter;

@Getter
public class User {

  private final UserID id;
  @Setter
  private Username username;
  @Setter
  private Password password;
  @Setter
  private Email email;

  public User(UserID id, Username username, Password password, Email email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.email = email;
  }

}
