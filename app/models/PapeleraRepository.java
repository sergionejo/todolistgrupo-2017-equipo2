package models;

import com.google.inject.ImplementedBy;

import models.Papelera;

import java.util.List;

@ImplementedBy(JPAPapeleraRepository.class)
public interface PapeleraRepository {
    public Papelera add(Papelera papelera);
    public Papelera findById(Long idPapelera);
    public Papelera update(Papelera papelera);
}