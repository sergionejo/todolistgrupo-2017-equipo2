package models;

import com.google.inject.ImplementedBy;

import models.Mensaje;

import java.util.List;

@ImplementedBy(JPAMensajeRepository.class)
public interface MensajeRepository {
    public Mensaje add(Mensaje mensaje);
    public Mensaje findById(Long idMensaje);
    public Mensaje update(Mensaje mensaje);
    public void delete(Long idMensaje);
}