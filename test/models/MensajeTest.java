import models.Grupo;
import models.GrupoRepository;
import models.Mensaje;
import models.MensajeRepository;
import models.Usuario;
import models.UsuarioRepository;

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

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

public class MensajeTest {
    static Database db;
    static private Injector injector;

    @BeforeClass
    static public void initApplication() {
        GuiceApplicationBuilder guiceApplicationBuilder = new GuiceApplicationBuilder().in(Environment.simple());
        injector = guiceApplicationBuilder.injector();
        db = injector.instanceOf(Database.class);
        // Necesario para inicializar JPA
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

    private MensajeRepository newMensajeRepository() {
        return injector.instanceOf(MensajeRepository.class);
    }

    private UsuarioRepository newUsuarioRepository() {
        return injector.instanceOf(UsuarioRepository.class);
    }

    private GrupoRepository newGrupoRepository() {
        return injector.instanceOf(GrupoRepository.class);
    }

    @Test
    public void addMensaje() {
        MensajeRepository repository = newMensajeRepository();
        UsuarioRepository usuarioRepository = newUsuarioRepository();
        GrupoRepository grupoRepository = newGrupoRepository();

        Usuario usuario = usuarioRepository.findById(1000L);
        Grupo grupo =  grupoRepository.findById(1000L);

        String cuerpo = "¿Hola que tal te va todo?";

        Mensaje mensaje = new Mensaje(cuerpo, usuario, grupo);
        mensaje = repository.add(mensaje);

        assertEquals((Long) 1000L, (Long) mensaje.getGrupo().getId());
        assertEquals((Long) 1000L, (Long) mensaje.getUsuario().getId());
        assertEquals(cuerpo, mensaje.getMensaje());
    }

    @Test
    public void findMensaje() {
        MensajeRepository repository = newMensajeRepository();

        Mensaje mensaje = repository.findById(1000L);

        assertEquals((Long) 1001L, (Long) mensaje.getGrupo().getId());
        assertEquals((Long) 1002L, (Long) mensaje.getUsuario().getId());
    }

    @Test
    public void updateMensaje() {
        MensajeRepository repository = newMensajeRepository();

        Mensaje mensaje = repository.findById(1000L);
        mensaje.setMensaje("AE");
        mensaje = repository.update(mensaje);

        assertEquals((Long) 1001L, (Long) mensaje.getGrupo().getId());
        assertEquals((Long) 1002L, (Long) mensaje.getUsuario().getId());
        assertEquals("AE", mensaje.getMensaje());
    }

    @Test
    public void deleteMensaje() {
        MensajeRepository repository = newMensajeRepository();

        repository.delete(1000L);

        assertNull(repository.findById(1000L));
    }
}