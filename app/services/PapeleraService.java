package services;

import javax.inject.*;

import models.Papelera;
import models.PapeleraRepository;
import models.Tablero;
import models.Usuario;
import models.Papelera;
import models.Tarea;
import models.UsuarioRepository;
import services.TableroServiceException;
import services.UsuarioService;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;

public class PapeleraService{
    PapeleraRepository papeleraRepository;
    UsuarioRepository usuarioRepository;

    @Inject
    public PapeleraService(PapeleraRepository papeleraRepository, UsuarioRepository usuarioRepository) {
       this.papeleraRepository = papeleraRepository;
       this.usuarioRepository = usuarioRepository;
    }

    public Papelera nuevaPapelera(){
        Papelera papelera = new Papelera();
        return papeleraRepository.add(papelera);
    }

    public List<Tarea> allTareasPapelera(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
           throw new TareaServiceException("Usuario no existente");
        }
        Papelera papelera = papeleraRepository.findById(usuario.getPapelera());
        System.out.println(papelera.getTareas());
        Set<Tarea> tareas = papelera.getTareas();
        List<Tarea> tareasList = new ArrayList<Tarea>();
        tareasList.addAll(tareas);
        Collections.sort(tareasList, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return tareasList;
     }

     public List<Tablero> allTablerosPapelera(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
           throw new TableroServiceException("Usuario no existente");
        }
        Papelera papelera = papeleraRepository.findById(usuario.getPapelera());
        
        Set<Tablero> tableros = papelera.getTableros();
        List<Tablero> tablerosList = new ArrayList<Tablero>();
        tablerosList.addAll(tableros);
        Collections.sort(tablerosList, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return tablerosList;
     }
}