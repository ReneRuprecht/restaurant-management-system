package com.example.user_service.domain.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.user_service.domain.exception.InvalidEmailException;
import org.junit.jupiter.api.Test;

class EmailTest {

  @Test
  void whenInputIsEmpty_throwsInvalidEmailException() {
    String emptyEmail = "";
    String expected = "Email must not be empty";

    InvalidEmailException thrown = assertThrows(InvalidEmailException.class, () -> {
      new Email(emptyEmail);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsInvalid_throwsInvalidEmailException() {
    String emptyEmail = "ab.de.de";
    String expected = "Email must be valid";

    InvalidEmailException thrown = assertThrows(InvalidEmailException.class, () -> {
      new Email(emptyEmail);
    });

    assertEquals(expected, thrown.getMessage());
  }

  @Test
  void whenInputIsValid_createEmail() {
    String email = "ab@cd.com";
    Email expected = new Email("ab@cd.com");

    Email actual = new Email(email);

    assertEquals(expected, actual);
  }
}