package com.example.testproject.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "jackals")
public class Jackal {
    @Id
    private final UUID jackalId;
    private String name;
    private final Date date;
    private long size;
    private String jackal;
    private String type;
    @ManyToOne
    @JoinColumn(nullable = false)
    private User user;
    @OneToOne
    private Picture pic;

    public Jackal(){
        date = new Date();
        jackalId = UUID.randomUUID();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJackal() {
        return jackal;
    }

    public void setJackal(String jackal) {
        this.jackal = jackal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Picture getPic() {
        return pic;
    }

    public void setPic(Picture pic) {
        this.pic = pic;
    }

    public UUID getJackalId() {
        return jackalId;
    }
}
