package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Label {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String descripcion;
    private String color;


public Label() {}


public Label(String titulo,String descripcion,String color){
    this.titulo=titulo;
    this.descripcion=descripcion;
    this.color=color;
}

public Label(String titulo,String color){
    this.titulo=titulo;
    this.color=color;
}



//getters y setters
public String getTitulo(){
    return titulo;
}

public String getDescripcion(){
    return descripcion;
}

public String getColor(){
    return color;
}

public String setTitulo(String titulo){
    this.titulo=titulo;
}

public String setDescripcion(String descripcion){
    this.descripcion=descripcion;
}

public String setColor(String color){
    this.color=color;
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