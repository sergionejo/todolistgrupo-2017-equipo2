package services;

import javax.inject.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;

import models.Usuario;
import models.UsuarioRepository;
import models.PapeleraRepository;
import models.Papelera;
import models.Tarea;
import models.TareaRepository;


public class TareaService {
   UsuarioRepository usuarioRepository;
   TareaRepository tareaRepository;
   PapeleraRepository papeleraRepository;

   @Inject
   public TareaService(UsuarioRepository usuarioRepository, TareaRepository tareaRepository, PapeleraRepository papeleraRepository) {
      this.usuarioRepository = usuarioRepository;
      this.tareaRepository = tareaRepository;
      this.papeleraRepository = papeleraRepository;
   }

   // Devuelve la lista de tareas de un usuario, ordenadas por su id
   // (equivalente al orden de creación)
   public List<Tarea> allTareasUsuario(Long idUsuario) {
      Usuario usuario = usuarioRepository.findById(idUsuario);
      if (usuario == null) {
         throw new TareaServiceException("Usuario no existente");
      }
      Set<Tarea> tareas = usuario.getTareas();
      List<Tarea> tareasList = new ArrayList<Tarea>();
      tareasList.addAll(tareas);
      Collections.sort(tareasList, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
      return tareasList;
   }

   public Tarea nuevaTarea(Long idUsuario, String titulo) {
      Usuario usuario = usuarioRepository.findById(idUsuario);
      if (usuario == null) {
         throw new TareaServiceException("Usuario no existente");
      }
      Tarea tarea = new Tarea(usuario, titulo,"");
      return tareaRepository.add(tarea);
   }

   public Tarea nuevaTarea(Long idUsuario, String titulo, String descripcion) {
      Usuario usuario = usuarioRepository.findById(idUsuario);
      if (usuario == null) {
         throw new TareaServiceException("Usuario no existente");
      }
      Tarea tarea = new Tarea(usuario, titulo, descripcion);
      return tareaRepository.add(tarea);
   }

   public Tarea nuevaTarea(Long idUsuario, String titulo, String descripcion,String fechaLimite) {
      Usuario usuario = usuarioRepository.findById(idUsuario);
      if (usuario == null) {
         throw new TareaServiceException("Usuario no existente");
      }
      Tarea tarea = new Tarea(usuario, titulo, descripcion, fechaLimite);
      return tareaRepository.add(tarea);
   }

   public Tarea obtenerTarea(Long idTarea) {
      return tareaRepository.findById(idTarea);
   }

   public Tarea modificaTarea(Long idTarea, String nuevoTitulo) {
      Tarea tarea = tareaRepository.findById(idTarea);
      if (tarea == null)
           throw new TareaServiceException("No existe tarea");
      tarea.setTitulo(nuevoTitulo);
      tarea = tareaRepository.update(tarea);
      return tarea;
   }

   public Tarea modificaTarea(Long idTarea, String nuevoTitulo, String nuevaDescripcion) {
      Tarea tarea = tareaRepository.findById(idTarea);
      if (tarea == null)
           throw new TareaServiceException("No existe tarea");
      tarea.setTitulo(nuevoTitulo);
      tarea.setDescripcion(nuevaDescripcion);
      tarea = tareaRepository.update(tarea);
      return tarea;
   }

   public Tarea modificaTarea(Long idTarea, String nuevoTitulo, String nuevaDescripcion,String fechaLimite) {
      Tarea tarea = tareaRepository.findById(idTarea);
      if (tarea == null)
           throw new TareaServiceException("No existe tarea");
      tarea.setTitulo(nuevoTitulo);
      tarea.setDescripcion(nuevaDescripcion);
      tarea.setFLimite(fechaLimite);
      tarea = tareaRepository.update(tarea);
      return tarea;
   }

   public void borraTarea(Long idTarea) {
      Tarea tarea = tareaRepository.findById(idTarea);
      if (tarea == null)
           throw new TareaServiceException("No existe tarea");
      tareaRepository.delete(idTarea);
   }

   public List<Tarea> allTareasUsuarioNoTerminadas(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new TareaServiceException("Usuario no existente");
        }
        Set<Tarea> tareas = usuario.getTareas();
        List<Tarea> tareasList = new ArrayList<Tarea>();
        tareasList.addAll(tareas);

        List<Tarea> dev = new ArrayList<Tarea>();

        for(Tarea t : tareasList){
            if(t.getEstado().equals("iniciada")){
                dev.add(t);
            }
        }


        Collections.sort(dev, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return dev;
   }

   public List<Tarea> allTareasUsuarioTerminadas(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario);
        if (usuario == null) {
            throw new TareaServiceException("Usuario no existente");
        }
        Set<Tarea> tareas = usuario.getTareas();
        List<Tarea> tareasList = new ArrayList<Tarea>();
        tareasList.addAll(tareas);

        List<Tarea> dev = new ArrayList<Tarea>();

        for(Tarea t : tareasList){
            if(t.getEstado().equals("terminada")){
                dev.add(t);
            }
        }


        Collections.sort(dev, (a, b) -> a.getId() < b.getId() ? -1 : a.getId() == b.getId() ? 0 : 1);
        return dev;
   }

   public Tarea toggleEstadoTarea(Long idTarea){
        Tarea tarea = tareaRepository.findById(idTarea);

        if(tarea.getEstado().equals("iniciada"))
            tarea.setEstado("terminada");

        else
            tarea.setEstado("iniciada");

        tarea = tareaRepository.update(tarea);

        return tarea;
   }

   public Tarea ToPapelera(Long idTarea){
        Tarea tarea = tareaRepository.findById(idTarea);
        Usuario usuario = tarea.getUsuario();
        Papelera papelera = papeleraRepository.findById(usuario.getPapelera());
        tarea.setPapelera(papelera);

        return tareaRepository.update(tarea);
   }

   public Tarea restaurarTarea(Long idTarea){
        Tarea tarea = tareaRepository.findById(idTarea);
        tarea.setPapelera(null);

        return tareaRepository.update(tarea);
   }
}
