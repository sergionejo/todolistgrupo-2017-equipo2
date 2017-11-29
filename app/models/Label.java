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

public void setTitulo(String titulo){
    this.titulo=titulo;
}

public void setDescripcion(String descripcion){
    this.descripcion=descripcion;
}

public void setColor(String color){
    this.color=color;
}

}