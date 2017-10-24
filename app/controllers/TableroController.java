package controllers;

import play.mvc.*;

import views.html.*;
import javax.inject.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import play.Logger;

import java.util.List;

import services.UsuarioService;
import services.TableroService;
import models.Usuario;
import models.Tablero;
import security.ActionAuthenticator;

public class TableroController extends Controller {

   @Inject FormFactory formFactory;
   @Inject UsuarioService usuarioService;
   @Inject TableroService tableroService;

   // Comprobamos si hay alguien logeado con @Security.Authenticated(ActionAuthenticator.class)
   // https://alexgaribay.com/2014/06/15/authentication-in-play-framework-using-java/
   @Security.Authenticated(ActionAuthenticator.class)
   public Result formularioNuevoTablero(Long idUsuario) {
      String connectedUserStr = session("connected");
      Long connectedUser =  Long.valueOf(connectedUserStr);
      if (!connectedUser.equals(idUsuario)) {
         return unauthorized("Lo siento, no estás autorizado");
      } else {
         Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
         return ok(formNuevoTablero.render(usuario, formFactory.form(Tablero.class),""));
      }
   }

   @Security.Authenticated(ActionAuthenticator.class)
   public Result creaNuevoTablero(Long idUsuario) {
      String connectedUserStr = session("connected");
      Long connectedUser =  Long.valueOf(connectedUserStr);
      if (!connectedUser.equals(idUsuario)) {
         return unauthorized("Lo siento, no estás autorizado");
      } else {
         Form<Tablero> tableroForm = formFactory.form(Tablero.class).bindFromRequest();
         if (tableroForm.hasErrors()) {
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            return badRequest(formNuevoTablero.render(usuario, formFactory.form(Tablero.class), "Hay errores en el formulario"));
         }
         Tablero tablero = tableroForm.get();
         tableroService.nuevoTablero(idUsuario, tablero.getNombre());
         flash("aviso", "El tablero se ha grabado correctamente");
         return redirect(controllers.routes.TableroController.listaTableros(idUsuario));
      }
   }

   @Security.Authenticated(ActionAuthenticator.class)
   public Result listaTableros(Long idUsuario) {
      String connectedUserStr = session("connected");
      Long connectedUser =  Long.valueOf(connectedUserStr);
      if (!connectedUser.equals(idUsuario)) {
         return unauthorized("Lo siento, no estás autorizado");
      } else {
         String aviso = flash("aviso");
         Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
         List<Tablero> tableros = tableroService.allTablerosUsuario(idUsuario);
         return ok(listaTableros.render(tableros, usuario, aviso));
      }
   }

   @Security.Authenticated(ActionAuthenticator.class)
   public Result formularioEditaTablero(Long idTablero) {
      Tablero tablero = tableroService.obtenerTablero(idTablero);
      if (tablero == null) {
         return notFound("Tablero no encontrado");
      } else {
         String connectedUserStr = session("connected");
         Long connectedUser =  Long.valueOf(connectedUserStr);
         if (!connectedUser.equals(tablero.getAdministrador().getId())) {
            return unauthorized("Lo siento, no estás autorizado");
         } else {
            return ok(formModificacionTablero.render(tablero.getAdministrador().getId(),
            tablero.getId(),
            tablero.getNombre(),
            ""));
         }
      }
   }

   @Security.Authenticated(ActionAuthenticator.class)
   public Result grabaTableroModificado(Long idTablero) {
      DynamicForm requestData = formFactory.form().bindFromRequest();
      String nuevoNombre = requestData.get("nombre");
      Tablero tablero = tableroService.modificaTablero(idTablero, nuevoNombre);
      return redirect(controllers.routes.TableroController.listaTableros(tablero.getAdministrador().getId()));
   }

   @Security.Authenticated(ActionAuthenticator.class)
   public Result borraTablero(Long idTablero) {
      tableroService.borraTablero(idTablero);
      flash("aviso", "Tablero borrado correctamente");
      return ok();
   }
}
