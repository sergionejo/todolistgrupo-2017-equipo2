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
import models.Usuario;
import models.Grupo;
import security.ActionAuthenticator;

public class GrupoController extends Controller {

    @Inject
    FormFactory formFactory;
    @Inject
    UsuarioService usuarioService;
    @Inject
    GrupoService grupoService;

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioNuevoGrupo(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            return ok(formNuevoGrupo.render(usuario, formFactory.form(Grupo.class), ""));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result creaNuevoGrupo(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            Form<Grupo> grupoForm = formFactory.form(Grupo.class).bindFromRequest();
            if (grupoForm.hasErrors()) {
                Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
                return badRequest(formNuevoGrupo.render(usuario, formFactory.form(Grupo.class),
                        "Hay errores en el formulario"));
            }
            Grupo grupo = grupoForm.get();
            grupoService.nuevoGrupo(idUsuario, grupo.getNombre());
            flash("aviso", "El grupo se ha grabado correctamente");
            return redirect(controllers.routes.GrupoController.listaGrupos(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result listaGrupos(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            List<Grupo> gruposAdministrados = grupoService.allGruposUsuario(idUsuario);

            return ok(listaGrupos.render(gruposAdministrados,
                    usuario, aviso));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result detalleGrupo(Long idGrupo) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        String aviso = flash("aviso");
        Grupo grupo = grupoService.findGrupoPorId(idGrupo);
        List<Usuario> participantes = grupoService.allParticipantesGrupo(grupo.getId());
        Usuario administrador = grupo.getAdministrador();
        return ok(detalleGrupo.render(grupo, administrador, participantes, aviso));
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result seguirGrupo(Long idUsuario, Long idGrupo) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            Grupo grupo = grupoService.findGrupoPorId(idGrupo);
            grupo.addParticipante(usuario);
            grupo = grupoService.updateGrupo(grupo);
            return redirect(controllers.routes.GrupoController.listaGrupos(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result noSeguirGrupo(Long idUsuario, Long idGrupo) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            Grupo grupo = grupoService.findGrupoPorId(idGrupo);
            grupo.removeParticipante(usuario);
            grupo = grupoService.updateGrupo(grupo);
            return redirect(controllers.routes.GrupoController.listaGrupos(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioEditaGrupo(Long idGrupo) {
        Grupo grupo = grupoService.findGrupoPorId(idGrupo);
        if (grupo == null) {
            return notFound("Grupo no encontrado");
        } else {
            String connectedUserStr = session("connected");
            Long connectedUser = Long.valueOf(connectedUserStr);
            if (!connectedUser.equals(grupo.getAdministrador().getId())) {
                return unauthorized("Lo siento, no estás autorizado");
            } else {
                return ok(formModificacionGrupo.render(grupo.getAdministrador().getId(), grupo.getId(),
                        grupo.getNombre(), ""));
            }
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result grabaGrupoModificado(Long idGrupo) {
        DynamicForm requestData = formFactory.form().bindFromRequest();
        String nuevoNombre = requestData.get("nombre");
        Grupo grupo = grupoService.modificaGrupo(idGrupo, nuevoNombre);
        return redirect(controllers.routes.GrupoController.listaGrupos(grupo.getAdministrador().getId()));
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result borraGrupo(Long idGrupo) {
        grupoService.borraGrupo(idGrupo);
        flash("aviso", "Grupo borrado correctamente");
        return ok();
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioGrupoNuevoUsuario(Long idGrupo){
        Grupo grupo = grupoService.findGrupoPorId(idGrupo);
        if (grupo == null) {
            return notFound("Grupo no encontrado");
        } else {
            String connectedUserStr = session("connected");
            Long connectedUser = Long.valueOf(connectedUserStr);
            if (!connectedUser.equals(grupo.getAdministrador().getId())) {
                return unauthorized("Lo siento, no estás autorizado");
            } else {
                DynamicForm requestData = formFactory.form().bindFromRequest();
                Long idUsuario = Long.valueOf(requestData.get("q"));
                if (connectedUser.equals(idUsuario)) {
                    return unauthorized("Lo siente, eres administrador de este grupo y no te puedes añadir como seguidor.");
                }
                grupoService.nuevoUsuario(idGrupo,idUsuario);
            }
        }
        return ok();
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioGrupoBorraUsuario(Long idGrupo){
        Grupo grupo = grupoService.findGrupoPorId(idGrupo);
        if (grupo == null) {
            return notFound("Grupo no encontrado");
        } else {
            String connectedUserStr = session("connected");
            Long connectedUser = Long.valueOf(connectedUserStr);
            if (!connectedUser.equals(grupo.getAdministrador().getId())) {
                return unauthorized("Lo siento, no estás autorizado");
            } else {
                DynamicForm requestData = formFactory.form().bindFromRequest();
                Long idUsuario = Long.valueOf(requestData.get("q"));
                if (connectedUser.equals(idUsuario)) {
                    return unauthorized("Lo siente, eres administrador de este grupo y no te puedes añadir como seguidor.");
                }
                grupoService.borraUsuario(idGrupo,idUsuario);
            }
        }
        flash("aviso", "Usuario borrado correctamente.");
        return redirect(controllers.routes.GrupoController.detalleGrupo(grupo.getId()));
    }
}
