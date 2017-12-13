package models;

import javax.inject.Inject;
import play.db.jpa.JPAApi;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import models.Mensaje;

import javax.persistence.NoResultException;

public class JPAMensajeRepository implements MensajeRepository {
    JPAApi jpaApi;

    @Inject
    public JPAMensajeRepository(JPAApi api) {
        this.jpaApi = api;
    }

    public Mensaje add(Mensaje mensaje) {
        return jpaApi.withTransaction(entityManager -> {
            entityManager.persist(mensaje);
            entityManager.flush();
            entityManager.refresh(mensaje);
            return mensaje;
        });
    }

    public Mensaje findById(Long idMensaje) {
        return jpaApi.withTransaction(entityManager -> {
            return entityManager.find(Mensaje.class, idMensaje);
        });
    }

    public Mensaje update(Mensaje mensaje) {
        return jpaApi.withTransaction(entityManager -> {
            Mensaje actualizado = entityManager.merge(mensaje);
            return actualizado;
        });
    }

    public void delete(Long idMensaje) {
        jpaApi.withTransaction(() -> {
           EntityManager entityManager = jpaApi.em();
           Mensaje mensajeBD = entityManager.getReference(Mensaje.class, idMensaje);
           entityManager.remove(mensajeBD);
        });
     }
}