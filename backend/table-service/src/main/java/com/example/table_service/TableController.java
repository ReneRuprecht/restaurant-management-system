package com.example.table_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/table")
public class TableController {

  @GetMapping()
  public String index(){
    return "Table Service";
  }

}
