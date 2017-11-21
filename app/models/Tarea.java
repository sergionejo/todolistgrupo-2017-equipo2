package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Tarea {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Long id;
   private String titulo;
   private String descripcion;
   private String estado;
   private String fCreacion;
   private String fLimite;
   // Relación muchos-a-uno entre tareas y usuario
   @ManyToOne
   // Nombre de la columna en la BD que guarda físicamente
   // el ID del usuario con el que está asociado una tarea
   @JoinColumn(name="usuarioId")
   public Usuario usuario;

   public Tarea() {}

   public Tarea(Usuario usuario, String titulo) {
      this.usuario = usuario;
      this.titulo = titulo;
      this.descripcion = "";
      this.estado = "iniciada";

      Date ahora = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh.mm.ss");
      String fechaConFormato = formatter.format(ahora);
      this.fCreacion=fechaConFormato;
   }
   public Tarea(Usuario usuario, String titulo, String descripcion) {
      this.usuario = usuario;
      this.titulo = titulo;
      this.descripcion = descripcion;
      this.estado = "iniciada";

      Date ahora = new Date();
      SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy-hh.mm.ss");//ejeplo salida: 21-11-2017-03.44.18
      String fechaConFormato = formatter.format(ahora);
      this.fCreacion=fechaConFormato;
   }

   // Getters y setters necesarios para JPA

   public Long getId() {
      return id;
   }

   public void setId(Long id) {
      this.id = id;
   }

   public String getTitulo() {
      return titulo;
   }

   public void setTitulo(String titulo) {
      this.titulo = titulo;
   }

   public void setDescripcion(String descripcion){
      this.descripcion = descripcion;
   }

   public String getDescripcion(){
      return descripcion;
   }

   public Usuario getUsuario() {
      return usuario;
   }

   public void setUsuario(Usuario usuario) {
      this.usuario = usuario;
   }

   public String toString() {
      return String.format("Tarea id: %s titulo: %s usuario: %s",
                      id, titulo, usuario.toString());
   }

   public String getEstado(){
      return estado;
   }

   public void setEstado(String estado){
      this.estado = estado;
   }

   public String getFCreacion(){
     return this.fCreacion;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = prime + ((titulo == null) ? 0 : titulo.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) return true;
      if (getClass() != obj.getClass()) return false;
      Tarea other = (Tarea) obj;
      // Si tenemos los ID, comparamos por ID
      if (id != null && other.id != null)
        return ((long) id == (long) other.id);
      // sino comparamos por campos obligatorios
      else {
         if (titulo == null) {
            if (other.titulo != null) return false;
         } else if (!titulo.equals(other.titulo)) return false;
         if (usuario == null) {
            if (other.usuario != null) return false;
            else if (!usuario.equals(other.usuario)) return false;
         }
      }
      return true;
   }
}
