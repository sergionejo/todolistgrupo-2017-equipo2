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
import models.Tarea;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import services.UsuarioService;
import services.UsuarioServiceException;
import services.TareaService;
import services.TareaServiceException;

public class TareaServiceTest {
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

   private TareaService newTareaService() {
      return injector.instanceOf(TareaService.class);
   }

   // Test #19: allTareasUsuarioEstanOrdenadas
   @Test
   public void allTareasUsuarioEstanOrdenadas() {
      TareaService tareaService = newTareaService();
      List<Tarea> tareas = tareaService.allTareasUsuario(1000L);
      assertEquals("Renovar DNI", tareas.get(0).getTitulo());
      assertEquals("Práctica 1 MADS", tareas.get(1).getTitulo());
   }

   // Test #20: exceptionSiUsuarioNoExisteRecuperandoSusTareas
   @Test(expected = TareaServiceException.class)
   public void crearNuevoUsuarioLoginRepetidoLanzaExcepcion(){
      TareaService tareaService = newTareaService();
      List<Tarea> tareas = tareaService.allTareasUsuario(1111L);
   }

   // Test #21: nuevaTareaUsuario
   @Test
   public void nuevaTareaUsuario() {
      TareaService tareaService = newTareaService();
      long idUsuario = 1000L;
      tareaService.nuevaTarea(idUsuario, "Pagar el alquiler");
      assertEquals(3, tareaService.allTareasUsuario(1000L).size());
   }

   // Test #22: modificación de tareas
   @Test
   public void modificacionTarea() {
      TareaService tareaService = newTareaService();
      long idTarea = 1000L;
      tareaService.modificaTarea(idTarea, "Pagar el alquiler");
      Tarea tarea = tareaService.obtenerTarea(idTarea);
      assertEquals("Pagar el alquiler", tarea.getTitulo());
   }

   // Test #23: borrado tarea
   @Test
   public void borradoTarea() {
      TareaService tareaService = newTareaService();
      long idTarea = 1000L;
      tareaService.borraTarea(idTarea);
      assertNull(tareaService.obtenerTarea(idTarea));
   }

   // Test #26: nuevaTareaDescripcionUsuario
   @Test
   public void nuevaTareaDescripcionUsuario() {
      TareaService tareaService = newTareaService();
      long idUsuario = 1000L;
      tareaService.nuevaTarea(idUsuario, "Pagar el alquiler","La factura a nombre Pepito Pérez, nº cuenta XXXXXXX");
      assertEquals(3, tareaService.allTareasUsuario(1000L).size());
   }

   // Test #27: modificación de tareas
   @Test
   public void modificacionTareaDescripcion() {
      TareaService tareaService = newTareaService();
      long idTarea = 1000L;
      tareaService.modificaTarea(idTarea, "Pagar el alquiler","La factura a nombre Pepito Pérez, nº cuenta XXXXXXX");
      Tarea tarea = tareaService.obtenerTarea(idTarea);
      assertEquals("Pagar el alquiler", tarea.getTitulo());
      assertEquals("La factura a nombre Pepito Pérez, nº cuenta XXXXXXX", tarea.getDescripcion());
   }

    @Test
    public void testTerminarTarea(){
        TareaService tareaService = newTareaService();

        Tarea tarea = tareaService.toggleEstadoTarea(1001L);

        assertEquals("Práctica 1 MADS", tarea.getTitulo());
        assertEquals("terminada", tarea.getEstado());
    }

    @Test
    public void testVerTareasNoTerminadas(){
        TareaService tareaService = newTareaService();
        long idUsuario = 1000L;
        tareaService.nuevaTarea(idUsuario, "Comprar auriculares","Quiero los Sony que son bonitos y se escuchan bien");

        tareaService.toggleEstadoTarea(1001L);
        List<Tarea> tareas = tareaService.allTareasUsuarioNoTerminadas(idUsuario);

        assertEquals("Comprar auriculares", tareas.get(0).getTitulo());
        assertEquals("Renovar DNI", tareas.get(1).getTitulo());
    }

    @Test
    public void testVerTareasTerminadas(){
        TareaService tareaService = newTareaService();
        long idUsuario = 1000L;
        tareaService.nuevaTarea(idUsuario, "Comprar auriculares","Quiero los Sony que son bonitos y se escuchan bien");

        tareaService.toggleEstadoTarea(1001L);
        List<Tarea> tareas = tareaService.allTareasUsuarioTerminadas(idUsuario);

        assertEquals("Práctica 1 MADS", tareas.get(0).getTitulo());
    }

    @Test
    public void testIniciarTareaTerminada(){
        TareaService tareaService = newTareaService();

        Tarea tarea = tareaService.toggleEstadoTarea(1001L);
        tarea = tareaService.toggleEstadoTarea(1001L);

        assertEquals("Práctica 1 MADS", tarea.getTitulo());
        assertEquals("iniciada", tarea.getEstado());
    }
}
