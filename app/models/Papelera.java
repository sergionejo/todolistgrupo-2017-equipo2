package models;

import javax.persistence.*;

import models.Tarea;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Pepelera {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy="papelera", fetch=FetchType.EAGER)
    private Set<Tarea> tareas = new HashSet<Tarea>();

    // Un constructor vacío necesario para JPA
    public Papelera() {}
    
    // GETTERS Y SETTERS
    public Set<Tarea> getTareas(){
        return tareas;
    }

    public void setTareas(Set<Tarea> tareas){
        this.tareas = tareas;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }
}
