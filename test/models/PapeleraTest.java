import org.junit.*;
import static org.junit.Assert.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

import play.db.Database;
import play.db.Databases;
import play.db.jpa.*;

import play.Logger;

import java.sql.*;

import org.junit.*;
import org.dbunit.*;
import org.dbunit.dataset.*;
import org.dbunit.dataset.xml.*;
import org.dbunit.operation.*;
import java.io.FileInputStream;

import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;
import play.Environment;

import models.Papelera;
import models.PapeleraRepository;
import models.JPAPapeleraRepository;

public class PapeleraTest {
   static Database db;
   static private Injector injector;

   // Se ejecuta sólo una vez, al principio de todos los tests
   @BeforeClass
   static public void initApplication() {
      GuiceApplicationBuilder guiceApplicationBuilder =
          new GuiceApplicationBuilder().in(Environment.simple());
      injector = guiceApplicationBuilder.injector();
      db = injector.instanceOf(Database.class);
      // Necesario para inicializar JPA
      injector.instanceOf(JPAApi.class);
   }


   // Se ejecuta al antes de cada test
   // Se insertan los datos de prueba en la tabla Papeleras de
   // la BD "DBTest". La BD ya contiene una tabla de usuarios
   // porque la ha creado JPA al tener la propiedad
   // hibernate.hbm2ddl.auto (en META-INF/persistence.xml) el valor update
   // Los datos de prueba se definen en el fichero
   // test/resources/usuarios_dataset.xml
   @Before
   public void initData() throws Exception {
        JndiDatabaseTester databaseTester = new JndiDatabaseTester("DBTest");
        IDataSet initialDataSet = new FlatXmlDataSetBuilder().build(new
        FileInputStream("test/resources/usuarios_dataset.xml"));
        databaseTester.setDataSet(initialDataSet);
        // Definimos como operación SetUp CLEAN_INSERT, que hace un
        // DELETE_ALL de todas las tablase del dataset, seguido por un
        // INSERT. (http://dbunit.sourceforge.net/components.html)
        // Es lo que hace DbUnit por defecto, pero así queda más claro.
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
   }

   private PapeleraRepository newPapeleraRepository() {
        return injector.instanceOf(PapeleraRepository.class);
   }

   @Test
   public void testAddPapelera(){
        PapeleraRepository repository = newPapeleraRepository();
        Papelera nueva = new Papelera();
        Papelera papelera = repository.add(nueva);

        assertEquals(nueva, papelera);
   }

   @Test
   public void testFindPapelera(){
        PapeleraRepository repository = newPapeleraRepository();
        Papelera papelera = repository.findById(1000L);

        Boolean dev = false;

        if(papelera.getId() == 1000L){
            dev = true;
        }

        assertTrue(dev);
   }
}
