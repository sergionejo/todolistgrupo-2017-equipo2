package models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.HashSet;


@Entity
public class Label {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String titulo;
    private String descripcion;
    private String color;

    @ManyToMany(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="Label_tablero")
    private Set<Tablero> TablerosConLabel = new HashSet<Tablero>();

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

public Long getId(){
    return id;
}

public void setID(Long id){
    this.id=id;
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



public Set<Tablero> tablerosAsignados() {
    return TablerosConLabel;
}

public void setTablerosAsignados(Set<Tablero> tablerosAsignados) {
    this.TablerosConLabel = tablerosAsignados;
}


}