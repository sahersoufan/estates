package com.springproject.estates.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

import static javax.persistence.FetchType.EAGER;
import static javax.persistence.GenerationType.AUTO;

@Entity @Table(name = "user") @EnableAutoConfiguration
@Data @NoArgsConstructor  @AllArgsConstructor
public class User {

    @Column @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
}
