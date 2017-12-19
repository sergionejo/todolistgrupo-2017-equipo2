package controllers;

import play.mvc.*;

import views.html.*;
import javax.inject.*;
import play.data.Form;
import play.data.FormFactory;
import play.data.DynamicForm;
import play.Logger;

import java.util.List;
import java.util.Set;
import java.util.HashSet;

import services.UsuarioService;
import services.TableroService;
import services.LabelService;
import models.Usuario;
import models.Tablero;
import models.Label;
import security.ActionAuthenticator;

public class TableroController extends Controller {

    @Inject
    FormFactory formFactory;
    @Inject
    UsuarioService usuarioService;
    @Inject
    TableroService tableroService;
    @Inject
    LabelService labelService;

    // Comprobamos si hay alguien logeado con @Security.Authenticated(ActionAuthenticator.class)
    // https://alexgaribay.com/2014/06/15/authentication-in-play-framework-using-java/
    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioNuevoTablero(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            List<Label> labels = labelService.allLabels();
            return ok(formNuevoTablero.render(usuario, formFactory.form(Tablero.class), "",labels));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result creaNuevoTablero(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            Form<Tablero> tableroForm = formFactory.form(Tablero.class).bindFromRequest();
            if (tableroForm.hasErrors()) {
                Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
                List<Label> labels = labelService.allLabels();
                return badRequest(formNuevoTablero.render(usuario, formFactory.form(Tablero.class),
                        "Hay errores en el formulario",labels));
            }
            System.out.println("estoy cogiendo:");
            Tablero tablero = tableroForm.get();

            System.out.println(tablero.getNombreslabel());
            System.out.println(tablero.getNombre());

            Label label = labelService.obtenerLabel(Long.valueOf(tablero.getNombreslabel()));

            System.out.println("he recuperado el label:");
            System.out.println(label.getTitulo());

            tablero.addLabel(label);

            Set<Label> setLabels = new HashSet<Label>();
            setLabels.add(label);
            System.out.println(setLabels.size());
            tablero.setLabels(setLabels);
            

            tableroService.nuevoTablero(idUsuario, tablero.getNombre(),setLabels);
            flash("aviso", "El tablero se ha grabado correctamente");
            return redirect(controllers.routes.TableroController.listaTableros(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result listaTableros(Long idUsuario) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            List<Tablero> tablerosAdministrados = tableroService.allTablerosUsuario(idUsuario);
            List<Tablero> tablerosParticipados = tableroService.allTablerosUsuarioParticipados(idUsuario);
            List<Tablero> tablerosResto = tableroService.allTablerosNoUsuario(idUsuario);
            List<Tablero> tableros = tableroService.allTableros();
            List<Label> labels = labelService.allLabels();


            return ok(listaTableros.render(tableros, tablerosAdministrados, tablerosParticipados, tablerosResto,
                    usuario, aviso,labels));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result detalleTablero(Long idTablero) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        String aviso = flash("aviso");
        Tablero tablero = tableroService.obtenerTablero(idTablero);
        List<Usuario> participantes = tableroService.allParticipantesTablero(tablero.getId());
        Usuario administrador = tablero.getAdministrador();
        return ok(detalleTablero.render(tablero, administrador, participantes, aviso));
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result seguirTablero(Long idUsuario, Long idTablero) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            Tablero tablero = tableroService.obtenerTablero(idTablero);
            tablero.addParticipante(usuario);
            tablero = tableroService.updateTablero(tablero);
            return redirect(controllers.routes.TableroController.listaTableros(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result noSeguirTablero(Long idUsuario, Long idTablero) {
        String connectedUserStr = session("connected");
        Long connectedUser = Long.valueOf(connectedUserStr);
        if (!connectedUser.equals(idUsuario)) {
            return unauthorized("Lo siento, no estás autorizado");
        } else {
            String aviso = flash("aviso");
            Usuario usuario = usuarioService.findUsuarioPorId(idUsuario);
            Tablero tablero = tableroService.obtenerTablero(idTablero);
            tablero.removeParticipante(usuario);
            tablero = tableroService.updateTablero(tablero);
            return redirect(controllers.routes.TableroController.listaTableros(idUsuario));
        }
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result formularioEditaTablero(Long idTablero) {
        Tablero tablero = tableroService.obtenerTablero(idTablero);
        if (tablero == null) {
            return notFound("Tablero no encontrado");
        } else {
            String connectedUserStr = session("connected");
            Long connectedUser = Long.valueOf(connectedUserStr);
            List<Label> labels = labelService.allLabels();

            if (!connectedUser.equals(tablero.getAdministrador().getId())) {
                return unauthorized("Lo siento, no estás autorizado");
            } else {
                return ok(formModificacionTablero.render(tablero.getAdministrador().getId(), tablero.getId(),
                        tablero.getNombre(), "",labels));
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

    @Security.Authenticated(ActionAuthenticator.class)
    public Result cerradoTablero(Long idTablero) {
        Tablero tablero = tableroService.cerrarTablero(idTablero);
        flash("aviso", "Tablero cerrado correctamente");
        return redirect(controllers.routes.TableroController.listaTableros(tablero.getAdministrador().getId()));
    }

    @Security.Authenticated(ActionAuthenticator.class)
    public Result cerrarTablero(Long idTablero) {
        return ok();
    }
}
