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
import services.GrupoService;
import services.MensajeService;
import models.Usuario;
import models.Grupo;
import models.Mensaje;
import security.ActionAuthenticator;

public class MensajeController extends Controller {

    @Inject
    FormFactory formFactory;
    @Inject
    UsuarioService usuarioService;
    @Inject
    GrupoService grupoService;
    @Inject
    MensajeService mensajeService;

    @Security.Authenticated(ActionAuthenticator.class)
    public Result listaMensajes(Long idUsuario, Long idGrupo) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);

        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");

        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            Grupo grupo = grupoService.findGrupoPorId(idGrupo);

            List<Mensaje> mensajes = mensajeService.allMensajesGrupo(idGrupo);

            return ok(listaMensajes.render(mensajes, usuario, grupo, aviso, formFactory.form(Mensaje.class)));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result creaNuevoMensaje(Long idUsuario, Long idGrupo) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);

        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");

        } else {
            Form<Mensaje> mensajeForm = formFactory.form(Mensaje.class).bindFromRequest();
            if (mensajeForm.hasErrors()) {
                Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
                Grupo grupo = grupoService.findGrupoPorId(idGrupo);
                List<Mensaje> mensajes = mensajeService.allMensajesGrupo(idGrupo);

                return badRequest(listaMensajes.render(mensajes, usuario, grupo, "Hay errores en el formulario",
                        formFactory.form(Mensaje.class)));
            }
            Mensaje mensaje = mensajeForm.get();
            mensajeService.nuevoMensaje(idUsuario, idGrupo, mensaje.getMensaje());
            flash("aviso", "El mensaje se ha enviado correctamente");
            return redirect(controllers.routes.MensajeController.listaMensajes(idUsuario, idGrupo));
        }
    }
}