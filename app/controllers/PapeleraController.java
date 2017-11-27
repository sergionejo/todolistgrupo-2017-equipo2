package controllers;

import play.mvc.*;

import views.html.*;
import javax.inject.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import play.Logger;

import java.util.List;

import services.PapeleraService;
import services.UsuarioService;
import services.PapeleraService;
import services.TareaService;
import models.Usuario;
import models.Tarea;
import security.ActionAuthenticator;

public class PapeleraController extends Controller {

   @Inject FormFactory formFactory;
   @Inject PapeleraService papeleraService;
   @Inject UsuarioService usuarioService;

   @Security.Authenticated(ActionAuthenticator.class)
   public Result listaPapelera(Long idUsuario) {
    String connectedUserStr = session("connected");
    Long connectedUser =  Long.valueOf(connectedUserStr);
    if (!connectedUser.equals(idUsuario)) {
       return unauthorized("Lo siento, no estás autorizado");
    } else {
       String aviso = flash("aviso");
       Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
       List<Tarea> tareas = papeleraService.allTareasPapelera(idUsuario);
       return ok(papelera.render(tareas, usuario, aviso));
    }
   }
}