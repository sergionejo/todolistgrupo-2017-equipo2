package models;

import java.util.Date;
import javax.persistence.*;

import models.Grupo;
import models.Usuario;

import java.util.List;
import java.util.ArrayList;

import java.util.Set;
import java.util.HashSet;

@Entity
public class Mensaje {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String mensaje;
    @ManyToOne
    @JoinColumn(name="autorId")
    public Usuario usuario;
    @ManyToOne
    @JoinColumn(name="grupoId")
    public Grupo grupo;
    

    // Un constructor vacío necesario para JPA
    public Mensaje() {}

    public Mensaje(String mensaje){
        this.mensaje = mensaje;
    }

    // Getters and Setters
    /**
     * @return the grupo
     */
    public Grupo getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the usuario
     */
    public Usuario getUsuario() {
        return usuario;
    }

    /**
     * @param usuario the usuario to set
     */
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}