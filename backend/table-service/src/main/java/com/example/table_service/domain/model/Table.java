package com.example.table_service.domain.model;

import lombok.Getter;
import org.springframework.util.Assert;

@Getter
public class Table {

  private final Long id;
  private TableStatus tableStatus;
  private int number;
  private String name;
  private int seats;

  public Table(Long id, String name, int number, int seats, TableStatus tableStatus) {
    Assert.notNull(name, "Name must not be null");
    Assert.notEmpty(new String[]{name}, "Name must not be empty");
    Assert.notNull(tableStatus, "Name must not be null");
    Assert.state(number > 0, "Number must be greater than 0");
    Assert.state(seats > 0, "Seats must be greater than 0");
    this.id = id;
    this.name = name;
    this.number = number;
    this.seats = seats;
    this.tableStatus = tableStatus;
  }

  public Table(Long id, String name, int number, int seats) {
    Assert.notNull(name, "Name must not be null");
    Assert.notEmpty(new String[]{name}, "Name must not be empty");
    Assert.state(number > 0, "Number must be greater than 0");
    Assert.state(seats > 0, "Seats must be greater than 0");
    this.id = id;
    this.name = name;
    this.number = number;
    this.seats = seats;
    this.tableStatus = TableStatus.AVAILABLE;
  }

  public void setName(String name) {
    Assert.notNull(name, "Name must not be null");
    Assert.notEmpty(new String[]{name}, "Name must not be empty");

    this.name = name;
  }

  public void setSeats(int seats) {
    Assert.state(seats > 0, "Seats must be greater than 0");
    this.seats = seats;
  }

  public void setNumber(int number) {
    Assert.state(number > 0, "Number must be greater than 0");
    this.number = number;
  }

  public void setTableStatus(TableStatus tableStatus) {
    Assert.notNull(name, "TableStatus must not be null");
    this.tableStatus = tableStatus;
  }


}
