package com.springproject.estates.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;

import static javax.persistence.GenerationType.AUTO;

@Entity
@EnableAutoConfiguration
@Data @NoArgsConstructor @AllArgsConstructor
public class Role {
 @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
}
