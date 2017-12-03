package services;

import javax.inject.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;

import models.Usuario;
import models.UsuarioRepository;
import models.Grupo;
import models.GrupoRepository;

public class GrupoService {
    UsuarioRepository usuarioRepository;
    GrupoRepository grupoRepository;

    @Inject
    public GrupoService(UsuarioRepository usuarioRepository, GrupoRepository grupoRepository) {
        this.usuarioRepository = usuarioRepository;
        this.grupoRepository = grupoRepository;
    }

    public List<Grupo> allGrupos() {
        List<Grupo> gruposList = grupoRepository.findAll();
        return gruposList;
    }

    // Devuelve la lista de grupos de un usuario, ordenadas por su id
    // (equivalente al orden de creación)
    public List<Grupo> allGruposUsuario(Long idUsuario) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new GrupoServiceException("Usuario no existente");
        }
        Set<Grupo> grupos = administrador.getGrupos();
        List<Grupo> gruposList = new ArrayList<Grupo>();
        gruposList.addAll(grupos);
        return gruposList;
    }

    public Grupo nuevoGrupo(Long idUsuario, String nombre) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new GrupoServiceException("Usuario no existente");
        }
        Grupo grupo = new Grupo(administrador, nombre);
        return grupoRepository.add(grupo);
    }

    public Grupo findGrupoPorId(Long idGrupo) {
        return grupoRepository.findById(idGrupo);
    }

    public Grupo modificaGrupo(Long idGrupo, String nuevoNombre) {
        Grupo grupo = grupoRepository.findById(idGrupo);
        if (grupo == null)
            throw new GrupoServiceException("No existe grupo");
        grupo.setNombre(nuevoNombre);
        grupo = grupoRepository.update(grupo);
        return grupo;
    }

    public Grupo updateGrupo(Grupo grupoActualizado) {
        Grupo grupo = grupoRepository.update(grupoActualizado);
        return grupo;
    }

    public void borraGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo);
        if (grupo == null)
            throw new GrupoServiceException("No existe grupo");
        grupoRepository.delete(idGrupo);
    }

    public List<Usuario> allParticipantesGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo);
        if (grupo == null) {
            throw new UsuarioServiceException("Grupo no existente");
        }
        Set<Usuario> participantes = grupo.getParticipantes();
        List<Usuario> participantesList = new ArrayList<Usuario>();
        participantesList.addAll(participantes);
        return participantesList;
    }

    public Usuario administradorGrupo(Long idGrupo) {
        Grupo grupo = grupoRepository.findById(idGrupo);
        if (grupo == null) {
            throw new UsuarioServiceException("Grupo no existente");
        }
        return grupo.getAdministrador();
    }
}