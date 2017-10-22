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

   public void borraTablero(Long idTablero) {
      Tablero tablero = tableroRepository.findById(idTablero);
      if (tablero == null)
           throw new TableroServiceException("No existe tablero");
      tableroRepository.delete(idTablero);
   }
}