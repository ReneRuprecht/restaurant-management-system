package com.example.table_service.entity;

import com.example.table_service.model.TableStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@jakarta.persistence.Table(name = "tables")
public class Table {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "number")
  private int number;

  @Column(name = "name")
  private String name;

  @Column(name = "seats")
  private int seats;

  @Column(name = "status")
  @Setter
  private TableStatus tableStatus;

  public void setName(String name) {
    if (name == null || name.isEmpty()) {
      return;
    }
    this.name = name;
  }

  public void setSeats(int seats) {
    if (seats < 1) {
      return;
    }
    this.seats = seats;
  }

}
