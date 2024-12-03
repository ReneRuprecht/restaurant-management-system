package com.example.user_service.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.user_service.domain.exception.InvalidPasswordException;
import org.junit.jupiter.api.Test;

class PasswordTest {

  @Test
  void whenInputIsNull_throwsInvalidPasswordException() {
    String expected = "Password must not be null";

    InvalidPasswordException thrown = assertThrows(InvalidPasswordException.class, () -> {
      new Password(null);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsEmpty_throwsInvalidPasswordException() {
    String emptyPassword = "";
    String expected = "Password must not be empty";

    InvalidPasswordException thrown = assertThrows(InvalidPasswordException.class, () -> {
      new Password(emptyPassword);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsInvalid_throwsInvalidPasswordException() {
    String invalidPassword = "123";
    String expected = "Password length must be > 7";

    InvalidPasswordException thrown = assertThrows(InvalidPasswordException.class, () -> {
      new Password(invalidPassword);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsLongerThan32_throwsInvalidPasswordException() {
    String invalidPassword = "1111111111_1111111111_1111111111_";
    String expected = "Password length must be < 33";

    InvalidPasswordException thrown = assertThrows(InvalidPasswordException.class, () -> {
      new Password(invalidPassword);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsValid_createPassword() {
    String validPassword = "Sup3rSecret!";
    Password expected = new Password("Sup3rSecret!");

    Password actual = new Password(validPassword);

    assertEquals(expected, actual);
  }
}