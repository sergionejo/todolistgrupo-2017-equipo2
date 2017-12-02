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
import services.TareaService;
import services.LabelService;
import models.Usuario;
import models.Tarea;
import models.Papelera;
import models.Label;
import security.ActionAuthenticator;

public class LabelController extends Controller {
   
    @Inject FormFactory formFactory;
    @Inject UsuarioService usuarioService;
    @Inject LabelService labelService;
 
    // Comprobamos si hay alguien logeado con @Security.Authenticated(ActionAuthenticator.class)
    // https://alexgaribay.com/2014/06/15/authentication-in-play-framework-using-java/
    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioNuevoLabel(Long idUsuario) {
       String connectedUserStr = session("connected");
       Long connectedUser =  Long.valueOf(connectedUserStr);
       if (!connectedUser.equals(idUsuario)) {
          return unauthorized("Lo siento, no estás autorizado");
       } else {
          Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
          return ok(formNuevoLabel.render(usuario, formFactory.form(Label.class),""));
       }
    }


    @Security.Authenticated(ActionAuthenticator.class)
    public Result creaNuevoLabel(Long idUsuario) {
       String connectedUserStr = session("connected");
       Long connectedUser =  Long.valueOf(connectedUserStr);
       if (!connectedUser.equals(idUsuario)) {
          return unauthorized("Lo siento, no estás autorizado");
       } else {
          Form<Label> labelForm = formFactory.form(Label.class).bindFromRequest();
          if (labelForm.hasErrors()) {
             Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
             return badRequest(formNuevoLabel.render(usuario, formFactory.form(Label.class),"Hay errores en el formulario"));
          }
          Label label = labelForm.get();
          labelService.nuevoLabel(idUsuario, label.getTitulo(),label.getDescripcion(),label.getColor());
          flash("aviso", "El label se ha grabado correctamente");
          return redirect(controllers.routes.TableroController.listaTableros(idUsuario));//continuar aqui
       }
    }


}    