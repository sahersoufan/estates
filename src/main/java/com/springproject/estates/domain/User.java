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

@Entity @EnableAutoConfiguration
@Data @NoArgsConstructor  @AllArgsConstructor
public class User {

    @Id @GeneratedValue(strategy = AUTO)
    private Long id;
    private String name;
    private String username;
    private String password;
    @ManyToMany(fetch = EAGER)
    private Collection<Role> roles = new ArrayList<>();
    @OneToOne(mappedBy = "user_add", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Tracing tracing;
    @OneToOne(mappedBy = "user_edit", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private Tracing tracing2;
    // Fatima :::: note: you don't need getter and setter lombok do that for you
}
