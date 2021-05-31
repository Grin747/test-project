package com.example.testproject.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "pics")
public class Picture {
    @Id
    private final UUID picId;
    private String picture;
    private String type;
    @OneToOne
    private Jackal jackal;

    public Picture(){
        picId = UUID.randomUUID();
    }

    public UUID getPicId() {
        return picId;
    }

    public Jackal getJackal() {
        return jackal;
    }

    public void setJackal(Jackal jackal) {
        this.jackal = jackal;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
