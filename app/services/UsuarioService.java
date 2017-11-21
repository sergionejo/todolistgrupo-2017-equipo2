package services;

import javax.inject.*;

import models.Usuario;
import models.UsuarioRepository;

import java.text.SimpleDateFormat;

import java.util.Date;

public class UsuarioService {
   UsuarioRepository repository;

   // Play proporcionará automáticamente el UsuarioRepository necesario
   // usando inyección de dependencias
   @Inject
   public UsuarioService(UsuarioRepository repository) {
      this.repository = repository;
   }

   public Usuario creaUsuario(String login, String email, String password) {
      if(repository.findByLogin(login) != null){
      	throw new UsuarioServiceException("Login ya existente");
      }
      Usuario usuario = new Usuario(login, email);
      usuario.setPassword(password);
      return repository.add(usuario);
   }

   public Usuario findUsuarioPorLogin(String login){
   	return repository.findByLogin(login);
   }

   public Usuario login(String login, String password){
      Usuario usuario = repository.findByLogin(login);
      if (usuario != null && usuario.getPassword().equals(password)){
         return usuario;
      }else{
         return null;
      }
   }

   public Usuario findUsuarioPorId(Long id){
      return repository.findById(id);
   }

   public Usuario modificarUsuario(Long idUsuario, String pass, String nombre, String apellidos, Date fecha) {
    Usuario usuario = repository.findById(idUsuario);

        if(usuario == null){
            throw new UsuarioServiceException("No existe el usuario");
        }

        if(pass != null && pass != ""){
            usuario.setPassword(pass);
        }

        usuario.setNombre(nombre);
        usuario.setApellidos(apellidos);
        usuario.setFechaNacimiento(fecha);
        
        usuario = repository.update(usuario);

        return usuario;
    }
}
