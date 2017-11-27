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
import models.Papelera;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import services.UsuarioService;
import services.UsuarioServiceException;
import services.TableroService;
import services.TableroServiceException;

public class DescripciontableroTest {
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
    
   private UsuarioService newUsuarioService() {
      return injector.instanceOf(UsuarioService.class);
   }

    @Test
    public void checkParticipantesTablero() {
        TableroService tableroService = newTableroService();
        UsuarioService usuarioService = newUsuarioService();
        Usuario administrador = usuarioService.findUsuarioPorId(1000L);
        Usuario participante = usuarioService.findUsuarioPorId(1001L);
        Tablero tablero1 = new Tablero(administrador, "Tablero 1");
        assertTrue(tablero1.addParticipante(participante));

        Papelera papelera1 = new Papelera();
        Papelera papelera2 = new Papelera();
        Papelera papelera3 = new Papelera();

        Usuario usuario1 = usuarioService.creaUsuario("usuario1", "usuario1@gmail.com","1234", papelera1);
        Usuario usuario2 = usuarioService.creaUsuario("usuario2", "usuario2@gmail.com","1234", papelera2);
        Usuario usuario3 = usuarioService.creaUsuario("usuario3", "usuario3@gmail.com","1234", papelera3);
        assertTrue(tablero1.addParticipante(usuario1));
        assertTrue(tablero1.addParticipante(usuario2));
        assertTrue(tablero1.addParticipante(usuario3));
        tablero1 = tableroService.updateTablero(tablero1);
        assertEquals(4,tableroService.allParticipantesTablero(tablero1.getId()).size());
    }

    @Test
    public void checkAdministradorTablero() {
        TableroService tableroService = newTableroService();
        UsuarioService usuarioService = newUsuarioService();
        Usuario administrador = usuarioService.findUsuarioPorId(1000L);
        Tablero tablero1 = tableroService.nuevoTablero(administrador.getId(), "Tablero TEST");
        assertEquals(administrador,tableroService.administradorTablero(tablero1.getId()));
    }

    @Test
    public void checkNombreTablero() {
        TableroService tableroService = newTableroService();
        UsuarioService usuarioService = newUsuarioService();
        Usuario administrador = usuarioService.findUsuarioPorId(1000L);
        Tablero tablero1 = tableroService.nuevoTablero(administrador.getId(), "Tablero TEST");
        assertEquals("Tablero TEST",tableroService.obtenerTablero(tablero1.getId()).getNombre());
    }
}
