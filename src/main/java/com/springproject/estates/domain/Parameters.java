package com.springproject.estates.domain;

import javax.persistence.*;

@Entity
@Table(name = "parameters")
public class Parameters {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long Id;
    @Column(name = "name")
    String name;
    @Column(name = "value")
    long value;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getvalue() {
        return value;
    }

    public void setvalue(long value) {
        this.value = value;
    }
}
