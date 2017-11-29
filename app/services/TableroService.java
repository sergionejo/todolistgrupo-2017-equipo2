package services;

import javax.inject.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;

import models.Usuario;
import models.UsuarioRepository;
import models.Tablero;
import models.TableroRepository;

public class TableroService {
    UsuarioRepository usuarioRepository;
    TableroRepository tableroRepository;

    @Inject
    public TableroService(UsuarioRepository usuarioRepository, TableroRepository tableroRepository) {
        this.usuarioRepository = usuarioRepository;
        this.tableroRepository = tableroRepository;
    }

    public List<Tablero> allTableros() {
        List<Tablero> tablerosList = tableroRepository.findAll();
        return tablerosList;
    }

    // Devuelve la lista de tableros de un usuario, ordenadas por su id
    // (equivalente al orden de creación)
    public List<Tablero> allTablerosUsuario(Long idUsuario) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new TableroServiceException("Usuario no existente");
        }
        Set<Tablero> tableros = administrador.getAdministrados();
        List<Tablero> tablerosList = new ArrayList<Tablero>();
        tablerosList.addAll(tableros);
        return tablerosList;
    }

    public List<Tablero> allTablerosUsuarioParticipados(Long idUsuario) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new TableroServiceException("Usuario no existente");
        }
        Set<Tablero> tableros = administrador.getTableros();
        List<Tablero> tablerosList = new ArrayList<Tablero>();
        tablerosList.addAll(tableros);
        return tablerosList;
    }

    public List<Tablero> allTablerosNoUsuario(Long idUsuario) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new TableroServiceException("Usuario no existente");
        }
        List<Tablero> administrados = allTablerosUsuario(idUsuario);
        List<Tablero> participados = allTablerosUsuarioParticipados(idUsuario);
        List<Tablero> tableros = allTableros();
        tableros.removeAll(administrados);
        tableros.removeAll(participados);
        return tableros;
    }

    public Tablero nuevoTablero(Long idUsuario, String nombre) {
        Usuario administrador = usuarioRepository.findById(idUsuario);
        if (administrador == null) {
            throw new TableroServiceException("Usuario no existente");
        }
        Tablero tablero = new Tablero(administrador, nombre);
        return tableroRepository.add(tablero);
    }

    public Tablero obtenerTablero(Long idTablero) {
        return tableroRepository.findById(idTablero);
    }

    public Tablero modificaTablero(Long idTablero, String nuevoNombre) {
        Tablero tablero = tableroRepository.findById(idTablero);
        if (tablero == null)
            throw new TableroServiceException("No existe tablero");
        tablero.setNombre(nuevoNombre);
        tablero = tableroRepository.update(tablero);
        return tablero;
    }

    public Tablero updateTablero(Tablero tableroActualizado) {
        Tablero tablero = tableroRepository.update(tableroActualizado);
        return tablero;
    }

    public void borraTablero(Long idTablero) {
        Tablero tablero = tableroRepository.findById(idTablero);
        if (tablero == null)
            throw new TableroServiceException("No existe tablero");
        tableroRepository.delete(idTablero);
    }

    public List<Usuario> allParticipantesTablero(Long idTablero) {
        Tablero tablero = tableroRepository.findById(idTablero);
        if (tablero == null) {
            throw new UsuarioServiceException("Tablero no existente");
        }
        Set<Usuario> participantes = tablero.getParticipantes();
        List<Usuario> participantesList = new ArrayList<Usuario>();
        participantesList.addAll(participantes);
        return participantesList;
    }

    public Usuario administradorTablero(Long idTablero) {
        Tablero tablero = tableroRepository.findById(idTablero);
        if (tablero == null) {
            throw new UsuarioServiceException("Tablero no existente");
        }
        return tablero.getAdministrador();
    }

    public Tablero cerrarTablero(Long idTablero) {
        Tablero tablero = tableroRepository.findById(idTablero);

        if (tablero == null) {
            throw new UsuarioServiceException("Tablero no existente");
        }

        tablero.setEstado("cerrado");
        return tableroRepository.update(tablero);
    }
}