import org.junit.*;
import static org.junit.Assert.*;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;

import play.Logger;

import java.sql.*;

import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;

import java.util.List;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import models.Usuario;
import models.Tarea;
import models.UsuarioRepository;
import models.JPAUsuarioRepository;
import models.PapeleraRepository;
import models.JPAPapeleraRepository;
import models.TareaRepository;
import models.JPATareaRepository;
import models.TableroRepository;
import models.JPATableroRepository;
import services.UsuarioService;
import services.UsuarioServiceException;
import services.TareaService;
import services.TareaServiceException;

public class Practica2Test {
   static Database db;
   static JPAApi jpaApi;

   @BeforeClass
   static public void initDatabase() {
      db = Databases.inMemoryWith("jndiName", "DBTest");
      db.getConnection();
      db.withConnection(connection -> {
         connection.createStatement().execute("SET MODE MySQL;");
      });
      jpaApi = JPA.createFor("memoryPersistenceUnit");
   }

   private TareaService newTareaService() {
      UsuarioRepository usuarioRepository = new JPAUsuarioRepository(jpaApi);
      TareaRepository tareaRepository = new JPATareaRepository(jpaApi);
      PapeleraRepository papeleraRepository = new JPAPapeleraRepository(jpaApi);
      TableroRepository tableroRepository = new JPATableroRepository(jpaApi);
      return new TareaService(usuarioRepository, tareaRepository, papeleraRepository,tableroRepository);
   }
   
   @Test
   public void testFindUsuarioPorIdIsNull() {
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      Usuario usuario = repository.findById(1015L);
      assertNull(usuario);
   }

   @Test
   public void findUsuarioPorIdIsNull() {
      UsuarioRepository repository = new JPAUsuarioRepository(jpaApi);
      UsuarioService usuarioService = new UsuarioService(repository);
      Usuario usuario = usuarioService.findUsuarioPorId(1015L);
      assertNull(usuario);
   }

   @Test(expected = TareaServiceException.class)
   public void borradoTareaThrowsException() {
      TareaService tareaService = newTareaService();
      long idTarea = 2000L;
      tareaService.borraTarea(idTarea);
   }

   @Test(expected = TareaServiceException.class)
   public void nuevaTareaUsuarioThrowsException() {
      TareaService tareaService = newTareaService();
      long idUsuario = 2000L;
      tareaService.nuevaTarea(idUsuario, "Pagar el alquiler");
   }
}