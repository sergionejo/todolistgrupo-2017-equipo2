package models;

import com.google.inject.ImplementedBy;
import java.util.List;
@ImplementedBy(JPATareaRepository.class)
public interface LabelRepository {
   Label add(Label label);
   //Tarea update(Tarea tarea);
   //void delete(Long idTarea);
   //Tarea findById(Long idTarea);
}