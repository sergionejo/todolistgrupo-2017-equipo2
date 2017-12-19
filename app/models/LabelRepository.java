package models;

import com.google.inject.ImplementedBy;
import java.util.List;
@ImplementedBy(JPALabelRepository.class)
public interface LabelRepository {
   Label add(Label label);
   Label update(Label label);
   void delete(Long idLabel);
   List<Label> findAll();
   Label findById(Long idLabel);
}