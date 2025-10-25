package com.example.budgetbook.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Agency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Agency() {}
    public Agency(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    @Override
    public boolean equals(Object o) { if (this==o) return true; if (!(o instanceof Agency)) return false; return Objects.equals(id, ((Agency)o).id); }
    @Override
    public int hashCode() { return Objects.hashCode(id); }
}
