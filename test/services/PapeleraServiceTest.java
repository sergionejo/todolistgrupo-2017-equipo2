import org.junit.*;
import static org.junit.Assert.*;

import play.db.jpa.*;

import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;

import java.util.List;

import models.Usuario;
import models.Tablero;
import models.Tarea;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import services.UsuarioService;
import services.UsuarioServiceException;
import services.PapeleraService;
import services.TareaService;
import services.TareaServiceException;

public class PapeleraServiceTest {
   static private Injector injector;

   @BeforeClass
   static public void initApplication() {
      GuiceApplicationBuilder guiceApplicationBuilder =
          new GuiceApplicationBuilder().in(Environment.simple());
      injector = guiceApplicationBuilder.injector();
      injector.instanceOf(JPAApi.class);
   }

   @Before
   public void initData() throws Exception {
      JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTest");
      IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new FileInputStream("test/resources/usuarios_dataset.xml"));
      databaseTester.setDataSet(initialDataSet);
      databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
      databaseTester.onSetup();
   }

   private PapeleraService newPapeleraService() {
      return injector.instanceOf(PapeleraService.class);
   }

   @Test
   public void testListarPapelera(){
       PapeleraService papeleraService = newPapeleraService();

       List<Tarea> tareas = papeleraService.allTareasPapelera(1000L);

       assertEquals(2, tareas.size());
       assertEquals((Long) 1000L, (Long) tareas.get(0).getId());
       assertEquals((Long) 1001L, (Long) tareas.get(1).getId());
   }

   @Test
   public void testListarPapeleraTablero(){
       PapeleraService papeleraService = newPapeleraService();

       List<Tablero> tableros = papeleraService.allTablerosPapelera(1000L);

       assertEquals(1, tableros.size());
       assertEquals((Long) 1000L, (Long) tableros.get(0).getId());
   }
}