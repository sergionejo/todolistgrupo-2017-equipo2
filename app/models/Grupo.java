package models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
 
import java.util.Date;
import javax.persistence.*;
import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Grupo {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String nombre;
    @ManyToOne
    @JoinColumn(name="admingrupoId")
    public Usuario admingrupo;
    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="Persona_Grupo")
    private Set<Usuario> participagrupo = new HashSet<Usuario>();

    // Un constructor vacío necesario para JPA
    public Grupo() {}

    // El constructor principal con los campos obligatorios
    public Grupo(Usuario admingrupo, String nombre) {
        this.admingrupo = admingrupo;
        this.nombre = nombre;
    }

    // Getters y setters necesarios para JPA

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Usuario getAdministrador() {
        return admingrupo;
    }

    public void setAdministrador(Usuario admingrupo) {
        this.admingrupo = admingrupo;
    }

    public String toString() {
        return String.format("Grupo id: %s nombre: %s admingrupo: %s",
                        id, nombre, admingrupo.toString());
    }

    public Set<Usuario> getParticipantes() {
        return participagrupo;
    }

    public void setParticipantes(Set<Usuario> participagrupo) {
        this.participagrupo = participagrupo;
    }

    public boolean addParticipante(Usuario usuario){
        return participagrupo.add(usuario);
    }

    public boolean removeParticipante(Usuario usuario){
        return participagrupo.remove(usuario);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = prime + ((nombre == null) ? 0 : nombre.hashCode());
        result = result + ((admingrupo == null) ? 0 : admingrupo.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (getClass() != obj.getClass()) return false;
        Grupo other = (Grupo) obj;
        // Si tenemos los ID, comparamos por ID
        if (id != null && other.id != null)
            return ((long) id == (long) other.id);
        // sino comparamos por campos obligatorios
        else {
            if (nombre == null) {
                if (other.nombre != null) return false;
            } else if (!nombre.equals(other.nombre)) return false;
            if (admingrupo == null) {
                if (other.admingrupo != null) return false;
                else if (!admingrupo.equals(other.admingrupo)) return false;
            }
        }
        return true;
    }
}
