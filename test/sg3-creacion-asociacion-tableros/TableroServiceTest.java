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

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import services.UsuarioService;
import services.UsuarioServiceException;
import services.TableroService;
import services.TableroServiceException;

public class TableroServiceTest {
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

   private TableroService newTableroService() {
      return injector.instanceOf(TableroService.class);
   }

   @Test
   public void nuevoTableroUsuario() {
      TableroService tableroService = newTableroService();
      long idUsuario = 1000L;
      System.out.println(tableroService.nuevoTablero(idUsuario, "Pagar el alquiler nuevo"));
      assertEquals(3, tableroService.allTablerosUsuario(1000L).size());
   }

   @Test
   public void modificacionTablero() {
      TableroService tableroService = newTableroService();
      long idTablero = 1000L;
      tableroService.modificaTablero(idTablero, "Pagar el alquiler2");
      Tablero tablero = tableroService.obtenerTablero(idTablero);
      assertEquals("Pagar el alquiler2", tablero.getNombre());
   }

   @Test
   public void borradoTablero() {
      TableroService tableroService = newTableroService();
      long idTablero = 1000L;
      tableroService.borraTablero(idTablero);
      assertNull(tableroService.obtenerTablero(idTablero));
   }

   @Test 
   public void testToPapelera(){
       TableroService tableroService = newTableroService();

       Tablero tablero = tableroService.ToPapelera(1000L);

       assertEquals((Long) 1000L, (Long) tablero.getPapeleraTablero().getId());
   }

   @Test 
   public void testRestaurarPapelera(){
       TableroService tableroService = newTableroService();

       Tablero tablero = tableroService.restaurarTablero(1000L);

       assertNull(tablero.getPapeleraTablero());
   }
}
