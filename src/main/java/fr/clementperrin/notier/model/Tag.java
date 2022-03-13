package fr.clementperrin.notier.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Class Tag
 * Created on 25/12/2021
 *
 * @author Clément PERRIN <contact@clementperrin.fr>
 */
@Entity
@Table(name = "tags")
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public Tag(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Tag() {

    }

    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Note> notes = new HashSet<>();



    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Note> getNotes() {
        return this.notes;
    }
}
