package models;

import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(JPAPapeleraRepository.class)
public interface TableroRepository {
    public Papelera add(Papelera papelera);
    public Papelera findById(Long idPapelera);
    public List<Tarea> findAllTareas();
}