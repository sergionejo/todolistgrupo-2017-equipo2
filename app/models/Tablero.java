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
public class Tablero {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String nombre;
   @ManyToOne
   // Nombre de la columna en la BD que guarda físicamente
   // el ID del usuario (administrador) con el que está asociado un tablero
   @JoinColumn(name="administradorId")
   public Usuario administrador;
   @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
   @JoinTable(name="Persona_Tablero")
   private Set<Usuario> participantes = new HashSet<Usuario>();


   // Un constructor vacío necesario para JPA
   public Tablero() {}

   // El constructor principal con los campos obligatorios
   public Tablero(Usuario administrador, String nombre) {
      this.administrador = administrador;
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
      return administrador;
   }

   public void setAdministrador(Usuario administrador) {
      this.administrador = administrador;
   }

   public String toString() {
      return String.format("Tablero id: %s nombre: %s administrador: %s",
                      id, nombre, administrador.toString());
   }
   
   public Set<Usuario> getParticipantes() {
       return participantes;
    }
 
    public void setParticipantes(Set<Usuario> participantes) {
       this.participantes = participantes;
    }

    public boolean addParticipante(Usuario usuario){
        return participantes.add(usuario);
    }
 
    @Override
    public int hashCode() {
       final int prime = 31;
       int result = prime + ((nombre == null) ? 0 : nombre.hashCode());
       result = result + ((administrador == null) ? 0 : administrador.hashCode());
       return result;
    }
 
    @Override
    public boolean equals(Object obj) {
       if (this == obj) return true;
       if (getClass() != obj.getClass()) return false;
       Tablero other = (Tablero) obj;
       // Si tenemos los ID, comparamos por ID
       if (id != null && other.id != null)
          return ((long) id == (long) other.id);
       // sino comparamos por campos obligatorios
       else {
          if (nombre == null) {
             if (other.nombre != null) return false;
          } else if (!nombre.equals(other.nombre)) return false;
          if (administrador == null) {
             if (other.administrador != null) return false;
             else if (!administrador.equals(other.administrador)) return false;
          }
       }
       return true;
    }
}
