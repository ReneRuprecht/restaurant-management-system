package com.example.user_service.domain.repository;

import com.example.user_service.domain.model.Email;
import com.example.user_service.domain.model.User;
import java.util.Optional;

public interface UserRepository {

  Optional<User> findByEmail(Email email);

}
