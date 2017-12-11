package services;

import javax.inject.*;

import java.util.ArrayList;
import java.util.Set;
import java.util.List;

import models.Grupo;
import models.GrupoRepository;
import models.Mensaje;
import models.MensajeRepository;
import models.Usuario;
import models.UsuarioRepository;

public class MensajeService{
    GrupoRepository grupoRepository;
    MensajeRepository mensajeRepository;
    UsuarioRepository usuarioRepository;

    @Inject
    public MensajeService(UsuarioRepository usuarioRepository, MensajeRepository mensajeRepository, GrupoRepository grupoRepository){
        this.grupoRepository = grupoRepository;
        this.mensajeRepository = mensajeRepository;
        this.usuarioRepository = usuarioRepository;
    }

    public Mensaje obtenerMensaje(Long idMensaje){
        return mensajeRepository.findById(idMensaje);
    }

    public Mensaje nuevoMensaje(Long idUsuario, Long idGrupo, String cuerpo){
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
           throw new TareaServiceException("Usuario no existente");
        }

        Grupo grupo = grupoRepository.findById(idGrupo);

        Mensaje mensaje = new Mensaje(cuerpo, usuario, grupo);
        return mensajeRepository.add(mensaje);
    }

    public void borrarMensaje(Long idMensaje){
        mensajeRepository.delete(idMensaje);
    }

    public List<Mensaje> allMensajesGrupo(Long idGrupo){
        Grupo grupo = grupoRepository.findById(idGrupo);

        Set<Mensaje> mensajesSet = grupo.getMensajes();
        List<Mensaje> mensajes = new ArrayList<Mensaje>(mensajesSet);

        return mensajes;
    }
}