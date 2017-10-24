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

public class ApuntarseylistadoTest {
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
    public void addParticipante() {
        TableroService tableroService = newTableroService();
        UsuarioService usuarioService = newUsuarioService();
        Usuario administrador = usuarioService.findUsuarioPorId(1000L);
        Usuario participante = usuarioService.findUsuarioPorId(1001L);
        Tablero tablero1 = new Tablero(administrador, "Tablero 1");
        assertTrue(tablero1.addParticipante(participante));
    }

    @Test
    public void checkUsuarioParticipante() {
        TableroService tableroService = newTableroService();
        UsuarioService usuarioService = newUsuarioService();
        Usuario administrador = usuarioService.findUsuarioPorId(1000L);
        Usuario participante = usuarioService.findUsuarioPorId(1001L);
        Tablero tablero1 = new Tablero(administrador, "Tablero 1");
        assertTrue(tablero1.addParticipante(participante));
        tableroService.updateTablero(tablero1);
        assertEquals(1,tableroService.allTablerosUsuarioParticipados(participante.getId()).size());
    }

    @Test
    public void getAllTableros() {
        TableroService tableroService = newTableroService();
        Tablero tablero1 = tableroService.nuevoTablero(1000L, "Tablero 3");
        assertEquals(3,tableroService.allTableros().size());
    }

    @Test
    public void getAllTablerosNoUsuario() {
        TableroService tableroService = newTableroService();
        Tablero tablero1 = tableroService.nuevoTablero(1001L, "Tablero 3");
        assertEquals(2,tableroService.allTablerosNoUsuario(1001L).size());
    }
}
