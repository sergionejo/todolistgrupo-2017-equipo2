package services;

import javax.inject.*;

import java.util.Set;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;

import models.Usuario;
import models.UsuarioRepository;
import models.PapeleraRepository;
import models.Papelera;
import models.Tarea;
import models.TareaRepository;
import models.Label;
import models.LabelRepository;


public class LabelService {
   UsuarioRepository usuarioRepository;
   LabelRepository labelRepository;

   @Inject
   public LabelService(UsuarioRepository usuarioRepository,LabelRepository labelRepository) {
      this.usuarioRepository = usuarioRepository;

      this.labelRepository = labelRepository;
   }

   public Label nuevoLabel(Long idUsuario, String titulo,String descripcion,String color) {
    Usuario usuario = usuarioRepository.findById(idUsuario);
    if (usuario == null) {
       throw new TareaServiceException("Usuario no existente");
    }
    Label label = new Label(titulo,descripcion,color );
    return labelRepository.add(label);
 }
}
