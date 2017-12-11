import org.junit.*;

import models.Mensaje;

import static org.junit.Assert.*;

import play.db.jpa.*;

import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;

import java.util.List;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import services.GrupoService;
import services.MensajeService;
import services.UsuarioService;
import play.Environment;

public class MensajeServiceTest {
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

    private UsuarioService newUsuarioService() {
        return injector.instanceOf(UsuarioService.class);
    }

    private MensajeService newMensajeService() {
        return injector.instanceOf(MensajeService.class);
    }

    private GrupoService newGrupoService() {
        return injector.instanceOf(GrupoService.class);
    }

    @Test
    public void testNuevoMensaje(){
        MensajeService mensajeService = newMensajeService();

        String cuerpo = "¿Hola que tal te va todo?";

        Mensaje mensaje = mensajeService.nuevoMensaje(1000L, 1000L, cuerpo);

        assertEquals((Long) 1000L, (Long) mensaje.getGrupo().getId());
        assertEquals((Long) 1000L, (Long) mensaje.getUsuario().getId());
        assertEquals(cuerpo, mensaje.getMensaje());
    }

    @Test
    public void testBorraMensaje(){

        MensajeService mensajeService = newMensajeService();
        mensajeService.borrarMensaje(1000L);

        assertNull(mensajeService.obtenerMensaje(1000L));
    }

    @Test
    public void testAllMensajesGrupo(){
        MensajeService mensajeService = newMensajeService();

        Mensaje men1 = mensajeService.nuevoMensaje(1000L, 1000L, "Hola");
        Mensaje men2 = mensajeService.nuevoMensaje(1000L, 1000L, "Sigues ahi?");

        List<Mensaje> mensajes = mensajeService.allMensajesGrupo(1000L);

        assertEquals(men1.getMensaje(), mensajes.get(0).getMensaje());
        assertEquals(men2.getMensaje(), mensajes.get(1).getMensaje());
        assertEquals(2, mensajes.size());
    }
}