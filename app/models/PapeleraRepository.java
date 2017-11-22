package models;

import com.google.inject.ImplementedBy;

import java.util.List;

@ImplementedBy(JPAPapeleraRepository.class)
public interface PapeleraRepository {
    public Papelera add(Papelera papelera);
    public Papelera findById(Long idPapelera);
}