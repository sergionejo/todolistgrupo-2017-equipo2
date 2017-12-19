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
import models.Grupo;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import services.UsuarioService;
import services.UsuarioServiceException;
import services.GrupoService;

public class TestGrupoService {
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

    private GrupoService newGrupoService() {
        return injector.instanceOf(GrupoService.class);
    }

    private UsuarioService newUsuarioService() {
        return injector.instanceOf(UsuarioService.class);
    }
    
    @Test
    public void crearNuevoGrupoTest(){
        UsuarioService usuarioService = newUsuarioService();
        Usuario usuario = usuarioService.findUsuarioPorId(1000L);
        GrupoService gs = newGrupoService();
        Grupo grupo = gs.nuevoGrupo(usuario.getId(),"Grupo prueba");
        assertNotNull(usuario.getId());
        assertNotNull(grupo);
    }

    @Test
    public void findGrupoPorId() {
        GrupoService gs = newGrupoService();
        Grupo grupo = gs.findGrupoPorId(1000L);
        assertNotNull(grupo);
        assertEquals("Grupo test 1", grupo.getNombre());
    }
}