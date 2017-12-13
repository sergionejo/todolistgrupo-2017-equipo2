package models;

import javax.persistence.*;

import models.Tablero;
import models.Tarea;
import models.Usuario;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Papelera {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    @OneToMany(mappedBy="papelera", fetch=FetchType.EAGER)
    private Set<Tarea> tareas = new HashSet<Tarea>();
    @OneToMany(mappedBy="papeleraTablero", fetch=FetchType.EAGER)
    private Set<Tablero> tableros = new HashSet<Tablero>();

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

    /**
     * @return the tableros
     */
    public Set<Tablero> getTableros() {
        return tableros;
    }

    /**
     * @param tableros the tableros to set
     */
    public void setTableros(Set<Tablero> tableros) {
        this.tableros = tableros;
    }
}
