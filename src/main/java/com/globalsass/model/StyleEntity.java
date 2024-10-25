package com.globalsass.model;

import jakarta.persistence.*;

@Entity
@Table(name = "css")
public class StyleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String variables;

    public StyleEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVariables() {
        return variables;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public StyleEntity(Long id, String variables) {
        this.id = id;
        this.variables = variables;
    }
}