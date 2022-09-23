package com.residenciaTst.AtividadePratica.model;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "tb_ministro")
public class Ministro extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column
    private String name;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
