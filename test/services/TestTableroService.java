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
import services.TareaService;

public class TestTableroService {
    static private Injector injector;

    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder = new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        injector.instanceOf(JPAApi.class);
    }

    @Before
    public void initData() throws Exception {
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTest");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder()
                .build(new FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    private TableroService newTableroService() {
        return injector.instanceOf(TableroService.class);
    }

    @Test
    public void testCerrarTablero() {
        TableroService tableroService = newTableroService();

        Tablero tablero = tableroService.cerrarTablero(1000L);

        assertEquals("cerrado", tablero.getEstado());
    }

    @Test(expected = UsuarioServiceException.class)
    public void testCerrarTableroInexistente() {
        TableroService tableroService = newTableroService();

        Tablero tablero = tableroService.cerrarTablero(3333L);
    }
}