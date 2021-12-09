package com.springproject.estates.domain;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "tracing")
public class Tracing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @OneToOne
    @JoinColumn(name = "user_add")
    User user_add;
    @Column(name ="add_date")
    Date add_date;

    @OneToOne
    @JoinColumn(name = "user_edit")
    User user_edit;

    @Column(name ="edit_date")
    Date edit_date;

    @OneToOne(mappedBy = "tracing", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private EstateModel estateModel;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser_add() {
        return user_add;
    }

    public void setUser_add(User user_add) {
        this.user_add = user_add;
    }

    public Date getAdd_date() {
        return add_date;
    }

    public void setAdd_date(Date add_date) {
        this.add_date = add_date;
    }

    public User getUser_edit() {
        return user_edit;
    }

    public void setUser_edit(User user_edit) {
        this.user_edit = user_edit;
    }

    public Date getEdit_date() {
        return edit_date;
    }

    public void setEdit_date(Date edit_date) {
        this.edit_date = edit_date;
    }
}
